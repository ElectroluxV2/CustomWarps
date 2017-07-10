package pl.insertt.customwarps.util;

import org.bukkit.Material;
import org.diorite.commons.math.DioriteRandomUtils;

import java.util.Random;

public class RandomUtils
{
    private static Random random = new Random();

    public static Material getRandomMaterial(Material[] limits)
    {
        return DioriteRandomUtils.getRandom(random, limits);
    }

    private RandomUtils() { }
}
