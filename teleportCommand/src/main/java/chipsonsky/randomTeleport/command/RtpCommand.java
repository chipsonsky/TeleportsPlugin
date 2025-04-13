package chipsonsky.randomTeleport.command;

import chipsonsky.randomTeleport.TeleportCommand;
import chipsonsky.randomTeleport.manager.RtpManager;
import chipsonsky.randomTeleport.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class RtpCommand implements CommandExecutor {

    HashMap<Player, Long> cooldowns = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        final FileConfiguration config = TeleportCommand.getInstance().getConfig();

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Данную команду может отправлять только человек!");
            return true;
        }
        Player player = (Player) commandSender;
        if (!(commandSender.isOp() || commandSender.hasPermission("Deluxe.Rtp"))) {
            ChatUtil.sendMessage(player, "У вас нет прав на использование данной команды!");
            player.playSound(player, Sound.BLOCK_SMITHING_TABLE_USE, 2, 2);
            return true;
        };

        if (player.getWorld().getName().equals("world_nether") || player.getWorld().getName().equals("world_the_end")) {
            ChatUtil.sendMessage(player, "Телепортация невозможна в этом мире.");
            player.playSound(player, Sound.BLOCK_SMITHING_TABLE_USE, 2, 2);
            return true;
        }

        if (cooldowns.containsKey(player)) {

            long timeElapsed = System.currentTimeMillis() - cooldowns.get(player);

            long remainingTime = (config.getLong("cooldown") * 1000) - timeElapsed;

            if (remainingTime > 0) {
                long remainingSeconds = remainingTime / 1000;
                ChatUtil.sendMessage(player, "Вы можете использовать эту команду через: " + (remainingSeconds + 1) + " секунд.");
                player.playSound(player, Sound.BLOCK_SMITHING_TABLE_USE, 2, 2);
                return true;
            }
        }

        new BukkitRunnable() {

            int timer = config.getInt("teleport_timer");
            BossBar bossBar = Bukkit.createBossBar(
                    ChatColor.translateAlternateColorCodes('&',  config.getString("boss_bar_title").replace("%time%", String.valueOf(timer))),
                    BarColor.valueOf(config.getString("boss_bat_color")),
                    BarStyle.SOLID
            );

            @Override
            public void run() {
                timer -= 1;

                bossBar.addPlayer((Player) commandSender);
                bossBar.setTitle(ChatColor.translateAlternateColorCodes('&',  config.getString("boss_bar_title").replace("%time%", String.valueOf(timer))));
                bossBar.setProgress((double) timer / config.getInt("teleport_timer"));

                if (timer == 0) {
                    RtpManager.startTeleportation(player);

                    ChatUtil.sendTitle(player,  "&d&lВы успешно телепортированы! ", "Ваши координаты: " + player.getLocation().getX() + " " + player.getLocation().getY() + " " + player.getLocation().getZ());
                    bossBar.removeAll();
                    cancel();
                }
            }
        }.runTaskTimer(TeleportCommand.getInstance(), 0L, 20L);

        ChatUtil.sendMessage(player, "Скоро вы будете телепортирован!");

        cooldowns.put(player, System.currentTimeMillis());

        return true;
    }
}
