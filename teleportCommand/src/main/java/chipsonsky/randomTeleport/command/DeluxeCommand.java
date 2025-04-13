package chipsonsky.randomTeleport.command;

import chipsonsky.randomTeleport.TeleportCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DeluxeCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender.hasPermission("Deluxe.Admin") || commandSender.isOp())) {
            commandSender.sendMessage("Недостаточно прав!");
            return true;
        }
        final FileConfiguration config = TeleportCommand.getInstance().getConfig();
        if (strings[0].equalsIgnoreCase("reload")) {
            TeleportCommand.getInstance().reloadConfig();
            commandSender.sendMessage("Конфиг перезагружен");
            return true;
        } else if (strings[0].equalsIgnoreCase("info")) {
            commandSender.sendMessage("Тесты на версии 1.21.1\n" +
                    "Команды плагина:\n" +
                    "/spawn - телепортирует на спавн по указанным в конфиге координатам\n" +
                    "/rtp - телепортирует на рандомные координаты, укажите в конфиге границы\n" +
                    "/deluxe <arg> - админские команды с настройками в игре\n" +
                    "Телеграм разработчика: @DeluxePlugin");
            return true;
        }
        else {
            commandSender.sendMessage("Неизвестный аргумент!");
            return true;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> tab = new ArrayList<>();
        tab.add("reload");
        tab.add("info");
        return tab;
    }
}
