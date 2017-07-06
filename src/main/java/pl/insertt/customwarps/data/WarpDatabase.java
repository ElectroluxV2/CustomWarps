package pl.insertt.customwarps.data;

import org.diorite.config.Config;
import org.diorite.config.annotations.Footer;
import org.diorite.config.annotations.Header;
import pl.insertt.customwarps.system.warp.api.CustomWarp;

import java.util.Set;

@Header("This is database of CustomWarps plugin.")
@Footer("This is end of database of CustomWarps plugin.")
public interface WarpDatabase extends Config
{
    Set<CustomWarp> getWarpList();

    void setWarpList(Set<CustomWarp> warpList);
}
