package pl.insertt.customwarps.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.insertt.customwarps.CustomWarpsPlugin;

public class AutosaveTask extends BukkitRunnable
{
    private final CustomWarpsPlugin plugin;

    public AutosaveTask(CustomWarpsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void run()
    {
        if(!plugin.getConfiguration().getAutosave())
        {
            this.cancel();
        }

        Bukkit.broadcastMessage(plugin.getMessages().getSecondColor() + plugin.getMessages().getAutosaveStart());

        try
        {
            plugin.getDatabase().setWarpList(plugin.getRegistry().getAllWarps());
            plugin.getDatabase().save();
            plugin.getConfiguration().save();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        Bukkit.broadcastMessage(plugin.getMessages().getSecondColor() + plugin.getMessages().getAutosaveComplete());

    }

}
