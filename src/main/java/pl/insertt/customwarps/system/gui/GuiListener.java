package pl.insertt.customwarps.system.gui;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class GuiListener implements Listener
{
    @EventHandler
    public void onClick(InventoryClickEvent e)
    {
        GuiWindow window = GuiWindow.getWindow(e.getInventory().getTitle());
        if(window != null)
        {
            GuiItem item = window.getItem(e.getSlot());
            if(item != null)
            {
                item.invClick(e);
            }
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent e)
    {
        GuiWindow window = GuiWindow.getWindow(e.getInventory().getTitle());
        if(window != null)
        {
            window.callOpen(e);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e)
    {
        GuiWindow window = GuiWindow.getWindow(e.getInventory().getTitle());
        if(window != null)
        {
            window.callClosed(e);
        }
    }

    @EventHandler
    public void onInteract(InventoryInteractEvent e)
    {
        if(GuiWindow.getWindow(e.getInventory().getTitle()) != null)
        {
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
        }
    }
}
