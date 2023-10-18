package com.github.bitc3t.bitapi.commands;

import com.github.bitc3t.bitapi.BitAPI;
import com.github.bitc3t.bitapi.objects.BitPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class InfoPlayerCommand {

    private final BitAPI plugin;

    public InfoPlayerCommand(BitAPI plugin) {
        this.plugin = plugin;
    }

    public void execute(String[] args, Player player) {
        String playerName = args[1];

        Player p = Bukkit.getPlayer(playerName);
        BitPlayer bp = this.plugin.getBitPlayer(p);

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m                        "));
        player.sendMessage("Player: " +bp.getPlayer().getName());
        player.sendMessage("UUID: " + bp.getUUID());
        player.sendMessage("Score: " + bp.getScore());
        player.sendMessage("Team.Name: " + bp.getTeam().getShortName());
        player.sendMessage("Team.Color: " + ChatColor.translateAlternateColorCodes('&',
                bp.getTeam().getTeamColor() + "🛡"));
        player.sendMessage("Spectating: " + bp.isSpectator());
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m                        "));
    }
}
