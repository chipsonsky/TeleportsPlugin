package chipsonsky.randomTeleport.manager;

import chipsonsky.randomTeleport.TeleportCommand;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class RtpManager {

    private static boolean cancelTeleport = false;

    public static void startTeleportation(Player player) {
        final FileConfiguration config = TeleportCommand.getInstance().getConfig();
        PotionEffect teleportEffect = new PotionEffect(PotionEffectType.BLINDNESS, 20 * config.getInt("effect_time"), 0);

        Random random = new Random();
        int randomX = getRandom(random);
        int randomZ = getRandom(random);

        Block randomBlock = (player.getWorld().getBlockAt(randomX, 64, randomZ));
        for (int i = 319; i > -64; i--) {
            player.getWorld().loadChunk(randomBlock.getChunk());
            if (player.getWorld().getBlockAt(randomX, i, randomZ).getType() != Material.AIR) {
                randomBlock = player.getWorld().getBlockAt(randomX, i + 1, randomZ);
                break;
            }
        }

        player.teleport(randomBlock.getLocation());
        player.addPotionEffect(teleportEffect);
        player.playSound(randomBlock.getLocation(), Sound.ENTITY_PLAYER_TELEPORT, 1, 1);
    }

    public static int getRandom(Random random) {
        final FileConfiguration config = TeleportCommand.getInstance().getConfig();
        int min1 = config.getInt("min_minus"), max1 = config.getInt("max_minus");
        int min2 = config.getInt("min"), max2 = config.getInt("max");

        int range1 = max1 - min1 + 1;
        int range2 = max2 - min2 + 1;
        int totalRange = range1 + range2;

        int randomIndex = random.nextInt(totalRange);

        return (randomIndex < range1)
                ? (min1 + randomIndex)
                : (min2 + randomIndex - range1);
    }
}

