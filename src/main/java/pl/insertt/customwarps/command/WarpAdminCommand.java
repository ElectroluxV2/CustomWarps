package pl.insertt.customwarps.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.insertt.customwarps.CustomWarpsPlugin;
import pl.insertt.customwarps.command.framework.*;
import pl.insertt.customwarps.system.builder.ItemBuilder;
import pl.insertt.customwarps.system.gui.GuiItem;
import pl.insertt.customwarps.system.gui.GuiWindow;

public class WarpAdminCommand implements Command
{
    private final CustomWarpsPlugin plugin;

    public WarpAdminCommand(CustomWarpsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @CommandInfo(name = "warpadmin", description = "Warp admin command.", usage = "/warpadmin", permission = "customwarps.command.warpadmin", minArgs = 0, maxArgs = 0, playerOnly = true)
    public void execute(CommandSender sender, Arguments args) throws ArgumentParseException, SomethingWentWrong
    {
        Player player = (Player) sender;

        GuiWindow window = new GuiWindow(plugin.getMessages().getAdminMenuTitle(), 6);

        window.setCloseEvent(event -> window.unregister());

        GuiItem reload = new GuiItem(new ItemBuilder(Material.EYE_OF_ENDER, 1).setName(ChatColor.GOLD + "Reload data").setLore(ChatColor.GRAY + "Click me to reload data!").getItem(), event ->
        {
            try
            {
                plugin.getConfiguration().load();
                plugin.getDatabase().load();
                plugin.getMessages().load();
                player.spigot().sendMessage(plugin.getConfiguration().getMessageFormat(), new TextComponent(plugin.getMessages().getSuccessColor() + plugin.getMessages().getConfigLoaded()));
            }
            catch(Exception ex)
            {
                event.getWhoClicked().closeInventory();
                player.spigot().sendMessage(plugin.getConfiguration().getMessageFormat(), new TextComponent(plugin.getMessages().getErrorColor() + plugin.getMessages().getUnknownError()));
                ex.printStackTrace();
            }
        });

        GuiItem save = new GuiItem(new ItemBuilder(Material.ENDER_PEARL, 1).setName(ChatColor.GOLD + "Save data").setLore(ChatColor.GRAY + "Click me to save data!").getItem(), event ->
        {
            try
            {
                plugin.getConfiguration().save();
                plugin.getDatabase().save();
                plugin.getMessages().save();
                player.spigot().sendMessage(plugin.getConfiguration().getMessageFormat(), new TextComponent(plugin.getMessages().getSuccessColor() + plugin.getMessages().getConfigSaved()));
            }
            catch(Exception ex)
            {
                event.getWhoClicked().closeInventory();
                player.spigot().sendMessage(plugin.getConfiguration().getMessageFormat(), new TextComponent(plugin.getMessages().getErrorColor() + plugin.getMessages().getUnknownError()));
                ex.printStackTrace();
            }
        });

        window.setItem(11, reload);
        window.setItem(15, save);
        window.fill();
        window.show(player);

    }
}