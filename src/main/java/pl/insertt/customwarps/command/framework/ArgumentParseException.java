package pl.insertt.customwarps.command.framework;

public class ArgumentParseException extends Throwable
{
    public ArgumentParseException()
    {
        super();
    }

    public ArgumentParseException(String message)
    {
        super(message);
    }

    public ArgumentParseException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ArgumentParseException(Throwable cause)
    {
        super(cause);
    }
}
