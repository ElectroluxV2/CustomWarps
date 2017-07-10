package pl.insertt.customwarps.system.gui;

import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiItemBuilder
{
    private final ItemStack itemStack;

    private ItemMeta itemMeta;

    public GuiItemBuilder(Material material)
    {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }

    public GuiItemBuilder(Material material, int stack)
    {
        this.itemStack = new ItemStack(material, stack);
        this.itemMeta = itemStack.getItemMeta();
    }

    public GuiItemBuilder(Material material, int stack, int data)
    {
        this.itemStack = new ItemStack(material, stack, (short) data);
        this.itemMeta = itemStack.getItemMeta();
    }

    public GuiItemBuilder(ItemStack itemStack)
    {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    private void refreshMeta()
    {
        this.itemStack.setItemMeta(itemMeta);
    }

    public GuiItemBuilder setName(String name)
    {
        itemMeta.setDisplayName(StringUtils.replace(name, "&", "§"));
        refreshMeta();
        return this;
    }

    public GuiItemBuilder setLore(String... lore)
    {
        List<String> formatted = new ArrayList<>();

        for (String str : lore)
        {
            formatted.add(ChatColor.GRAY + StringUtils.replace(str, "&", "§"));
        }

        itemMeta.setLore(formatted);
        refreshMeta();
        return this;
    }

    public GuiItemBuilder setEnchant(Enchantment enchant, int level)
    {
        itemMeta.addEnchant(enchant, level, true);
        refreshMeta();
        return this;
    }

    public ItemStack getItem()
    {
        return itemStack;
    }

}
