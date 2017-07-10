package pl.insertt.customwarps;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.diorite.config.ConfigManager;
import org.diorite.config.serialization.Serialization;
import pl.insertt.customwarps.command.*;
import pl.insertt.customwarps.system.command.CommandRegistry;
import pl.insertt.customwarps.data.WarpConfig;
import pl.insertt.customwarps.data.WarpDatabase;
import pl.insertt.customwarps.data.WarpMessages;
import pl.insertt.customwarps.system.gui.GuiListener;
import pl.insertt.customwarps.system.command.api.WarpConsole;
import pl.insertt.customwarps.system.command.WarpConsoleImpl;
import pl.insertt.customwarps.system.command.WarpPlayerCreator;
import pl.insertt.customwarps.system.gui.PredefinedGui;
import pl.insertt.customwarps.system.warp.CustomWarpFactory;
import pl.insertt.customwarps.system.warp.CustomWarpRegistry;
import pl.insertt.customwarps.system.warp.CustomWarpImpl;
import pl.insertt.customwarps.task.AutosaveTask;

import java.io.File;

public class CustomWarpsPlugin extends JavaPlugin
{
    private final File warpDatabaseFile = new File(getDataFolder(), "database.yml");
    private final File warpConfigFile = new File(getDataFolder(), "config.yml");
    private final File warpMessagesFile = new File(getDataFolder(), "messages.yml");

    private CustomWarpRegistry warpRegistry;
    private CustomWarpFactory warpFactory;
    private WarpDatabase warpDatabase;
    private WarpConfig warpConfig;
    private WarpMessages warpMessages;
    private CommandRegistry commandRegistry;
    private WarpConsole warpConsole;
    private PredefinedGui guiList;

    @Override
    public void onEnable()
    {
        this.warpRegistry = new CustomWarpRegistry(this);
        this.warpFactory = new CustomWarpFactory(this);
        this.warpDatabase = ConfigManager.createInstance(WarpDatabase.class);
        this.warpDatabase.bindFile(warpDatabaseFile);
        Serialization instance = Serialization.getInstance();
        instance.registerSerializable(CustomWarpImpl.class);
        this.warpConfig = ConfigManager.createInstance(WarpConfig.class);
        this.warpConfig.bindFile(warpConfigFile);

        this.warpMessages = ConfigManager.createInstance(WarpMessages.class);
        this.warpMessages.bindFile(warpMessagesFile);
        this.commandRegistry = new CommandRegistry(this);
        this.warpConsole = new WarpConsoleImpl();

        try
        {
            this.warpDatabase.load();
        }
        catch(Exception ex)
        {
            this.warpDatabase.metadata().put("crash-status", true);
        }

        this.warpConfig.load();
        this.warpMessages.load();

        this.warpConfig.save();
        this.warpMessages.save();

        this.warpRegistry.loadWarps(warpDatabase.getWarpList());

        this.guiList = new PredefinedGui(this);

        registerCommands();
        registerListeners(new GuiListener(), new WarpPlayerCreator(this));

        startTask();
    }

    @Override
    public void onDisable()
    {
        if(warpDatabase.metadata().get("crash-status") == null)
        {
            warpDatabase.setWarpList(warpRegistry.getAllWarps());
            warpDatabase.save();
        }
        warpConfig.setMessageFormat(this.warpConfig.getMessageFormat());
        warpConfig.save();
    }

    private void registerCommands()
    {
        commandRegistry.register(new CreateWarpCommand(this), new WarpsCommand(this), new WarpCommand(this), new WarpInfoCommand(this), new WarpAdminCommand(this), new ManageWarpCommand(this, guiList), new DeleteWarpCommand(this));
    }

    private void registerListeners(Listener... listeners)
    {
        final PluginManager pm = Bukkit.getPluginManager();

        for (Listener listener : listeners)
        {
            pm.registerEvents(listener, this);
        }
    }

    private void startTask()
    {
        if (this.warpConfig.getAutosave())
        {
            new AutosaveTask(this).runTaskTimer(this, this.warpConfig.getAutosaveInterval() * 20L, this.warpConfig.getAutosaveInterval() * 20L);
        }
    }

    public CustomWarpFactory getFactory()
    {
        return warpFactory;
    }

    public CustomWarpRegistry getRegistry()
    {
        return warpRegistry;
    }

    public WarpDatabase getDatabase()
    {
        return warpDatabase;
    }

    public WarpConfig getConfiguration()
    {
        return warpConfig;
    }

    public WarpMessages getMessages()
    {
        return warpMessages;
    }

    public WarpConsole getConsole()
    {
        return warpConsole;
    }

    public PredefinedGui getGuiList()
    {
        return guiList;
    }
}
