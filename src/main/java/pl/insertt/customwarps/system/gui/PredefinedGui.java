package pl.insertt.customwarps.system.gui;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.insertt.customwarps.CustomWarpsPlugin;
import pl.insertt.customwarps.system.command.WarpPlayerManager;
import pl.insertt.customwarps.system.command.api.WarpPlayer;
import pl.insertt.customwarps.system.warp.api.CustomWarp;

import java.util.UUID;

public class PredefinedGui
{
    private final CustomWarpsPlugin plugin;

    public final GuiWindow MANAGE_WARP_OPTIONS_GUI;
    private final GuiWindow MANAGE_SELECTED_APPLICABLE_PLAYER_GUI;

    private final GuiItem CLOSE_INVENTORY_BUTTON;
    private final GuiItem REMOVE_SELECTED_PLAYER_BUTTON;
    private final GuiItem ADD_SELECTED_PLAYER_BUTTON;
    private final GuiItem ADD_PLAYER_BUTTON;
    private final GuiItem CHANGE_NAME;
    private final GuiItem CHANGE_LOCATION;
    private final GuiItem CHANGE_ICON;
    private final GuiItem CHANGE_PLAYERS;

    public PredefinedGui(CustomWarpsPlugin plugin)
    {
        this.plugin = plugin;

        MANAGE_SELECTED_APPLICABLE_PLAYER_GUI = new GuiWindow("Manage selected player", 3);

        ADD_SELECTED_PLAYER_BUTTON = new GuiItem(new GuiItemBuilder(Material.STAINED_CLAY, 1, 5).setName(ChatColor.GOLD + "Add player").setLore("Click on me with named name tag", "to add applicable player on this place.", "Added player must be online!").getItem(), event ->
        {
            if(event.getCursor() != null && event.getCursor().hasItemMeta() && event.getCursor().getItemMeta().hasDisplayName())
            {
                CustomWarp name = plugin.getRegistry().getWarp(event.getClickedInventory().getItem(4).getItemMeta().getDisplayName());
                if(name == null)
                {
                    return;
                }
                WarpPlayer clicked = WarpPlayerManager.getByUUID(event.getWhoClicked().getUniqueId());
                Player player = Bukkit.getPlayer(event.getCursor().getItemMeta().getDisplayName());
                if(player == null)
                {
                    return;
                }
                if(name.getApplicablePlayers().contains(player.getUniqueId()))
                {
                    clicked.sendDependMessage(plugin.getMessages().getErrorColor() + "This player is already added.");
                    return;
                }
                name.addApplicablePlayer(player.getUniqueId());
                clicked.sendDependMessage(plugin.getMessages().getSuccessColor() + plugin.getMessages().getApplicablePlayerAdded());
                clicked.bukkit().closeInventory();
            }
        });

        REMOVE_SELECTED_PLAYER_BUTTON = new GuiItem(new GuiItemBuilder(Material.HARD_CLAY, 1, 14).setName(ChatColor.GOLD + "Remove player").setLore("Click on me to remove selected player").getItem(), event ->
        {
            CustomWarp name = plugin.getRegistry().getWarp(event.getClickedInventory().getItem(4).getItemMeta().getDisplayName());
            OfflinePlayer player = Bukkit.getOfflinePlayer(ChatColor.stripColor(event.getClickedInventory().getItem(4).getItemMeta().getLore().get(1)));
            WarpPlayer clicked = WarpPlayerManager.getByUUID(event.getWhoClicked().getUniqueId());
            name.removeApplicablePlayer(player.getUniqueId());
            clicked.sendDependMessage(plugin.getMessages().getSuccessColor() + plugin.getMessages().getApplicablePlayerRemoved());
            clicked.bukkit().closeInventory();
        });

        CLOSE_INVENTORY_BUTTON = new GuiItem(new GuiItemBuilder(Material.BARRIER, 1).setName(ChatColor.RED + "Close inventory").getItem(), event ->
        {
            WarpPlayer player = WarpPlayerManager.getByUUID(event.getWhoClicked().getUniqueId());
            event.getWhoClicked().closeInventory();
        });

        ADD_PLAYER_BUTTON = new GuiItem(new GuiItemBuilder(Material.SKULL_ITEM, 1).setName(ChatColor.GOLD + "Add new player").setLore("Click on me to add new applicable player!").getItem(), event ->
        {
            event.getWhoClicked().closeInventory();
            GuiWindow window = MANAGE_SELECTED_APPLICABLE_PLAYER_GUI;
            window.setItem(4, new GuiItem(new GuiItemBuilder(Material.NETHER_STAR, 1).setName(event.getClickedInventory().getItem(4).getItemMeta().getDisplayName()).setLore("I'm getting edited right now! :D").getItem(), action ->
            {
                   //nothing
            }));
            window.setItem(10, ADD_SELECTED_PLAYER_BUTTON);
            window.setItem(16, CLOSE_INVENTORY_BUTTON);
            window.fill();
            event.getWhoClicked().openInventory(window.getBukkitInventory());
        });

        /**
         * Manage warp gui.
         */
        MANAGE_WARP_OPTIONS_GUI = new GuiWindow("Management menu", 6);
        CHANGE_NAME = new GuiItem(new GuiItemBuilder(Material.SIGN, 1).setName(ChatColor.GOLD + "Change name").setLore("Click on me with name tag to change name.", "Name will be changed to name of nametag.").getItem(), event ->
        {
            if(event.getCursor() != null && event.getCursor().getType() != Material.AIR && event.getCursor().hasItemMeta() && event.getCursor().getItemMeta().hasDisplayName())
            {
                WarpPlayer player = WarpPlayerManager.getByUUID(event.getWhoClicked().getUniqueId());
                ItemStack nametag = event.getCursor();
                ItemStack warpItem = event.getClickedInventory().getItem(4);
                CustomWarp warp = plugin.getRegistry().getWarp(warpItem.getItemMeta().getDisplayName());
                if(plugin.getRegistry().exists(nametag.getItemMeta().getDisplayName()))
                {
                    player.sendDependMessage(plugin.getMessages().getErrorColor() + "This name is already in use.");
                    event.getWhoClicked().closeInventory();
                    return;
                }
                warp.setName(nametag.getItemMeta().getDisplayName());
                event.getWhoClicked().closeInventory();
                player.sendDependMessage(plugin.getMessages().getSuccessColor() + "New name set.");
            }
        });

        CHANGE_LOCATION = new GuiItem(new GuiItemBuilder(Material.COMPASS, 1).setName(ChatColor.GOLD + "Change location").setLore("Click on me to change location of warp.", "Location will be changed to your location.").getItem(), event ->
        {
            final Location location = event.getWhoClicked().getLocation();
            ItemStack warpItem = event.getClickedInventory().getItem(4);
            CustomWarp warp = plugin.getRegistry().getWarp(warpItem.getItemMeta().getDisplayName());
            warp.setLocation(location);
            WarpPlayer player = WarpPlayerManager.getByUUID(event.getWhoClicked().getUniqueId());
            event.getWhoClicked().closeInventory();
            player.sendDependMessage(plugin.getMessages().getSuccessColor() + "New location set.");
        });

        CHANGE_ICON = new GuiItem(new GuiItemBuilder(Material.PAINTING, 1).setName(ChatColor.GOLD + "Change icon").setLore("Click on me to with chosen item to change icon.", "Icon will be changed to item held on cursor.").getItem(), event ->
        {
            if(event.getCursor() != null && event.getCursor().getType() != Material.AIR)
            {
                WarpPlayer player = WarpPlayerManager.getByUUID(event.getWhoClicked().getUniqueId());
                Material icon = event.getCursor().getType();
                ItemStack warpItem = event.getClickedInventory().getItem(4);
                CustomWarp warp = plugin.getRegistry().getWarp(warpItem.getItemMeta().getDisplayName());
                warp.setIcon(icon);
                event.getWhoClicked().closeInventory();
                player.sendDependMessage(plugin.getMessages().getSuccessColor() + "New icon set.");
            }
        });

        CHANGE_PLAYERS = new GuiItem(new GuiItemBuilder(Material.SKULL_ITEM, 1, 3).setName(ChatColor.GOLD + "Change applicable players").setLore("Click on me to change applicable players.").getItem(), event ->
        {
            event.getWhoClicked().closeInventory();

            GuiWindow window = new GuiWindow("Manage applicable players", 6);
            window.setItem(4, new GuiItem(new GuiItemBuilder(Material.NETHER_STAR, 1).setName(event.getClickedInventory().getItem(4).getItemMeta().getDisplayName()).setLore("I'm getting edited right now! :D").getItem(), e ->
            {
                //nothing
            }));
            CustomWarp warp = plugin.getRegistry().getWarp(window.getBukkitInventory().getItem(4).getItemMeta().getDisplayName());

            for(UUID uuid : warp.getApplicablePlayers())
            {
                OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                GuiItem head = new GuiItem(new GuiItemBuilder(Material.SKULL_ITEM, 1, 3).setName(player.getName()).setLore("Click on me to get more options!").getItem(), run ->
                {
                    GuiWindow inventory = MANAGE_SELECTED_APPLICABLE_PLAYER_GUI;
                    inventory.setItem(4, new GuiItem(new GuiItemBuilder(Material.NETHER_STAR, 1).setName(warp.getName()).setLore("I'm getting edited right now! :D", ChatColor.BLACK + player.getName()).getItem(), action ->
                    {
                        //nothing
                    }));
                    inventory.setItem(10, REMOVE_SELECTED_PLAYER_BUTTON);
                    inventory.setItem(16, CLOSE_INVENTORY_BUTTON);
                    inventory.fill();
                    event.getWhoClicked().openInventory(inventory.getBukkitInventory());
                });
                window.setToNextFree(head, 9);
            }
            for(int i = 0; i <= 8; i++)
            {
                if(window.getBukkitInventory().getItem(i) == null)
                {
                    window.getBukkitInventory().setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));
                }
            }
            for(ItemStack is : window.getBukkitInventory().getContents())
            {
                if(is != null && is.hasItemMeta() && !warp.isApplicable(Bukkit.getOfflinePlayer(is.getItemMeta().getDisplayName()).getUniqueId()))
                {
                    is.setType(Material.AIR);
                }
                if(is == null || is.getType() == Material.AIR)
                {
                    window.setToNextFree(ADD_PLAYER_BUTTON);
                }
            }
            event.getWhoClicked().openInventory(window.getBukkitInventory());
        });

        MANAGE_WARP_OPTIONS_GUI.setItem(10, CHANGE_NAME);
        MANAGE_WARP_OPTIONS_GUI.setItem(16, CHANGE_LOCATION);
        MANAGE_WARP_OPTIONS_GUI.setItem(37, CHANGE_ICON);
        MANAGE_WARP_OPTIONS_GUI.setItem(43, CHANGE_PLAYERS);
        MANAGE_WARP_OPTIONS_GUI.setItem(49, CLOSE_INVENTORY_BUTTON);
        MANAGE_WARP_OPTIONS_GUI.fill();
        /**
         * End of manage warp gui
         */
    }


}
