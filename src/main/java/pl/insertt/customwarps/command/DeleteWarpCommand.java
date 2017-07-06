package pl.insertt.customwarps.command;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.insertt.customwarps.CustomWarpsPlugin;
import pl.insertt.customwarps.command.framework.*;
import pl.insertt.customwarps.system.warp.api.CustomWarp;

public class DeleteWarpCommand implements Command
{
    private final CustomWarpsPlugin plugin;

    public DeleteWarpCommand(CustomWarpsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @CommandInfo(name = "deletewarp", description = "Delete warp command.", usage = "/deletewarp <name>", permission = "customwarps.command.deletewarp", minArgs = 1, maxArgs = 1)
    public void execute(CommandSender sender, Arguments args) throws ArgumentParseException, SomethingWentWrong
    {
        if(sender.hasPermission("customwarps.command.deletewarp.admin") || sender.isOp())
        {
            String name = args.getString(0);
            CustomWarp warp = plugin.getRegistry().getWarp(name);
            boolean result = plugin.getRegistry().unregister(warp);
            if(sender instanceof Player)
            {
                if(result)
                {
                    ((Player)sender).spigot().sendMessage(plugin.getConfiguration().getMessageFormat(), new TextComponent(plugin.getMessages().getSuccessColor() + plugin.getMessages().getWarpDeleted()));
                    return;
                }
                else
                {
                    throw new SomethingWentWrong(plugin.getMessages().getWarpDoesntExists());
                }
            }
            if(result)
            {
                sender.sendMessage(plugin.getMessages().getSuccessColor() + plugin.getMessages().getWarpDeleted());
            }
            else
            {
                throw new SomethingWentWrong(plugin.getMessages().getWarpDoesntExists());
            }
            return;
        }

        Player player = (Player) sender;

        String name = args.getString(0);
        CustomWarp warp = plugin.getRegistry().getWarp(name);
        if(!warp.getOwner().equals(player.getUniqueId()))
        {
            throw new SomethingWentWrong("You're not owner of this warp!");
        }

        boolean result = plugin.getRegistry().unregister(warp);
        if(result)
        {
            ((Player)sender).spigot().sendMessage(plugin.getConfiguration().getMessageFormat(), new TextComponent(plugin.getMessages().getSuccessColor() + plugin.getMessages().getWarpDeleted()));
        }
        else
        {
            throw new SomethingWentWrong(plugin.getMessages().getWarpDoesntExists());
        }
    }
}

