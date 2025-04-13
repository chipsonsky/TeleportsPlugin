package chipsonsky.randomTeleport;

import chipsonsky.randomTeleport.command.DeluxeCommand;
import chipsonsky.randomTeleport.command.RtpCommand;
import chipsonsky.randomTeleport.command.SpawnCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class TeleportCommand extends JavaPlugin {

    private static TeleportCommand instance;

    public static TeleportCommand getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        getCommand("rtp").setExecutor(new RtpCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("deluxe").setExecutor(new DeluxeCommand());

        getLogger().info("\n\nПлагин запущен!\n");
    }

    @Override
    public void onDisable() {
        getLogger().info("\n\nПлагин выключен!\n");
    }
}
