package pl.insertt.customwarps.system.command.api;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public interface WarpPlayer extends WarpCommandSender
{
    UUID getUniqueId();

    void sendMessage(String message);

    void sendMessage(String... messages);

    void sendMessage(List<String> messages);

    void sendMessage(BaseComponent... components);

    void sendDependMessage(String message);

    void sendDependMessage(String... messages);

    void sendDependMessages(List<String> messages);

    void sendDependMessages(BaseComponent... components);

    Player bukkit();

    Player.Spigot spigot();
}
