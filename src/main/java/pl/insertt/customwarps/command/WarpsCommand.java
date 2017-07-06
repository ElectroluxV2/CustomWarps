package pl.insertt.customwarps.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.insertt.customwarps.CustomWarpsPlugin;
import pl.insertt.customwarps.command.framework.ArgumentParseException;
import pl.insertt.customwarps.command.framework.Arguments;
import pl.insertt.customwarps.command.framework.Command;
import pl.insertt.customwarps.command.framework.CommandInfo;
import pl.insertt.customwarps.util.FormatUtils;
import pl.insertt.customwarps.warp.api.CustomWarp;

public class WarpsCommand implements Command
{
    private final CustomWarpsPlugin plugin;

    public WarpsCommand(CustomWarpsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @CommandInfo(name = "warps", description = "Warp list command.", usage = "/warps", aliases = {"warplist", "warpslist"}, permission = "customwarps.command.warps", minArgs = 0, maxArgs = 0, playerOnly = true)
    public void execute(CommandSender sender, Arguments args) throws ArgumentParseException
    {
        Player player = (Player) sender;

        TextComponent start = new TextComponent(ChatColor.GOLD + "Warps: ");

        for(CustomWarp warp : plugin.getWarpRegistry().getAllWarps())
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
