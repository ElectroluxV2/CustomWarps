package pl.insertt.customwarps.system.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class GuiItem
{
    private ItemStack item;
    private Consumer<InventoryClickEvent> consumer;

    public GuiItem(ItemStack item, Consumer<InventoryClickEvent> consumer)
    {
        this.item = item;

        if(consumer == null)
        {
            consumer = a -> {};
            return;
        }

        this.consumer = consumer;
    }

    public ItemStack getBukkitItem()
    {
        return this.item;
    }

    public void invClick(InventoryClickEvent e)
    {
        this.consumer.accept(e);
    }
}
