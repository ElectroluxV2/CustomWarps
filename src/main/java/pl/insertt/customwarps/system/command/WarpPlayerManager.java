package pl.insertt.customwarps.system.command;

import org.bukkit.Bukkit;
import pl.insertt.customwarps.CustomWarpsPlugin;
import pl.insertt.customwarps.system.command.api.WarpPlayer;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class WarpPlayerManager
{
    private static final Set<WarpPlayer> players = new HashSet<>();
    private final CustomWarpsPlugin plugin;

    protected WarpPlayerManager(CustomWarpsPlugin plugin)
    {
        this.plugin = plugin;
    }

    protected boolean registerPlayer(UUID uuid)
    {
        WarpPlayer player = new WarpPlayerImpl(plugin, uuid);
        return players.add(player);
    }

    protected boolean unregisterPlayer(UUID uuid)
    {
        for(WarpPlayer player : players)
        {
            if(player.getUniqueId().equals(uuid))
            {
                return players.remove(player);
            }
        }
        return false;
    }

    public static WarpPlayer getByUUID(UUID uuid)
    {
        for(WarpPlayer player : players)
        {
            if(player.getUniqueId().equals(uuid))
            {
                return player;
            }
        }
        return null;
    }

    public static WarpPlayer getByName(String name)
    {
        for(WarpPlayer player : players)
        {
            if(Bukkit.getPlayer(player.getUniqueId()).getName().equalsIgnoreCase(name))
            {
                return player;
            }
        }
        return null;
    }

    public static Set<WarpPlayer> getPlayers()
    {
        return new HashSet<>(players);
    }
}