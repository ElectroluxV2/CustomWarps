package pl.insertt.customwarps.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.insertt.customwarps.CustomWarpsPlugin;
import pl.insertt.customwarps.command.framework.*;
import pl.insertt.customwarps.system.warp.api.CustomWarp;
import pl.insertt.customwarps.util.FormatUtils;

public class WarpsCommand implements Command
{
    private final CustomWarpsPlugin plugin;

    public WarpsCommand(CustomWarpsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @CommandInfo(name = "warps", description = "Warp list command.", usage = "/warps", aliases = {"warplist", "warpslist"}, permission = "customwarps.command.warps", minArgs = 0, maxArgs = 0, playerOnly = true)
    public void execute(CommandSender sender, Arguments args) throws ArgumentParseException, SomethingWentWrong
    {
        Player player = (Player) sender;
        //String type = plugin.getConfiguration().getWarpListType();
        //int menus = (int) Math.ceil(plugin.getRegistry().getWarpCount() / 53);

        /*if(type.equalsIgnoreCase("gui")) //TODO: Warps in GUI.
        {
            GuiWindow window = new GuiWindow(ChatColor.RED + "Warps", 6);
            window.setCloseEvent(event -> window.unregister());

        }
        else
        {*/
        TextComponent start = new TextComponent(plugin.getMessages().getMainColor() + plugin.getMessages().getAvailableWarps());

        for(CustomWarp warp : plugin.getRegistry().getAllWarps())
        {
            TextComponent comp = new TextComponent(ChatColor.GRAY + warp.getName() + ", ");
            comp.setHoverEvent(
                    new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder("Name: " + warp.getName() + "\n").color(ChatColor.GRAY)
                                    .append("Owner: " + Bukkit.getPlayer(warp.getOwner()).getName() + "\n").color(ChatColor.GRAY)
                                    .append("Location: " + FormatUtils.formatLocation(warp.getLocation())).color(ChatColor.GRAY)
                                    .create()));
            start.addExtra(comp);
        }
        player.spigot().sendMessage(start);
    }

}
