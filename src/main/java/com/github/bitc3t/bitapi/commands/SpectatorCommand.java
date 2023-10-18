package com.github.bitc3t.bitapi.commands;

import com.github.bitc3t.bitapi.BitAPI;
import com.github.bitc3t.bitapi.objects.BitPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SpectatorCommand {

    private final BitAPI plugin;

    public SpectatorCommand(BitAPI plugin) {
        this.plugin = plugin;
    }

    public void execute(String[] args, Player player) {
        String playerName = args[1];
        Player p = Bukkit.getPlayer(playerName);

        BitPlayer bitPlayer = this.plugin.getBitPlayer(p);

        if(bitPlayer.isSpectator()) {
            bitPlayer.setSpectator(false);

            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&6" + '\u270E' + "&7] &f" + playerName + " has been &cremoved from being a spectator."));
        } else {
            bitPlayer.setSpectator(true);

            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&6" + '\u270E' + "&7] &f" + playerName + " has been &amade a spectator."));
        }
    }
}
