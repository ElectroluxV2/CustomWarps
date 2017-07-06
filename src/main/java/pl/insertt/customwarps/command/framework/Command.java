package pl.insertt.customwarps.command.framework;

import org.bukkit.command.CommandSender;

public interface Command
{
    void execute(CommandSender sender, Arguments args) throws ArgumentParseException;
}
