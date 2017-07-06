package pl.insertt.customwarps.command.framework;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.insertt.customwarps.CustomWarpsPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class CommandRegistry
{
    private final CustomWarpsPlugin plugin;

    private CommandMap commandMap;

    public CommandRegistry(CustomWarpsPlugin plugin)
    {
        this.plugin = plugin;

        if (this.commandMap == null)
        {
            try
            {
                Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                field.setAccessible(true);
                this.commandMap = ((CommandMap)field.get(Bukkit.getServer()));
                field.setAccessible(false);
            }
            catch (IllegalAccessException | NoSuchFieldException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void register(Command... commands)
    {
        for (Command command : commands)
        {
            Method[] methods = command.getClass().getDeclaredMethods();

            for (Method method : methods)
            {
                if (method.isAnnotationPresent(CommandInfo.class))
                {
                    final CommandInfo info = method.getAnnotation(CommandInfo.class);

                    org.bukkit.command.Command bukkitCommand = new org.bukkit.command.Command(info.name(), info.description(), info.usage(), Arrays.asList(info.aliases()))
                    {
                        public boolean execute(CommandSender sender, String s, String[] strings)
                        {
                            Arguments args = new Arguments(strings);

                            if (!sender.hasPermission(info.permission()))
                            {
                                if(sender instanceof Player)
                                {
                                    ((Player)sender).spigot().sendMessage(plugin.getConfiguration().getMessageFormat(), new TextComponent(plugin.getMessages().getErrorColor() + plugin.getMessages().getNoPermission()));
                                }
                                else
                                {
                                    sender.sendMessage(plugin.getMessages().getErrorColor() + plugin.getMessages().getNoPermission() + ChatColor.GRAY + "(" + info.permission() + ")");
                                }
                            }

                            if (info.playerOnly())
                            {
                                if (!(sender instanceof Player))
                                {
                                    sender.sendMessage(plugin.getMessages().getErrorColor() + plugin.getMessages().getPlayerOnly());
                                    return true;
                                }
                            }

                            if ((args.getSize() < info.minArgs()) || (args.getSize() > info.maxArgs()))
                            {
                                if(sender instanceof Player)
                                {
                                    ((Player)sender).spigot().sendMessage(plugin.getConfiguration().getMessageFormat(), new TextComponent(plugin.getMessages().getErrorColor() + plugin.getMessages().getUsage() + info.usage()));
                                }
                                else
                                {
                                    sender.sendMessage(plugin.getMessages().getErrorColor() + plugin.getMessages().getUsage() + info.usage());
                                }
                                return true;
                            }

                            try
                            {
                                command.execute(sender, args);
                            }
                            catch(ArgumentParseException | SomethingWentWrong | Exception ex)
                            {
                                if(ex instanceof Exception)
                                {
                                    if(sender instanceof Player)
                                    {
                                        ((Player)sender).spigot().sendMessage(plugin.getConfiguration().getMessageFormat(), new TextComponent(plugin.getMessages().getErrorColor() + plugin.getMessages().getUnknownError()));
                                        ex.printStackTrace();
                                    }
                                    else
                                    {
                                        sender.sendMessage(plugin.getMessages().getErrorColor() + plugin.getMessages().getUnknownError());
                                        ex.printStackTrace();
                                    }
                                }

                                if(sender instanceof Player)
                                {
                                    ((Player)sender).spigot().sendMessage(plugin.getConfiguration().getMessageFormat(), new TextComponent(plugin.getMessages().getErrorColor() + plugin.getMessages().getSomethingWrong() + ex.getMessage()));
                                }
                                else
                                {
                                    sender.sendMessage(plugin.getMessages().getErrorColor() + plugin.getMessages().getSomethingWrong() + ex.getMessage());
                                }
                            }
                            return true;
                        }
                    };
                    this.commandMap.register("", bukkitCommand);
                }
            }
        }
    }
}

