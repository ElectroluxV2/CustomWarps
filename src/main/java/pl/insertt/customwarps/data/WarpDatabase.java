package pl.insertt.customwarps.data;

import org.diorite.config.Config;
import org.diorite.config.annotations.Header;
import pl.insertt.customwarps.system.warp.api.CustomWarp;

import java.util.Set;

@Header("This is database of CustomWarps plugin. It's recommended to not touch this file on your own. Be careful!")
public interface WarpDatabase extends Config
{
    Set<CustomWarp> getWarpList();

    void setWarpList(Set<CustomWarp> warpList);
}
