package pl.insertt.customwarps.warp.api;

import org.apache.commons.lang3.ArrayUtils;

public enum CustomWarpFlag
{

    MESSAGETYPE(new String[]{"messageType", "msgType"}),

    AUTOSAVE(new String[]{"enableAutoSave"}),

    AUTOSAVE_INTERVAL(new String[]{"autosaveInterval, autosave"}),

    SAVE(new String[]{"saveconfig, savedb"}),

    LOAD(new String[]{"loadconfig, loaddb"}),

    SHOW(new String[]{"showAll", "showOptions"}),

    GET(new String[]{"getValue", "getEntry"});

    private String[] aliases;

    CustomWarpFlag(String[] aliases)
    {
        this.aliases = aliases;
    }

    public static CustomWarpFlag getByName(String name)
    {
        for(CustomWarpFlag flag : CustomWarpFlag.values())
        {
            if(flag.name().equalsIgnoreCase(name) || ArrayUtils.contains(flag.aliases, name))
            {
                return flag;
            }
        }
        return null;
    }

}
