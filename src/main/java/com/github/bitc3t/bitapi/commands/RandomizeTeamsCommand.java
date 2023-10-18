package com.github.bitc3t.bitapi.commands;

import com.github.bitc3t.bitapi.BitAPI;
import com.github.bitc3t.bitapi.objects.teams.Team;
import com.github.bitc3t.bitapi.objects.BitPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class RandomizeTeamsCommand {

    private final BitAPI plugin;

    public RandomizeTeamsCommand(BitAPI plugin) {
        this.plugin = plugin;
    }

    public void execute(Player player) {
        int noTeams = BitAPI.getInstance().getTeamController().getPlayingTeam().size();
        int countPlayers = 0;

        for(Player p1 : Bukkit.getOnlinePlayers()) {
            if(p1.isOp()) {
                continue;
            }

            countPlayers++;
        }

        int countPerTeam = (int) Math.ceil((double) countPlayers / noTeams);

        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p.isOp()) {
                continue;
            }

            BitPlayer wp = BitAPI.getInstance().getBitPlayer(p);

            if(wp.getTeam().isPlaying()) {
                continue;
            }

            Team validTeam = this.getTeam(countPerTeam);

            if(validTeam == null) {
                continue;
            }

            Team oldTeam = wp.getTeam();
            wp.changeTeam(validTeam);

            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&6" + '\u270E' + "&7] &f" + p.getName() + "'s team has been set to " + validTeam.getTeamColor() + validTeam.getShortName() + " &ffrom " + oldTeam.getTeamColor() + oldTeam.getShortName() + "."));
        }
    }

    private Team getTeam(int playersPerTeam) {
        Set<Team> teams = BitAPI.getInstance().getTeamController().getPlayingTeam();
        List<Team> teamList = new ArrayList<>();
        for(Team team : teams) {
            teamList.add(team);
            System.out.println(team.getShortName());
        }

        Collections.shuffle(teamList);
        Team chosen = null;

        for(Team team : teamList) {
            if(team.getOnlinePlayers().size() >= playersPerTeam) {
                continue;
            }

            if(!team.isPlaying()) {
                continue;
            }

            chosen = team;
            break;
        }

        return chosen;
    }
}
