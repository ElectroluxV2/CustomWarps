package pl.insertt.customwarps.system.warp;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.insertt.customwarps.CustomWarpsPlugin;
import pl.insertt.customwarps.system.warp.api.CreateWarpEvent;
import pl.insertt.customwarps.system.warp.api.CustomWarp;
import pl.insertt.customwarps.util.RandomUtils;

import javax.annotation.Nullable;
import java.util.UUID;

public class CustomWarpFactory
{
    private final CustomWarpsPlugin plugin;

    public CustomWarpFactory(CustomWarpsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Nullable
    public CustomWarp createCustomWarp(UUID owner, String name, Location location, Material icon) throws IllegalArgumentException
    {
        Validate.notNull(owner, "Owner's UUID can't be null!");
        Validate.notNull(name, "Name of the warp can't be null!");
        Validate.notNull(location, "Location of the warp can't be null!");

        if(icon == null || icon == Material.AIR)
        {
            icon = RandomUtils.getRandomMaterial(CustomWarpConstants.RANDOM_WARP_ICONS);
        }

        final CustomWarp service = new CustomWarpService(owner, name, location, icon);
        final Player player = Bukkit.getPlayer(owner);
        final CreateWarpEvent event = new CreateWarpEvent(owner, service);

        if(event.isCancelled())
        {
            player.sendMessage(ChatColor.RED + "Something went wrong: " + event.getReason());
            return null;
        }

        boolean valid = plugin.getRegistry().register(service);

        if(!valid)
        {
            player.sendMessage(ChatColor.RED + "Something went wrong: this warp exists!");
            return null;
        }

        return service;
    }

}
