package pl.insertt.customwarps.system.command.api;

import net.md_5.bungee.api.chat.BaseComponent;

import java.util.List;

public interface WarpCommandSender
{
    boolean isPlayer();

    boolean isConsole();

    void sendMessage(String message);

    void sendMessage(String... messages);

    void sendMessage(List<String> messages);

    void sendMessage(BaseComponent... component);

    boolean hasPermission(String permission);

    boolean isOp();
}
