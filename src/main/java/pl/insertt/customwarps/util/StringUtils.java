package pl.insertt.customwarps.util;

import org.bukkit.ChatColor;

import java.util.List;

public class StringUtils
{
    private StringUtils()
    {

    }

    public static String buildName(List<String> arguments)
    {
        final StringBuilder buf = new StringBuilder();

        for(String str : arguments)
        {
            buf.append(str).append(" ");
        }
        return buf.toString().trim();
    }

    public static String colorize(String string)
    {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
