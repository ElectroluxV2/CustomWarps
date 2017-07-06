package pl.insertt.customwarps.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.insertt.customwarps.CustomWarpsPlugin;
import pl.insertt.customwarps.command.framework.ArgumentParseException;
import pl.insertt.customwarps.command.framework.Arguments;
import pl.insertt.customwarps.command.framework.Command;
import pl.insertt.customwarps.command.framework.CommandInfo;
import pl.insertt.customwarps.warp.CustomWarpConstants;
import pl.insertt.customwarps.warp.api.CustomWarp;

public class WarpCommand implements Command
{
    private final CustomWarpsPlugin plugin;
    private final CustomWarpConstants constants;

    public WarpCommand(CustomWarpsPlugin plugin, CustomWarpConstants constants)
    {
        this.plugin = plugin;
        this.constants = constants;
    }

    @CommandInfo(name = "warp", description = "Teleport onto warp", usage = "/warp <name>", aliases = {"tpwarp", "cwarp"}, permission = "customwarps.command.warp", minArgs = 1, maxArgs = 16, playerOnly = true)
    public void execute(CommandSender sender, Arguments args) throws ArgumentParseException
    {
        Player player = (Player) sender;
        final StringBuilder buf = new StringBuilder();

        for(String str : args.getFrom(1, 16))
        {
            buf.append(str).append(" ");
        }

        String warpName = buf.toString().trim();

        CustomWarp warp = this.plugin.getWarpRegistry().getWarp(warpName);

        if(warp == null)
        {
            player.sendMessage(ChatColor.RED + "This warp doesn't exists!");
            return;
        }

        if(!warp.isSafe())
        {
            TextComponent message = new TextComponent(ChatColor.YELLOW + "Teleport to this warp isn't safe, do you want to teleport? \n" + ChatColor.YELLOW + "We will teleport you in 3 seconds, if u want to abort, move.");
            player.spigot().sendMessage(message);
            final Location start = player.getLocation();

            Bukkit.getScheduler().runTaskLater(plugin, () ->
            {
                if(player.getLocation().getX() != start.getX() || player.getLocation().getZ() != start.getZ())
                {
                    player.sendMessage(ChatColor.RED + "Teleport aborted.");
                    return;
                }

                player.teleport(warp.getLocation());

                player.spigot().sendMessage(constants.MESSAGE_TYPE, new TextComponent(ChatColor.GREEN + "Teleported onto warp: " + warp.getName()));
            }, 3 * 20L);
            return;
        }

        player.teleport(warp.getLocation());

        player.spigot().sendMessage(constants.MESSAGE_TYPE, new TextComponent(ChatColor.GREEN + "Teleported onto warp: " + warp.getName()));
    }
}
