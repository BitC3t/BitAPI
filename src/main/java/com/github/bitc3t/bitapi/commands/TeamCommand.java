package com.github.bitc3t.bitapi.commands;

import com.github.bitc3t.bitapi.BitAPI;
import com.github.bitc3t.bitapi.objects.BitPlayer;
import com.github.bitc3t.bitapi.objects.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TeamCommand {

    private final BitAPI plugin;

    public TeamCommand(BitAPI plugin) {
        this.plugin = plugin;
    }

    public void execute(Player player, String[] args) {
        String playerName = args[1];
        String newTeam = args[2];

        Team team = this.plugin.getTeamController().getTeamFromName(newTeam);
        BitPlayer bitPlayer = this.plugin.getBitPlayer(Bukkit.getPlayer(playerName));

        Team oldTeam = bitPlayer.getTeam();

        if(team == null || bitPlayer == null) {
            return;
        }

        bitPlayer.changeTeam(team);

        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&7[&6" + '\u270E' + "&7] &f" + playerName + " has been moved to " + team.getTeamColor() + newTeam + " &ffrom " + oldTeam.getTeamColor() + oldTeam.getShortName() + "."));
    }
}
