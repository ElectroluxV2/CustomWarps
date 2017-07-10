package pl.insertt.customwarps.util;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerUtils
{
    public static void broadcast(TextComponent component)
    {
        for(Player player : Bukkit.getOnlinePlayers())
        {
            player.spigot().sendMessage(component);
        }
    }


    public static void broadcast(ChatMessageType type, TextComponent component)
    {
        for(Player player : Bukkit.getOnlinePlayers())
        {
            player.spigot().sendMessage(type, component);
        }
    }

    public static String getName(UUID uuid)
    {
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        return player.getName();
    }

    private PlayerUtils() { }
}
