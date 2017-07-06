package pl.insertt.customwarps.util;

import org.bukkit.Location;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FormatUtils
{
    private FormatUtils()
    {

    }

    public static String formatLocation(Location location)
    {
        final StringBuilder buf = new StringBuilder();

        buf.append(location.getWorld().getName()).append(", ").append(round(location.getX(), 2)).append(", ").append(round(location.getY(), 2)).append(", ").append(round(location.getZ(), 2));

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

    public static Instant fromLong(long time)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);

        return calendar.toInstant();
    }

    public static double round(double value, int places)
    {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

}
