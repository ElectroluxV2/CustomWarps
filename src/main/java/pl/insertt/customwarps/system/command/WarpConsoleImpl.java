package pl.insertt.customwarps.system.command;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import pl.insertt.customwarps.system.command.api.WarpConsole;

import java.util.List;

public class WarpConsoleImpl implements WarpConsole
{
    @Override
    public boolean isPlayer()
    {
        return false;
    }

    @Override
    public boolean isConsole()
    {
        return true;
    }

    @Override
    public ConsoleCommandSender console()
    {
        return Bukkit.getConsoleSender();
    }

    @Override
    public void sendMessage(String message)
    {
        this.console().sendMessage(message);
    }

    @Override
    public void sendMessage(String... messages)
    {
        for(String message : messages)
        {
            this.console().sendMessage(message);
        }
    }

    @Override
    public void sendMessage(List<String> messages)
    {
        for(String message : messages)
        {
            this.console().sendMessage(message);
        }
    }

    @Override
    public void sendMessage(BaseComponent... component)
    {
        for(BaseComponent comp : component)
        {
            this.sendMessage(comp.toLegacyText());
        }
    }

    @Override
    public boolean hasPermission(String permission)
    {
        return true;
    }

    @Override
    public boolean isOp()
    {
        return true;
    }
}
