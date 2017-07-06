package pl.insertt.customwarps.command;

import org.bukkit.command.CommandSender;
import pl.insertt.customwarps.command.framework.ArgumentParseException;
import pl.insertt.customwarps.command.framework.Arguments;
import pl.insertt.customwarps.command.framework.Command;
import pl.insertt.customwarps.command.framework.CommandInfo;

public class WarpAdminCommand implements Command
{
    @CommandInfo(name = "warpadmin", description = "Warp admin command.", usage = "/warpadmin <flag> <value>", permission = "customwarps.command.warpadmin", minArgs = 0, maxArgs = 2)
    public void execute(CommandSender sender, Arguments args) throws ArgumentParseException
    {
        if(args.getSize() == 0)
        {

        }
    }
}
