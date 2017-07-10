package pl.insertt.customwarps.system.command.api;

import org.bukkit.command.ConsoleCommandSender;

import java.util.List;

public interface WarpConsole extends WarpCommandSender
{
    ConsoleCommandSender console();

    void sendMessage(String message);

    void sendMessage(String... messages);

    void sendMessage(List<String> messages);
}
