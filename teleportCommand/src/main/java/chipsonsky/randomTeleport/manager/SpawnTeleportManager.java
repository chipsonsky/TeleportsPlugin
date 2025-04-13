package chipsonsky.randomTeleport.manager;

import chipsonsky.randomTeleport.TeleportCommand;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SpawnTeleportManager {
    public static void StartTeleport(Player player) {
        final FileConfiguration config = TeleportCommand.getInstance().getConfig();
        double spawnX = config.getDouble("spawnX");
        double spawnY = config.getDouble("spawnY");
        double spawnZ = config.getDouble("spawnZ");

        Location spawnLocation;
        spawnLocation = new Location(
                player.getWorld(),
                spawnX,
                spawnY,
                spawnZ);

        player.teleport(spawnLocation);
        player.playSound(spawnLocation, Sound.ENTITY_PLAYER_TELEPORT, 1, 1);
    }
}
