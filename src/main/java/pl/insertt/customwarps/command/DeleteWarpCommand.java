package pl.insertt.customwarps.command;

import pl.insertt.customwarps.CustomWarpsPlugin;
import pl.insertt.customwarps.system.command.ArgumentParseException;
import pl.insertt.customwarps.system.command.Arguments;
import pl.insertt.customwarps.system.command.SomethingWentWrong;
import pl.insertt.customwarps.system.command.api.Command;
import pl.insertt.customwarps.system.command.api.CommandInfo;
import pl.insertt.customwarps.system.command.api.WarpCommandSender;
import pl.insertt.customwarps.system.command.api.WarpPlayer;
import pl.insertt.customwarps.system.warp.api.CustomWarp;
import pl.insertt.customwarps.util.StringUtils;

public class DeleteWarpCommand implements Command
{
    private final CustomWarpsPlugin plugin;

    public DeleteWarpCommand(CustomWarpsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @CommandInfo(name = "deletewarp",
                 description = "Delete warp command.",
                 usage = "/deletewarp <name>",
                 aliases = {"delwarp", "removewarp", "remwarp"},
                 permission = "customwarps.command.deletewarp",
                 minArgs = 1,
                 maxArgs = 16)
    public void execute(WarpCommandSender sender, Arguments args) throws ArgumentParseException, SomethingWentWrong
    {
        if(sender.hasPermission("customwarps.command.deletewarp.admin") || sender.isOp())
        {
            String name = StringUtils.buildName(args.getFrom(1, 16));
            CustomWarp warp = plugin.getRegistry().getWarp(name);

            if(warp == null)
            {
                throw new SomethingWentWrong(plugin.getMessages().getWarpDoesntExists());
            }

            plugin.getRegistry().unregister(warp);
            sender.sendMessage(plugin.getMessages().getSuccessColor() + plugin.getMessages().getWarpDeleted());
            return;
        }

        final WarpPlayer player = (WarpPlayer) sender;
        String name = args.getString(0);
        final CustomWarp warp = plugin.getRegistry().getWarp(name);

        if(warp == null)
        {
            throw new SomethingWentWrong(plugin.getMessages().getWarpDoesntExists());
        }

        if(!warp.getOwner().equals(player.getUniqueId()))
        {
            throw new SomethingWentWrong(plugin.getMessages().getNotWarpOwner());
        }

        plugin.getRegistry().unregister(warp);
        player.sendDependMessage(plugin.getMessages().getSuccessColor() + plugin.getMessages().getWarpDeleted());
    }
}

