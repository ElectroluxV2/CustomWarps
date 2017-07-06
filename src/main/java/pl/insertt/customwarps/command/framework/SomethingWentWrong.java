package pl.insertt.customwarps.command.framework;

public class SomethingWentWrong extends Throwable
{
    public SomethingWentWrong()
    {
        super();
    }

    public SomethingWentWrong(String message)
    {
        super(message);
    }

    public SomethingWentWrong(String message, Throwable cause)
    {
        super(message, cause);
    }

    public SomethingWentWrong(Throwable cause)
    {
        super(cause);
    }
}
