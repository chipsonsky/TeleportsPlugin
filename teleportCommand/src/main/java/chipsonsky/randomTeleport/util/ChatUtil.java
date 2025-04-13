package chipsonsky.randomTeleport.util;

import chipsonsky.randomTeleport.TeleportCommand;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ChatUtil {
    static final FileConfiguration config = TeleportCommand.getInstance().getConfig();

    public static String prefix = config.getString("chat.prefix");
    public static void sendMessage(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
    }

    public static void sendTitle(Player player, String message, String subMsg, int fadeIn, int stay, int fadeOut) {

        player.sendTitle(
                ChatColor.translateAlternateColorCodes('&', message),
                ChatColor.translateAlternateColorCodes('&', subMsg),
                fadeIn,
                stay,
                fadeOut
        );
    }

    public static void sendTitle(Player player, String message, String subMsg) {
        player.sendTitle(
                ChatColor.translateAlternateColorCodes('&', message),
                ChatColor.translateAlternateColorCodes('&', subMsg),
                10,
                20,
                10
        );
    }
}
