package pl.insertt.customwarps.system.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class GuiWindow
{
    private static Map<String, GuiWindow> windows = new HashMap<>();

    private Inventory inv;
    private Map<Integer, GuiItem> items;
    private Consumer<InventoryOpenEvent> onOpen = null;
    private Consumer<InventoryCloseEvent> onClose = null;

    public GuiWindow(String name, int rows)
    {
        name = getValidName(name);

        this.inv = Bukkit.createInventory(null, rows > 6 ? 6 * 9 : rows * 9, name);
        this.items = new HashMap<>(rows > 6 ? 6 * 9 : rows * 9);

        windows.put(name, this);
    }

    public void setItem(int slot, GuiItem item)
    {
        this.items.put(slot, item);
        this.inv.setItem(slot, item.getBukkitItem());
    }

    public void setItem(int x, int y, GuiItem item)
    {
        setItem(x + y*9, item);
    }

    public void setToNextFree(GuiItem item)
    {
        for(int i = 0; i < inv.getSize(); i++)
        {
            if(inv.getItem(i) == null)
            {
                this.items.put(i, item);
                this.inv.setItem(i, item.getBukkitItem());
                break;
            }
        }
    }

    public void setToNextFree(GuiItem item, int start)
    {
        for(int i = start; i < inv.getSize(); i++)
        {
            if(inv.getItem(i) == null)
            {
                this.items.put(i, item);
                this.inv.setItem(i, item.getBukkitItem());
                break;
            }
        }
    }

    public GuiItem getItem(int slot)
    {
        return this.items.get(slot);
    }

    public GuiItem getItem(int x, int y)
    {
        return getItem(x*9 + y);
    }

    public void setOpenEvent(Consumer<InventoryOpenEvent> e)
    {
        this.onOpen = e;
    }

    @Deprecated
    void callOpen(InventoryOpenEvent e)
    {
        if(onOpen != null) onOpen.accept(e);
    }

    public void setCloseEvent(Consumer<InventoryCloseEvent> e)
    {
        this.onClose = e;
    }

    @Deprecated
    void callClosed(InventoryCloseEvent e)
    {
        if(onClose != null) onClose.accept(e);
    }

    public Inventory getBukkitInventory()
    {
        return this.inv;
    }

    public void show(HumanEntity h)
    {
        Inventory inv = Bukkit.createInventory(h, getBukkitInventory().getSize(), getBukkitInventory().getTitle());
        inv.setContents(getBukkitInventory().getContents());
        h.openInventory(inv);
    }

    public void unregister()
    {
        windows.remove(this.getBukkitInventory().getTitle());
        this.items.clear();
    }

    static GuiWindow getWindow(String inv)
    {
        return windows.get(inv);
    }

    private String getValidName(String name)
    {
        if(windows.containsKey(name)) return getValidName(name + ChatColor.RESET);
        else return name;
    }

    public void fill()
    {
        for(int i = 0; i < inv.getSize(); i++)
        {
            if(inv.getItem(i) == null)
            {
                inv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));
            }
        }
    }

    public void redefine(GuiItem item)
    {
        for(Map.Entry<Integer, GuiItem> entry : items.entrySet())
        {
            if(entry.getValue().equals(item))
            {
                items.remove(entry.getKey());
                items.put(entry.getKey(), entry.getValue());
                break;
            }
        }
    }
}
