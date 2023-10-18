package com.github.bitc3t.bitapi.objects.teams;

import com.github.bitc3t.bitapi.BitAPI;
import com.github.bitc3t.bitapi.objects.BitPlayer;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TeamController {

    private Set<Team> teams = new HashSet<>();
    private Set<Team> playingTeams = new HashSet<>();
    private Set<BitPlayer> players = new HashSet<>();
    private final BitAPI plugin;

    public TeamController(BitAPI plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(BitPlayer player) {
        this.players.add(player);
    }

    public void addTeam(Team team) {
        this.teams.add(team);

        if(team.isPlaying()) {
            playingTeams.add(team);
        }
    }

    public Set<Team> getTeams() {
        return this.teams;
    }

    public Set<Team> getPlayingTeam() {
        Set<Team> playingTeams = new HashSet<>();
        for(Team team : this.teams) {
            if(!team.isPlaying()) {
                continue;
            }

            playingTeams.add(team);
        }

        return playingTeams;
    }

    public Set<BitPlayer> getPlayers() {
        return this.players;
    }

    public Team getTeamFromName(String teamName) {
        Team returningTeam = null;
        for(Team team : this.teams) {
            if(team == null) {
                continue;
            }

            if(team.getShortName().equalsIgnoreCase(teamName)) {
                returningTeam = team;
                break;
            }
        }

        return returningTeam;
    }

    public boolean doesPlayerExist(Player player) {
        UUID uuid = player.getUniqueId();

        boolean returningBoolean = false;
        for(BitPlayer bitPlayer : this.players) {
            if(bitPlayer == null) {
                continue;
            }

            if(bitPlayer.getUUID().equals(uuid)) {
                returningBoolean = true;
                break;
            }
        }

        return returningBoolean;
    }
}
