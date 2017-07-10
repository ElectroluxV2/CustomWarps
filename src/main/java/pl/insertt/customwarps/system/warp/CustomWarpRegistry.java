package pl.insertt.customwarps.system.warp;

import pl.insertt.customwarps.CustomWarpsPlugin;
import pl.insertt.customwarps.system.warp.api.CustomWarp;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CustomWarpRegistry
{
    private final CustomWarpsPlugin plugin;
    private final Set<CustomWarp> warps = new HashSet<>();

    public CustomWarpRegistry(CustomWarpsPlugin plugin)
    {
        this.plugin = plugin;
    }

    public void register(CustomWarp warp)
    {
        warps.add(warp);
    }

    public void unregister(CustomWarp warp)
    {
        warps.remove(warp);
    }

    public boolean exists(String name)
    {
        return warps.stream().anyMatch(w -> w.getName().equalsIgnoreCase(name));
    }

    @Nullable
    public CustomWarp getWarp(String name)
    {
        for(CustomWarp warp : warps)
        {
            if(warp.getName().equalsIgnoreCase(name))
            {
                return warp;
            }
        }
        return null;
    }

    public Set<CustomWarp> getPlayerWarps(UUID player)
    {
        return warps.stream().filter(w -> w.getOwner().equals(player)).collect(Collectors.toSet());
    }

    public Set<CustomWarp> getApplicableWarps(UUID player)
    {
        return warps.stream().filter(w -> w.getApplicablePlayers().contains(player)).collect(Collectors.toSet());
    }

    public UUID getOwner(String name)
    {
        for(CustomWarp warp : warps)
        {
            if(warp.getName().equalsIgnoreCase(name))
            {
                return warp.getOwner();
            }
        }
        return null;
    }

    public void loadWarps(Set<CustomWarp> warpList)
    {
        warps.addAll(warpList);
    }

    public Set<CustomWarp> getAllWarps()
    {
        return new HashSet<>(warps);
    }

    public int getWarpCount()
    {
        return warps.size();
    }
}
