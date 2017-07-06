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
import pl.insertt.customwarps.util.PlayerUtils;
import pl.insertt.customwarps.util.StringUtils;
import pl.insertt.customwarps.warp.CustomWarpConstants;
import pl.insertt.customwarps.warp.api.CustomWarp;

public class CreateWarpCommand implements Command
{
    private final CustomWarpsPlugin plugin;
    private final CustomWarpConstants constants;

    public CreateWarpCommand(CustomWarpsPlugin plugin, CustomWarpConstants constants)
    {
        this.plugin = plugin;
        this.constants = constants;
    }

    @CommandInfo(name = "createwarp", description = "Command to create warps.", usage = "/createwarp <name>", aliases = {"setwarp", "setcustomwarp", "createcustomwarp"}, permission = "customwarps.command.createwarp", minArgs = 1, maxArgs = 16, playerOnly = true)
    public void execute(CommandSender sender, Arguments args) throws ArgumentParseException
    {
        final Player player = (Player) sender;
        String name = StringUtils.buildName(args.getFrom(1, 16));
        CustomWarp warp = plugin.getWarpFactory().createCustomWarp(player.getUniqueId(), name, player.getLocation());

        if(warp == null)
        {
            return;
        }

        TextComponent message = new TextComponent(ChatColor.GREEN + "A new warp has been created. (Hover me!)");
        message.setHoverEvent(
                new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder("Name: " + name + "\n").color(ChatColor.GRAY)
                                .append("Owner: " + Bukkit.getPlayer(warp.getOwner()).getName() + "\n").color(ChatColor.GRAY)
                                .append("Location: " + FormatUtils.formatLocation(warp.getLocation())).color(ChatColor.GRAY)
                                .create()));

        PlayerUtils.broadcast(constants.MESSAGE_TYPE, message);
    }
}
