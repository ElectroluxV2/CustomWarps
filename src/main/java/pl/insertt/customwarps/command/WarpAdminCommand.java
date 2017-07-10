package pl.insertt.customwarps.command;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import pl.insertt.customwarps.CustomWarpsPlugin;
import pl.insertt.customwarps.system.command.ArgumentParseException;
import pl.insertt.customwarps.system.command.Arguments;
import pl.insertt.customwarps.system.command.SomethingWentWrong;
import pl.insertt.customwarps.system.command.api.Command;
import pl.insertt.customwarps.system.command.api.CommandInfo;
import pl.insertt.customwarps.system.command.api.WarpCommandSender;
import pl.insertt.customwarps.system.command.api.WarpPlayer;
import pl.insertt.customwarps.system.gui.GuiItem;
import pl.insertt.customwarps.system.gui.GuiItemBuilder;
import pl.insertt.customwarps.system.gui.GuiWindow;

public class WarpAdminCommand implements Command
{
    private final CustomWarpsPlugin plugin;

    public WarpAdminCommand(CustomWarpsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @CommandInfo(name = "warpadmin",
                 description = "Warp admin command.",
                 usage = "/warpadmin",
                 permission = "customwarps.command.warpadmin",
                 minArgs = 0,
                 maxArgs = 0,
                 playerOnly = true)
    public void execute(WarpCommandSender sender, Arguments args) throws ArgumentParseException, SomethingWentWrong
    {
        final WarpPlayer player = (WarpPlayer) sender;
        GuiWindow window = new GuiWindow(plugin.getMessages().getAdminMenuTitle(), 6);
        window.setCloseEvent(event -> window.unregister());

        GuiItem reload = new GuiItem(new GuiItemBuilder(Material.EYE_OF_ENDER, 1).setName(ChatColor.GOLD + "Reload data").setLore(ChatColor.GRAY + "Click me to reload data!").getItem(), event ->
        {
            try
            {
                plugin.getConfiguration().load();
                plugin.getDatabase().load();
                plugin.getRegistry().loadWarps(plugin.getDatabase().getWarpList());
                plugin.getMessages().load();
                player.sendDependMessage(plugin.getMessages().getSuccessColor() + plugin.getMessages().getConfigLoaded());
            }
            catch(Exception ex)
            {
                event.getWhoClicked().closeInventory();
                sneakyThrow(new SomethingWentWrong(plugin.getMessages().getUnknownError()));
                ex.printStackTrace();
            }
        });

        GuiItem save = new GuiItem(new GuiItemBuilder(Material.ENDER_PEARL, 1).setName(ChatColor.GOLD + "Save data").setLore(ChatColor.GRAY + "Click me to save data!").getItem(), event ->
        {
            try
            {
                plugin.getConfiguration().save();
                plugin.getDatabase().setWarpList(plugin.getRegistry().getAllWarps());
                plugin.getDatabase().save();
                plugin.getMessages().save();
                player.sendDependMessage(plugin.getMessages().getSuccessColor() + plugin.getMessages().getConfigSaved());
            }
            catch(Exception ex)
            {
                event.getWhoClicked().closeInventory();
                sneakyThrow(new SomethingWentWrong(plugin.getMessages().getUnknownError()));
                ex.printStackTrace();
            }
        });

        window.setItem(11, reload);
        window.setItem(15, save);
        window.fill();
        window.show(player.bukkit());
    }
}