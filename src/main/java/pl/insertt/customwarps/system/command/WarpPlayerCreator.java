package pl.insertt.customwarps.system.command;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.insertt.customwarps.CustomWarpsPlugin;

public class WarpPlayerCreator extends WarpPlayerManager implements Listener
{
    public WarpPlayerCreator(CustomWarpsPlugin plugin)
    {
        super(plugin);
    }

    @EventHandler
    private void onJoin(final PlayerJoinEvent event)
    {
        super.registerPlayer(event.getPlayer().getUniqueId());
    }

    @EventHandler
    private void onQuit(final PlayerQuitEvent event)
    {
        super.unregisterPlayer(event.getPlayer().getUniqueId());
    }
}
