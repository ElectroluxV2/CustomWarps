package pl.insertt.customwarps.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import pl.insertt.customwarps.CustomWarpsPlugin;
import pl.insertt.customwarps.system.command.ArgumentParseException;
import pl.insertt.customwarps.system.command.Arguments;
import pl.insertt.customwarps.system.command.SomethingWentWrong;
import pl.insertt.customwarps.system.command.api.Command;
import pl.insertt.customwarps.system.command.api.CommandInfo;
import pl.insertt.customwarps.system.command.api.WarpCommandSender;
import pl.insertt.customwarps.system.command.api.WarpPlayer;
import pl.insertt.customwarps.system.warp.api.CustomWarp;
import pl.insertt.customwarps.util.FormatUtils;
import pl.insertt.customwarps.util.PlayerUtils;
import pl.insertt.customwarps.util.StringUtils;

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

    @CommandInfo(name = "warpinfo",
                 description = "Warp info command.",
                 usage = "/warpinfo <name>",
                 aliases = {"winfo", "checkwarp"},
                 permission = "customwarps.command.warpinfo",
                 minArgs = 1,
                 maxArgs = 1)
    public void execute(WarpCommandSender sender, Arguments args) throws ArgumentParseException, SomethingWentWrong
    {
        String name = StringUtils.buildName(args.getFrom(1, 16));
        CustomWarp warp = plugin.getRegistry().getWarp(name);

        if(warp == null)
        {
            throw new SomethingWentWrong(plugin.getMessages().getErrorColor() + plugin.getMessages().getWarpDoesntExists());
        }

        List<String> applicablePlayers = new ArrayList<>();

        for(UUID uuid : warp.getApplicablePlayers())
        {
            applicablePlayers.add(PlayerUtils.getName(uuid));
        }

        BaseComponent component;

        if(sender.hasPermission("customwarps.command.warpinfo.admin") || sender.isOp() || (sender.isPlayer() && warp.getOwner().equals(((WarpPlayer)sender).getUniqueId())))
        {
            component = new TextComponent(ChatColor.GOLD + "Warp: " + ChatColor.GRAY + warp.getName() + "\n"
                    + ChatColor.GOLD + "Owner: " + ChatColor.GRAY + PlayerUtils.getName(warp.getOwner()) + "\n"
                    + ChatColor.GOLD + "Location: " + ChatColor.GRAY + FormatUtils.formatLocation(warp.getLocation()) + "\n"
                    + ChatColor.GOLD + "Creation time: " + ChatColor.GRAY + FormatUtils.formatTime(warp.getCreationDate()) + "\n"
                    + ChatColor.GOLD + "Modified: " + ChatColor.GRAY + warp.isModified() + "\n"
                    + (warp.getModificationDate().getTime() == 0L ? "" : ChatColor.GOLD + "Modification time: " + ChatColor.GRAY + FormatUtils.formatTime(warp.getModificationDate()) + "\n")
                    + ChatColor.GOLD + "Who can teleport: " + ChatColor.GRAY + StringUtils.buildName(applicablePlayers) + "\n"
                    + ChatColor.GOLD + "Safe: " + ChatColor.GRAY + warp.isSafe());
        }
        else
        {
            component = new TextComponent(ChatColor.GOLD + "Warp: " + ChatColor.GRAY + warp.getName() + "\n"
                    + ChatColor.GOLD + "Owner: " + ChatColor.GRAY + PlayerUtils.getName(warp.getOwner()) + "\n"
                    + ChatColor.GOLD + "Location: " + ChatColor.GRAY + FormatUtils.formatLocation(warp.getLocation()) + "\n"
                    + ChatColor.GOLD + "Who can teleport: " + ChatColor.GRAY +StringUtils.buildName(applicablePlayers) + "\n"
                    + ChatColor.GOLD + "Safe: " + ChatColor.GRAY + warp.isSafe());
        }
        sender.sendMessage(component);
    }
}
