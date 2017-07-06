package pl.insertt.customwarps.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import pl.insertt.customwarps.CustomWarpsPlugin;
import pl.insertt.customwarps.command.framework.ArgumentParseException;
import pl.insertt.customwarps.command.framework.Arguments;
import pl.insertt.customwarps.command.framework.Command;
import pl.insertt.customwarps.command.framework.CommandInfo;
import pl.insertt.customwarps.warp.api.CustomWarpFlag;

public class WarpAdminCommand implements Command
{
    private final CustomWarpsPlugin plugin;

    public WarpAdminCommand(CustomWarpsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @CommandInfo(name = "warpadmin", description = "Warp admin command.", usage = "/warpadmin <flag> <value>", permission = "customwarps.command.warpadmin", minArgs = 0, maxArgs = 2)
    public void execute(CommandSender sender, Arguments args) throws ArgumentParseException
    {
        if(args.getSize() == 0)
        {
            TextComponent start = new TextComponent(ChatColor.GOLD + "Available flags:\n");

            for(CustomWarpFlag flag : CustomWarpFlag.values())
            {
                TextComponent comp = new TextComponent(ChatColor.GRAY + flag.name().toLowerCase() + "\n");
                comp.setClickEvent(
                        new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/warpadmin " + flag.name().toLowerCase()));
                start.addExtra(comp);
            }

            sender.spigot().sendMessage(start);
        }
        else if(args.getSize() == 1)
        {
            switch(CustomWarpFlag.getByName(args.getString(0)))
            {
                case SAVE:
                    plugin.getWarpConfig().save();
                    plugin.getWarpDatabase().save();
                    sender.sendMessage(ChatColor.GREEN + "Action done.");
                    break;
                case LOAD:
                    plugin.reloadConfig();
                    plugin.getWarpDatabase().load();
                    sender.sendMessage(ChatColor.GREEN + "Action done.");
                    break;
                case MESSAGETYPE:
                    sender.sendMessage(ChatColor.RED + "You need to specify value for this flag.");
                    break;
                case AUTOSAVE_INTERVAL:
                    sender.sendMessage(ChatColor.RED + "You need to specify value for this flag.");
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + "That flag doesn't exists (or less arguments than needed)!");
                    break;
            }
        }
        else if(args.getSize() == 2)
        {
            switch(CustomWarpFlag.getByName(args.getString(0)))
            {
                case MESSAGETYPE:
                    plugin.getWarpConfig().setMessageFormat(ChatMessageType.valueOf(args.getString(1).toUpperCase()));
                    sender.sendMessage(ChatColor.GREEN + "Action done.");
                    break;
                case AUTOSAVE:
                    plugin.getWarpConfig().setAutosave(args.getBoolean(1));
                    sender.sendMessage(ChatColor.GREEN + "Action done.");
                    break;
                case AUTOSAVE_INTERVAL:
                    plugin.getWarpConfig().setAutosaveInterval(args.getInteger(1));
                    sender.sendMessage(ChatColor.GREEN + "Action done.");
                    break;
                case GET:
                    sender.sendMessage(ChatColor.GOLD + args.getString(1) + ": " + ChatColor.GRAY + plugin.getWarpConfig().get(args.getString(1)).toString());
                    break;
                case SHOW:
                    //TODO: implementation of show.
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + "That flag doesn't exists (or too much arguments than needed)!");
                    break;
            }
        }
    }
}
