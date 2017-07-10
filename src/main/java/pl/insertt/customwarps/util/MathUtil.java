package pl.insertt.customwarps.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil
{
    public static int roundToMultiply(int num, int multiple)
    {
        return multiple * (int) Math.ceil((float) num / (float) multiple);
    }

    public static double roundToPlaces(double value, int places)
    {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

    private MathUtil() { }
}
