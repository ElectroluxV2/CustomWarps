package pl.insertt.customwarps.util;

import org.bukkit.Location;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FormatUtils
{
    public static String formatLocation(Location location)
    {
        final StringBuilder buf = new StringBuilder();

        buf.append(location.getWorld().getName()).append(", ").append(MathUtil.roundToPlaces(location.getX(), 2)).append(", ").append(MathUtil.roundToPlaces(location.getY(), 2)).append(", ").append(MathUtil.roundToPlaces(location.getZ(), 2));

        return buf.toString();
    }

    public static String formatTime(Date date)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        return dateFormat.format(calendar.getTime());
    }

    public static String formatTime(long time)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        return dateFormat.format(calendar.getTime());
    }

    public static Date fromLong(long time)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);

        return Date.from(calendar.toInstant());
    }

    private FormatUtils() { }
}
