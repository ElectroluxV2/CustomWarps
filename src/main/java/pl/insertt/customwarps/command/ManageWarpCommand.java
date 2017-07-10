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
import pl.insertt.customwarps.system.gui.PredefinedGui;
import pl.insertt.customwarps.system.warp.api.CustomWarp;
import pl.insertt.customwarps.util.FormatUtils;
import pl.insertt.customwarps.util.MathUtil;

public class ManageWarpCommand implements Command
{
    private final CustomWarpsPlugin plugin;
    private final PredefinedGui gui;

    public ManageWarpCommand(CustomWarpsPlugin plugin, PredefinedGui gui)
    {
        this.plugin = plugin;
        this.gui = gui;
    }

    @CommandInfo(name = "managewarp",
                 description = "Warp management command.",
                 usage = "/managewarp",
                 aliases = {"mw", "changewarp", "managewarps", "changewarps", "warpmanage"},
                 permission = "customwarps.command.managewarp",
                 minArgs = 0,
                 maxArgs = 0,
                 playerOnly = true)
    public void execute(WarpCommandSender sender, Arguments args) throws ArgumentParseException, SomethingWentWrong
    {
        WarpPlayer player = (WarpPlayer) sender;
        int warpSize = MathUtil.roundToMultiply(plugin.getConfiguration().getMaxWarpsPerPlayer(), 9);
        GuiWindow window = new GuiWindow(ChatColor.RED + "Warp management menu", warpSize);
        window.setCloseEvent(event -> window.unregister());

        for(CustomWarp warp : plugin.getRegistry().getPlayerWarps(player.getUniqueId()))
        {
            if(warp.isModified())
            {
                GuiItem item = new GuiItem(new GuiItemBuilder(warp.getIcon(), 1)
                        .setName(ChatColor.GOLD + warp.getName())
                        .setLore(ChatColor.GRAY + "Location: " + FormatUtils.formatLocation(warp.getLocation()),
                                ChatColor.GRAY + "Creation time: " + FormatUtils.formatTime(warp.getCreationDate()),
                                ChatColor.GRAY + "Modified: " + warp.isModified(),
                                ChatColor.GRAY + "Modification time: " + FormatUtils.formatTime(warp.getModificationDate()))
                        .getItem(),
                        event ->
                        {
                            event.getWhoClicked().closeInventory();
                            GuiWindow w = gui.MANAGE_WARP_OPTIONS_GUI;
                            w.setItem(4, new GuiItem(new GuiItemBuilder(Material.NETHER_STAR, 1).setName(warp.getName()).setLore("I'm getting edited right now! :D").getItem(), null));
                            event.getWhoClicked().openInventory(w.getBukkitInventory());
                        });
                window.setToNextFree(item);
            }
            else
            {
                GuiItem item = new GuiItem(new GuiItemBuilder(warp.getIcon(), 1)
                        .setName(ChatColor.GOLD + warp.getName())
                        .setLore(ChatColor.GRAY + "Location: " + FormatUtils.formatLocation(warp.getLocation()),
                                ChatColor.GRAY + "Creation time: " + FormatUtils.formatTime(warp.getCreationDate()),
                                ChatColor.GRAY + "Modified: " + warp.isModified())
                        .getItem(),
                        event ->
                        {
                            event.getWhoClicked().closeInventory();
                            GuiWindow w = gui.MANAGE_WARP_OPTIONS_GUI;
                            w.setItem(4, new GuiItem(new GuiItemBuilder(Material.NETHER_STAR, 1).setName(warp.getName()).setLore("I'm getting edited right now! :D").getItem(), null));
                            event.getWhoClicked().openInventory(w.getBukkitInventory());
                        });
                window.setToNextFree(item);
            }
        }
        window.fill();
        window.show(player.bukkit());
    }
}
