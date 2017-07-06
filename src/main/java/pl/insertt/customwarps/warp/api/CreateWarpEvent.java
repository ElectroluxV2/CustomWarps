package pl.insertt.customwarps.warp.api;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class CreateWarpEvent extends Event implements Cancellable
{
    private static HandlerList handlers = new HandlerList();

    private boolean isCancelled;

    private UUID attemptPlayer;

    private CustomWarp warp;

    private String reason;

    public CreateWarpEvent(UUID attemptPlayer, CustomWarp warp)
    {
        this.warp = warp;
        this.attemptPlayer = attemptPlayer;
    }

    public UUID getWho()
    {
        return attemptPlayer;
    }

    public CustomWarp getWarp()
    {
        return warp;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public String getReason()
    {
        return reason;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    @Override
    public boolean isCancelled()
    {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled)
    {
        this.isCancelled = isCancelled;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }
}
