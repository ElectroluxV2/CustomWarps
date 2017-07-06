package pl.insertt.customwarps.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.insertt.customwarps.CustomWarpsPlugin;
import pl.insertt.customwarps.command.framework.*;
import pl.insertt.customwarps.system.warp.api.CustomWarp;
import pl.insertt.customwarps.util.FormatUtils;
import pl.insertt.customwarps.util.PlayerUtils;
import pl.insertt.customwarps.util.StringUtils;

public class CreateWarpCommand implements Command
{
    private final CustomWarpsPlugin plugin;

    public CreateWarpCommand(CustomWarpsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @CommandInfo(name = "createwarp", description = "Command to create warps.", usage = "/createwarp <name>", aliases = {"setwarp", "setcustomwarp", "createcustomwarp"}, permission = "customwarps.command.createwarp", minArgs = 1, maxArgs = 16, playerOnly = true)
    public void execute(CommandSender sender, Arguments args) throws ArgumentParseException, SomethingWentWrong
    {
        final Player player = (Player) sender;
        String name = StringUtils.buildName(args.getFrom(1, 16));
        Material icon = player.getInventory().getItemInMainHand().getType();
        CustomWarp warp = plugin.getFactory().createCustomWarp(player.getUniqueId(), name, player.getLocation(), icon);

        if(warp == null)
        {
            return;
        }

        TextComponent message = new TextComponent(plugin.getMessages().getBroadcastColor() + plugin.getMessages().getWarpCreated());
        message.setHoverEvent(
                new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder("Name: " + name + "\n").color(ChatColor.GRAY)
                                .append("Owner: " + Bukkit.getPlayer(warp.getOwner()).getName() + "\n").color(ChatColor.GRAY)
                                .append("Location: " + FormatUtils.formatLocation(warp.getLocation())).color(ChatColor.GRAY)
                                .create()));

        PlayerUtils.broadcast(plugin.getConfiguration().getMessageFormat(), message);
    }
}
