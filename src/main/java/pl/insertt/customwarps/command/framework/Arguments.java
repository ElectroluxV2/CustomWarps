package pl.insertt.customwarps.command.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Arguments
{
    private final List<String> arguments;

    public Arguments(String[] arguments)
    {
        this.arguments = new ArrayList<>(Arrays.asList(arguments));
    }

    public int getSize()
    {
        return this.arguments.size();
    }

    public String getString(int index)
    {
        if (index >= this.arguments.size())
        {
            return "";
        }
        return this.arguments.get(index);
    }

    public boolean getBoolean(int index) throws ArgumentParseException
    {
        if (index >= this.arguments.size())
        {
            throw new ArgumentParseException("Argument can't be empty!");
        }
        try
        {
            return Boolean.parseBoolean(this.arguments.get(index));
        }
        catch (Exception e)
        {
            throw new ArgumentParseException(e);
        }
    }

    public int getInteger(int index) throws ArgumentParseException
    {
        if (index >= this.arguments.size())
        {
            throw new ArgumentParseException("Argument can't be empty!");
        }
        try
        {
            return Integer.parseInt(this.arguments.get(index));
        }
        catch (Exception e)
        {
            throw new ArgumentParseException(e);
        }
    }

    public double getDouble(int index) throws ArgumentParseException
    {
        if (index >= this.arguments.size())
        {
            throw new ArgumentParseException("Argument can't be empty!");
        }
        try
        {
            return Double.parseDouble(this.arguments.get(index));
        }
        catch (Exception e)
        {
            throw new ArgumentParseException(e);
        }
    }

    public List<String> getFrom(int start, int end) throws ArgumentParseException
    {
        int fixed = start - 1;

        if(fixed > getSize())
        {
            throw new ArgumentParseException("Start can't be bigger than size of arguments. (Report this to staff.)");
        }

        if(end > getSize())
        {
            return this.arguments.subList(fixed, getSize());
        }

        return this.arguments.subList(fixed, end);
    }

    public List<String> getArguments()
    {
        return new ArrayList<>(this.arguments);
    }

    public boolean areEmpty()
    {
        return this.arguments.isEmpty();
    }
}
