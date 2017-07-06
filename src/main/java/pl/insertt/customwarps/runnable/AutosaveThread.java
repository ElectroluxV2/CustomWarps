package pl.insertt.customwarps.runnable;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import pl.insertt.customwarps.CustomWarpsPlugin;

public class AutosaveThread implements Runnable
{
    private final CustomWarpsPlugin plugin;

    public AutosaveThread(CustomWarpsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void run()
    {
        Bukkit.broadcastMessage(ChatColor.GRAY + "Autosaving warps...");

        try
        {
            plugin.getWarpDatabase().setWarpList(plugin.getWarpRegistry().getAllWarps());
            plugin.getWarpDatabase().save();
            plugin.getWarpConfig().save();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        Bukkit.broadcastMessage(ChatColor.GRAY + "Autosaving warps completed!");

    }
}
