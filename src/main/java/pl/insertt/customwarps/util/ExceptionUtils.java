package pl.insertt.customwarps.util;

public class ExceptionUtils
{
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> void sneakyThrow(Throwable t) throws T
    {
        throw (T) t;
    }

    private ExceptionUtils() { }
}
