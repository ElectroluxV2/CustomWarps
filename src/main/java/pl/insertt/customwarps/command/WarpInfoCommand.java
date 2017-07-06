package pl.insertt.customwarps.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
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
import pl.insertt.customwarps.warp.api.CustomWarp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WarpInfoCommand implements Command
{
    private final CustomWarpsPlugin plugin;

    public WarpInfoCommand(CustomWarpsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @CommandInfo(name = "warpinfo", description = "Warp info command.", usage = "/warpinfo <name>", aliases = {"winfo", "checkwarp"}, permission = "customwarps.command.warpinfo", minArgs = 1, maxArgs = 1)
    public void execute(CommandSender sender, Arguments args) throws ArgumentParseException
    {
        Player player = (Player) sender;
        String name = StringUtils.buildName(args.getFrom(1, 16));
        CustomWarp warp = plugin.getWarpRegistry().getWarp(name);
        List<String> applicablePlayers = new ArrayList<>();

        for(UUID uuid : warp.getApplicablePlayers())
        {
            applicablePlayers.add(PlayerUtils.getName(uuid));
        }

        BaseComponent component;

        if(sender.hasPermission("customwarps.command.warpinfo.admin") || sender.isOp())
        {
            if(warp.isModified())
            {
                component = new TextComponent(ChatColor.GOLD + "Warp: " + ChatColor.GRAY + warp.getName() + "\n"
                        + ChatColor.GOLD + "Owner: " + ChatColor.GRAY + PlayerUtils.getName(warp.getOwner()) + "\n"
                        + ChatColor.GOLD + "Location: " + ChatColor.GRAY + FormatUtils.formatLocation(warp.getLocation()) + "\n"
                        + ChatColor.GOLD + "Creation time: " + ChatColor.GRAY + FormatUtils.formatTime(warp.getCreationDate()) + "\n"
                        + ChatColor.GOLD + "Modified: " + ChatColor.GRAY + warp.isModified() + "\n"
                        + ChatColor.GOLD + "Modification time: " + ChatColor.GRAY + FormatUtils.formatTime(warp.getModificationDate()) + "\n"
                        + ChatColor.GOLD + "Who can teleport: " + ChatColor.GRAY + StringUtils.buildName(applicablePlayers) + "\n"
                        + ChatColor.GOLD + "Safe: " + ChatColor.GRAY + warp.isSafe());
            }
            else
            {
                component = new TextComponent(ChatColor.GOLD + "Warp: " + ChatColor.GRAY + warp.getName() + "\n"
                        + ChatColor.GOLD + "Owner: " + ChatColor.GRAY + PlayerUtils.getName(warp.getOwner()) + "\n"
                        + ChatColor.GOLD + "Location: " + ChatColor.GRAY + FormatUtils.formatLocation(warp.getLocation()) + "\n"
                        + ChatColor.GOLD + "Creation time: " + ChatColor.GRAY + FormatUtils.formatTime(warp.getCreationDate()) + "\n"
                        + ChatColor.GOLD + "Modified: " + ChatColor.GRAY + warp.isModified() + "\n"
                        + ChatColor.GOLD + "Who can teleport: " + ChatColor.GRAY + StringUtils.buildName(applicablePlayers) + "\n"
                        + ChatColor.GOLD + "Safe: " + ChatColor.GRAY + warp.isSafe());
            }
        }
        else
        {
            component = new TextComponent(ChatColor.GOLD + "Warp: " + ChatColor.GRAY + warp.getName() + "\n"
                    + ChatColor.GOLD + "Owner: " + ChatColor.GRAY + PlayerUtils.getName(warp.getOwner()) + "\n"
                    + ChatColor.GOLD + "Location: " + ChatColor.GRAY + FormatUtils.formatLocation(warp.getLocation()) + "\n"
                    + ChatColor.GOLD + "Who can teleport: " + ChatColor.GRAY +StringUtils.buildName(applicablePlayers) + "\n"
                    + ChatColor.GOLD + "Safe: " + ChatColor.GRAY + warp.isSafe());
        }
        player.spigot().sendMessage(component);
    }
}
