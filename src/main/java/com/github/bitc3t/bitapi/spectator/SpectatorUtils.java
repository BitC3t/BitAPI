package com.github.bitc3t.bitapi.spectator;

import com.github.bitc3t.bitapi.BitAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpectatorUtils {

    private static List<Player> spectators = new ArrayList<>();

    public static void setSpectator(Player player) {

        player.setGameMode(GameMode.SPECTATOR);

        player.setFireTicks(0);
        player.setArrowsInBody(0);
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.setAllowFlight(true);
        player.setFlying(true);
        player.getInventory().clear();
        player.setCollidable(false);
        player.setFoodLevel(20);

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.hidePlayer(BitAPI.getInstance(), player);
        }

        spectators.add(player);

        // TODO Maybe set up a GUI for teleportation
    }

    public static void unsetSpectator(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.setFireTicks(0);
        player.setArrowsInBody(0);
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setCollidable(true);
        player.setInvisible(false);

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.showPlayer(BitAPI.getInstance(), player);
        }

        spectators.remove(player);
    }

    public static List<Player> getSpectators() {
        return spectators;
    }
}
