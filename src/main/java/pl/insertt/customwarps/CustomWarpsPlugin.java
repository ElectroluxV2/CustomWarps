package pl.insertt.customwarps;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.diorite.config.ConfigManager;
import org.diorite.config.serialization.Serialization;
import pl.insertt.customwarps.command.*;
import pl.insertt.customwarps.command.framework.CommandRegistry;
import pl.insertt.customwarps.data.WarpConfig;
import pl.insertt.customwarps.data.WarpDatabase;
import pl.insertt.customwarps.runnable.AutosaveThread;
import pl.insertt.customwarps.warp.CustomWarpFactory;
import pl.insertt.customwarps.warp.CustomWarpRegistry;
import pl.insertt.customwarps.warp.CustomWarpService;

import java.io.File;

public class CustomWarpsPlugin extends JavaPlugin
{
    private final File warpDatabaseFile = new File(getDataFolder(), "database.yml");
    private final File warpConfigFile = new File(getDataFolder(), "config.yml");

    private CustomWarpRegistry warpRegistry;
    private CustomWarpFactory warpFactory;
    private WarpDatabase warpDatabase;
    private WarpConfig warpConfig;
    private CommandRegistry commandRegistry;

    @Override
    public void onEnable()
    {
        this.warpRegistry = new CustomWarpRegistry(this);
        this.warpFactory = new CustomWarpFactory(this);
        this.warpDatabase = ConfigManager.createInstance(WarpDatabase.class);
        this.warpDatabase.bindFile(warpDatabaseFile);
        Serialization instance = Serialization.getInstance();
        instance.registerSerializable(CustomWarpService.class);
        this.warpConfig = ConfigManager.createInstance(WarpConfig.class);
        this.warpConfig.bindFile(warpConfigFile);
        this.commandRegistry = new CommandRegistry(this);

        warpDatabase.load();
        warpConfig.load();

        warpConfig.save();

        warpRegistry.loadWarps(warpDatabase.getWarpList());

        registerCommands();
        registerEvents();

        startRunnable();
    }

    @Override
    public void onDisable()
    {
        warpDatabase.setWarpList(warpRegistry.getAllWarps());
        warpDatabase.save();
        warpConfig.setMessageFormat(this.warpConfig.getMessageFormat());
        warpConfig.save();
    }

    private void registerCommands()
    {
        commandRegistry.register(new CreateWarpCommand(this), new WarpsCommand(this), new WarpCommand(this), new WarpInfoCommand(this), new WarpAdminCommand(this));
    }

    private void registerEvents(Listener... listeners)
    {
        final PluginManager pm = Bukkit.getPluginManager();

        for(Listener listener : listeners)
        {
            pm.registerEvents(listener, this);
        }
    }

    private void startRunnable()
    {
        if(this.warpConfig.getAutosave())
        {
            Bukkit.getScheduler().runTaskTimerAsynchronously(this, new AutosaveThread(this), this.warpConfig.getAutosaveInterval() * 20L, this.warpConfig.getAutosaveInterval() * 20L);
        }
    }

    public CustomWarpFactory getWarpFactory()
    {
        return warpFactory;
    }

    public CustomWarpRegistry getWarpRegistry()
    {
        return warpRegistry;
    }

    public WarpDatabase getWarpDatabase()
    {
        return warpDatabase;
    }

    public WarpConfig getWarpConfig()
    {
        return warpConfig;
    }

}
