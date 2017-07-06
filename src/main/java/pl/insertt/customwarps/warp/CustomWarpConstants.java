package pl.insertt.customwarps.warp;

import net.md_5.bungee.api.ChatMessageType;
import pl.insertt.customwarps.CustomWarpsPlugin;

import java.util.UUID;

public final class CustomWarpConstants
{
    private CustomWarpsPlugin plugin;

    public final ChatMessageType MESSAGE_TYPE;

    public final boolean AUTOSAVE;

    public final int AUTOSAVE_INTERVAL;

    public final UUID EVERYONE;

    public CustomWarpConstants(CustomWarpsPlugin plugin)
    {
        this.plugin = plugin;

        MESSAGE_TYPE = plugin.getWarpConfig().getMessageFormat();

        AUTOSAVE = plugin.getWarpConfig().getAutosave();

        AUTOSAVE_INTERVAL = plugin.getWarpConfig().getAutosaveInterval();

        EVERYONE = UUID.fromString("FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF");
    }

}
