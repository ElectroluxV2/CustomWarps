package pl.insertt.customwarps.data;

import net.md_5.bungee.api.ChatMessageType;
import org.diorite.config.Config;
import org.diorite.config.annotations.Comment;
import org.diorite.config.annotations.Footer;
import org.diorite.config.annotations.Header;

@Header("This is CustomWarps configuration file.")
@Footer("It's the end of configuration file ;)")

public interface WarpConfig extends Config
{
    @Comment({"Defines where messages will appear. Available modes: CHAT, ACTION_BAR", "Not every message will be appeared on action bar if selected.", "And remember, action bar doesn't have many features like hovers or multi-line messages and they will not work.", "Recommended setting: chat"})
    default ChatMessageType getMessageFormat()
    {
        return ChatMessageType.CHAT;
    }

    @Comment({"Defines whether warp autosave should be enabled", "Takes effect after restart."})
    default boolean getAutosave()
    {
        return true;
    }

    @Comment({"Defines interval between warp autosave, default: 600", "If autosave isn't enabled, this value have no matter", "Takes effect after restart."})
    default int getAutosaveInterval()
    {
        return 600;
    }

    @Comment({"Defines where server warps should appear.", "Available modes: chat, gui"})
    default String getWarpListType()
    {
        return "chat";
    }

    @Comment({"Defines how much warps player can have.", "Warps per permission will be added in future."})
    default int getMaxWarpsPerPlayer()
    {
        return 1;
    }

    @Comment({"Defines how much applicable players can be add to warp.", "At the moment, max applicable players > 45 is not supported, sorry."})
    default int getMaxApplicablePlayers()
    {
        return 5;
    }

    void setMessageFormat(ChatMessageType type);

    void setAutosave(boolean autoSave);

    void setAutosaveInterval(int interval);

    void setWarpListType(String warpListType);

    void setMaxWarpsPerPlayer(int maxWarpsPerPlayer);
}
