package pl.insertt.customwarps.data;

import net.md_5.bungee.api.ChatColor;
import org.diorite.config.Config;
import org.diorite.config.annotations.Comment;

public interface WarpMessages extends Config
{
    @Comment("Main chat color, used in prefixes etc.")
    default ChatColor getMainColor()
    {
        return ChatColor.GOLD;
    }

    @Comment("Second color, used in detail of messages.")
    default ChatColor getSecondColor()
    {
        return ChatColor.GRAY;
    }

    @Comment("Color used when information like created warp is being broadcasted.")
    default ChatColor getBroadcastColor()
    {
        return ChatColor.GREEN;
    }

    @Comment("Color used in success messages.")
    default ChatColor getSuccessColor()
    {
        return ChatColor.GREEN;
    }

    @Comment("Color used in warning messages.")
    default ChatColor getWarningColor()
    {
        return ChatColor.YELLOW;
    }

    @Comment("Color used in error messages.")
    default ChatColor getErrorColor()
    {
        return ChatColor.RED;
    }

    @Comment("Message displayed when something went wrong ( ͡° ͜ʖ ͡°)")
    default String getSomethingWrong()
    {
        return "Something went wrong: ";
    }

    @Comment("Message displayed when warp has been created.")
    default String getWarpCreated()
    {
        return "Warp has been created. (Hover me!)";
    }

    @Comment("Message displayed when player has been teleported onto warp.")
    default String getWarpTeleport()
    {
        return "Teleported onto warp: ";
    }

    @Comment("Message displayed when player aborted unsafe warp teleport.")
    default String getTeleportAbort()
    {
        return "Teleport aborted.";
    }

    @Comment("Message displayed with /warps command.")
    default String getAvailableWarps()
    {
        return "Available warps: ";
    }

    @Comment("Message displayed when database doesn't contain any warps while executing /warps command.")
    default String getNoAvailableWarps()
    {
        return "No available warps.";
    }

    @Comment("Message displayed when warp is not safe.")
    default String getUnsafeWarp()
    {
        return "Teleport to this warp isn't safe, do you want to teleport? \nWe will teleport you in 3 seconds, if u want to abort, move.";
    }

    @Comment("Message displayed when someone tried to create existing warp with same name.")
    default String getWarpAlreadyExists()
    {
        return "Warp with this name already exists!";
    }

    @Comment("Message displayed when someone tried to delete non-existing warp or want to teleport onto non-existing warp.")
    default String getWarpDoesntExists()
    {
        return "This warp doesn't exists.";
    }

    @Comment("Message displayed when someone tried to delete warp without permission (not owner).")
    default String getNotWarpOwner()
    {
        return "You're not owner of this warp.";
    }

    @Comment("Message displayed when warp has been deleted.")
    default String getWarpDeleted()
    {
        return "Warp deleted successufully.";
    }

    @Comment("Message displayed when config has been loaded.")
    default String getConfigLoaded()
    {
        return "Configuration has been loaded successufully.";
    }

    @Comment("Message displayed when config has been saved.")
    default String getConfigSaved()
    {
        return "Configuration has been saved successufully.";
    }

    @Comment("Message displayed when operation throwed unknown error.")
    default String getUnknownError()
    {
        return "An error occured. See console.";
    }

    @Comment("Title of /managewarp command gui.")
    default String getManageWarpTitle()
    {
        return "Warp management menu";
    }

    @Comment("Title of /warpadmin command gui.")
    default String getAdminMenuTitle()
    {
        return "Admin management menu";
    }

    @Comment("Message displayed when someone used command without keeping syntax.")
    default String getUsage()
    {
        return "Usage: ";
    }

    @Comment("Message displayed when console (or commandblock) used command only for players.")
    default String getPlayerOnly()
    {
        return "This command is only for players.";
    }

    @Comment("Message displayed when someone tried to use command without permissions.")
    default String getNoPermission()
    {
        return "You do not have enough permissions to do that.";
    }

    @Comment("Message displayed when autosave task started.")
    default String getAutosaveStart()
    {
        return "Autosaving warps...";
    }

    @Comment("Message displayed when autosave task has been completed.")
    default String getAutosaveComplete()
    {
        return "Autosave warps complete!";
    }

    @Comment("Message displayed when player has been added to applicable players of warp")
    default String getApplicablePlayerAdded()
    {
        return "New player has been added to applicable players.";
    }

    @Comment("Message displayed when player has been removed from applicable players of warp")
    default String getApplicablePlayerRemoved()
    {
        return "Player has been removed from applicable players.";
    }


}
