package pl.insertt.customwarps.warp.api;

public enum CustomWarpFlag
{

    MESSAGETYPE(new String[]{"messageType", "msgType"}),
    AUTOSAVE_INTERVAL(new String[]{"autosaveInterval, autosave"});

    private String[] aliases;

    CustomWarpFlag(String[] aliases)
    {
        this.aliases = aliases;
    }

}
