package pl.insertt.customwarps.command;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.insertt.customwarps.CustomWarpsPlugin;
import pl.insertt.customwarps.command.framework.*;
import pl.insertt.customwarps.system.warp.api.CustomWarp;

public class WarpCommand implements Command
{
    private final CustomWarpsPlugin plugin;

    public WarpCommand(CustomWarpsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @CommandInfo(name = "warp", description = "Teleport onto warp", usage = "/warp <name>", aliases = {"tpwarp", "cwarp"}, permission = "customwarps.command.warp", minArgs = 1, maxArgs = 16, playerOnly = true)
    public void execute(CommandSender sender, Arguments args) throws ArgumentParseException, SomethingWentWrong
    {
        Player player = (Player) sender;
        final StringBuilder buf = new StringBuilder();

        for(String str : args.getFrom(1, 16))
        {
            buf.append(str).append(" ");
        }

        String warpName = buf.toString().trim();

        CustomWarp warp = this.plugin.getRegistry().getWarp(warpName);

        if(warp == null)
        {
            throw new SomethingWentWrong(plugin.getMessages().getWarpDoesntExists());
        }

        if(!warp.isSafe())
        {
            TextComponent message = new TextComponent(plugin.getMessages().getWarningColor() + "" + plugin.getMessages().getUnsafeWarp());
            player.spigot().sendMessage(message);
            final Location start = player.getLocation();

            Bukkit.getScheduler().runTaskLater(plugin, () ->
            {
                if(player.getLocation().getX() != start.getX() || player.getLocation().getZ() != start.getZ())
                {
                    player.sendMessage(plugin.getMessages().getErrorColor() + plugin.getMessages().getTeleportAbort());
                    return;
                }

                player.teleport(warp.getLocation());

                player.spigot().sendMessage(plugin.getConfiguration().getMessageFormat(), new TextComponent(plugin.getMessages().getSuccessColor() + plugin.getMessages().getWarpTeleport() + warp.getName()));
            }, 3 * 20L);
            return;
        }

        player.teleport(warp.getLocation());

        player.spigot().sendMessage(plugin.getConfiguration().getMessageFormat(), new TextComponent(plugin.getMessages().getSuccessColor() + plugin.getMessages().getWarpTeleport() + warp.getName()));
    }
}
