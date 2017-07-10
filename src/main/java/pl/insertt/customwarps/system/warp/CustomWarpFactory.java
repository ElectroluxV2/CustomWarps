package pl.insertt.customwarps.system.warp;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import pl.insertt.customwarps.CustomWarpsPlugin;
import pl.insertt.customwarps.system.command.SomethingWentWrong;
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
    public CustomWarp createCustomWarp(UUID owner, String name, Location location, Material icon) throws IllegalArgumentException, SomethingWentWrong
    {
        Validate.notNull(owner, "Owner's UUID can't be null!");
        Validate.notNull(name, "Name of the warp can't be null!");
        Validate.notNull(location, "Location of the warp can't be null!");

        if(icon == null || icon == Material.AIR)
        {
            icon = RandomUtils.getRandomMaterial(CustomWarpConstants.RANDOM_WARP_ICONS);
        }

        final CustomWarp service = new CustomWarpImpl(owner, name, location, icon);
        final CreateWarpEvent event = new CreateWarpEvent(owner, service);

        if(event.isCancelled())
        {
            throw new SomethingWentWrong(event.getReason());
        }

        if(plugin.getRegistry().exists(service.getName()))
        {
            throw new SomethingWentWrong(plugin.getMessages().getWarpAlreadyExists());
        }

        plugin.getRegistry().register(service);
        return service;
    }

}
