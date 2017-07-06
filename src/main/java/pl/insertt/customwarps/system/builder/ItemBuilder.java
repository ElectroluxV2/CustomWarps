package pl.insertt.customwarps.system.builder;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder
{
    private final ItemStack itemStack;

    private ItemMeta itemMeta;

    public ItemBuilder(Material material)
    {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int stack)
    {
        this.itemStack = new ItemStack(material, stack);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int stack, int data)
    {
        this.itemStack = new ItemStack(material, stack, (short) data);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(ItemStack itemStack)
    {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    private void refreshMeta()
    {
        this.itemStack.setItemMeta(itemMeta);
    }

    public ItemBuilder setName(String name)
    {
        itemMeta.setDisplayName(StringUtils.replace(name, "&", "§"));

        refreshMeta();

        return this;
    }

    public ItemBuilder setLore(String... lore)
    {
        for (String str : lore)
        {
            StringUtils.replace(str, "&", "§");
        }

        itemMeta.setLore(Arrays.asList(lore));

        refreshMeta();

        return this;
    }

    public ItemBuilder setEnchant(Enchantment enchant, int level)
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
