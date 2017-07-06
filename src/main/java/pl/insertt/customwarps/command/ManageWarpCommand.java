package pl.insertt.customwarps.command;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.insertt.customwarps.CustomWarpsPlugin;
import pl.insertt.customwarps.command.framework.*;
import pl.insertt.customwarps.system.builder.ItemBuilder;
import pl.insertt.customwarps.system.gui.GuiItem;
import pl.insertt.customwarps.system.gui.GuiWindow;
import pl.insertt.customwarps.system.warp.api.CustomWarp;
import pl.insertt.customwarps.util.FormatUtils;
import pl.insertt.customwarps.util.MathUtil;

public class ManageWarpCommand implements Command
{
    private final CustomWarpsPlugin plugin;

    public ManageWarpCommand(CustomWarpsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @CommandInfo(name = "managewarp", description = "Warp management command.", usage = "/managewarp", permission = "customwarps.command.managewarp", minArgs = 0, maxArgs = 0, playerOnly = true)
    public void execute(CommandSender sender, Arguments args) throws ArgumentParseException, SomethingWentWrong
    {
        Player player = (Player) sender;
        int warpSize = MathUtil.roundToMultiply(plugin.getConfiguration().getMaxWarpsPerPlayer(), 9);
        GuiWindow window = new GuiWindow(ChatColor.RED + "Warp management menu", warpSize);
        window.setCloseEvent(event -> window.unregister());

        for(CustomWarp warp : plugin.getRegistry().getPlayerWarps(player.getUniqueId()))
        {
            if(warp.isModified())
            {
                GuiItem item = new GuiItem(new ItemBuilder(warp.getIcon(), 1)
                        .setName(ChatColor.GOLD + warp.getName())
                        .setLore(ChatColor.GRAY + "Location: " + FormatUtils.formatLocation(warp.getLocation()),
                                ChatColor.GRAY + "Creation time: " + FormatUtils.formatTime(warp.getCreationDate()),
                                ChatColor.GRAY + "Modified: " + warp.isModified(),
                                ChatColor.GRAY + "Modification time: " + FormatUtils.formatTime(warp.getModificationDate()))
                        .getItem(),
                        event ->
                        {
                            //TODO
                        });
                window.setToNextFree(item);
            }
            else
            {
                GuiItem item = new GuiItem(new ItemBuilder(warp.getIcon(), 1)
                        .setName(ChatColor.GOLD + warp.getName())
                        .setLore(ChatColor.GRAY + "Location: " + FormatUtils.formatLocation(warp.getLocation()),
                                ChatColor.GRAY + "Creation time: " + FormatUtils.formatTime(warp.getCreationDate()),
                                ChatColor.GRAY + "Modified: " + warp.isModified())
                        .getItem(),
                        event ->
                        {
                            //TODO
                        });
                window.setToNextFree(item);
            }
        }
        window.fill();
        window.show(player);
    }
}
