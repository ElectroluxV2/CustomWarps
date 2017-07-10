package pl.insertt.customwarps.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import pl.insertt.customwarps.CustomWarpsPlugin;
import pl.insertt.customwarps.system.command.ArgumentParseException;
import pl.insertt.customwarps.system.command.Arguments;
import pl.insertt.customwarps.system.command.SomethingWentWrong;
import pl.insertt.customwarps.system.command.api.Command;
import pl.insertt.customwarps.system.command.api.CommandInfo;
import pl.insertt.customwarps.system.command.api.WarpCommandSender;
import pl.insertt.customwarps.system.command.api.WarpPlayer;
import pl.insertt.customwarps.system.warp.api.CustomWarp;

public class WarpCommand implements Command
{
    private final CustomWarpsPlugin plugin;

    public WarpCommand(CustomWarpsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @CommandInfo(name = "warp",
                 description = "Teleport onto warp",
                 usage = "/warp <name>",
                 aliases = {"tpwarp", "warpto"},
                 permission = "customwarps.command.warp",
                 minArgs = 1,
                 maxArgs = 16,
                 playerOnly = true)
    public void execute(WarpCommandSender sender, Arguments args) throws ArgumentParseException, SomethingWentWrong
    {
        WarpPlayer player = (WarpPlayer) sender;
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
            player.sendDependMessage(plugin.getMessages().getWarningColor() + plugin.getMessages().getUnsafeWarp());
            final Location start = player.bukkit().getLocation();

            Bukkit.getScheduler().runTaskLater(plugin, () ->
            {
                if(player.bukkit().getLocation().getX() != start.getX() || player.bukkit().getLocation().getZ() != start.getZ())
                {
                    player.sendMessage(plugin.getMessages().getErrorColor() + plugin.getMessages().getTeleportAbort());
                    return;
                }

                player.bukkit().teleport(warp.getLocation());

                player.sendDependMessage(plugin.getMessages().getSuccessColor() + plugin.getMessages().getWarpTeleport() + warp.getName());
            }, 3 * 20L);
            return;
        }

        player.bukkit().teleport(warp.getLocation());

        player.sendDependMessage(plugin.getMessages().getSuccessColor() + plugin.getMessages().getWarpTeleport() + warp.getName());
    }
}
