package pl.insertt.customwarps.system.command;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.insertt.customwarps.CustomWarpsPlugin;
import pl.insertt.customwarps.system.command.api.WarpPlayer;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class WarpPlayerImpl implements WarpPlayer
{
    private final CustomWarpsPlugin plugin;

    private final UUID uuid;

    public WarpPlayerImpl(CustomWarpsPlugin plugin, UUID uuid)
    {
        this.plugin = plugin;
        this.uuid = uuid;
    }

    public WarpPlayerImpl(CustomWarpsPlugin plugin, String name)
    {
        this.plugin = plugin;
        this.uuid = Bukkit.getPlayer(name).getUniqueId();
    }

    public WarpPlayerImpl(CustomWarpsPlugin plugin, Player player)
    {
        this.plugin = plugin;
        this.uuid = player.getUniqueId();
    }

    @Override
    public UUID getUniqueId()
    {
        return uuid;
    }

    @Override
    public void sendMessage(String message)
    {
        this.spigot().sendMessage(new TextComponent(message));
    }

    @Override
    public void sendMessage(String... messages)
    {
        for(String message : messages)
        {
            this.spigot().sendMessage(new TextComponent(message));
        }
    }

    @Override
    public void sendMessage(BaseComponent... component)
    {
        this.spigot().sendMessage(component);
    }

    @Override
    public void sendDependMessage(String message)
    {
        this.spigot().sendMessage(plugin.getConfiguration().getMessageFormat(), new TextComponent(message));
    }

    @Override
    public void sendDependMessage(String... messages)
    {
        for(String message : messages)
        {
            this.spigot().sendMessage(plugin.getConfiguration().getMessageFormat(), new TextComponent(message));
        }
    }

    @Override
    public void sendDependMessages(List<String> messages)
    {
        for(String message : messages)
        {
            this.spigot().sendMessage(plugin.getConfiguration().getMessageFormat(), new TextComponent(message));
        }
    }

    @Override
    public void sendDependMessages(BaseComponent... components)
    {
        this.spigot().sendMessage(plugin.getConfiguration().getMessageFormat(), new TextComponent(components));
    }

    @Override
    public void sendMessage(List<String> messages)
    {
        for(String message : messages)
        {
            this.spigot().sendMessage(new TextComponent(message));
        }
    }

    @Override
    public boolean hasPermission(String permission)
    {
        return this.bukkit().hasPermission(permission);
    }

    @Override
    public boolean isOp()
    {
        return this.bukkit().isOp();
    }

    @Override
    public Player bukkit()
    {
        return Bukkit.getPlayer(uuid);
    }

    @Override
    public Player.Spigot spigot()
    {
        return Bukkit.getPlayer(uuid).spigot();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        WarpPlayerImpl that = (WarpPlayerImpl) o;
        return Objects.equals(plugin, that.plugin) && Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(plugin, uuid);
    }

    @Override
    public boolean isPlayer()
    {
        return true;
    }

    @Override
    public boolean isConsole()
    {
        return false;
    }
}
