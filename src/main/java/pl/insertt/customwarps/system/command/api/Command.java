package pl.insertt.customwarps.system.command.api;

import pl.insertt.customwarps.system.command.ArgumentParseException;
import pl.insertt.customwarps.system.command.Arguments;
import pl.insertt.customwarps.system.command.SomethingWentWrong;

public interface Command
{
    void execute(WarpCommandSender sender, Arguments args) throws ArgumentParseException, SomethingWentWrong;

    default <T extends Throwable> void sneakyThrow(Throwable t) throws T
    {
        throw (T) t;
    }
}
