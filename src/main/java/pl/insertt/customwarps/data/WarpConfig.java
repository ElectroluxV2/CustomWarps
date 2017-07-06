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
    @Comment("Defines where messages will appear. Available modes: CHAT, ACTION_BAR \nNot every message will be appeared on action bar if selected.")
    default ChatMessageType getMessageFormat()
    {
        return ChatMessageType.CHAT;
    }

    @Comment("Defines whether warp autosave should be enabled. \nTakes effect after restart.")
    default boolean getAutosave()
    {
        return true;
    }

    @Comment("Defines interval between warp autosave, default: 600 \nIf autosave isn't enabled, this value have no matter. \nTakes effect after restart.")
    default int getAutosaveInterval()
    {
        return 600;
    }

    void setMessageFormat(ChatMessageType type);

    void setAutosave(boolean autoSave);

    void setAutosaveInterval(int interval);

}
