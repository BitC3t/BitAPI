package com.github.bitc3t.bitapi.objects;

import com.github.bitc3t.bitapi.BitAPI;
import com.github.bitc3t.bitapi.objects.teams.Team;
import com.github.bitc3t.bitapi.spectator.SpectatorUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BitPlayer {

    private UUID uuid;
    private Player player;
    private Team team;
    private boolean isSpectator = false;
    private int score = 0;

    public BitPlayer(UUID uuid, Team team, int score) {
        this.uuid = uuid;
        this.team = team;
        this.score = score;

        if(Bukkit.getPlayer(uuid) != null) {
            this.player = Bukkit.getPlayer(uuid);
        } else {
            this.player = null;
        }
    }

    public boolean isSpectator() {
        return this.isSpectator;
    }

    public int getScore() {
        return this.score;
    }

    public void addScore(int a) {
        this.score += a;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Team getTeam() {
        return this.team;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public boolean isValid() {
        if(this.player != null) {
            return true;
        }

        return false;
    }

    public void setSpectator(boolean spectator) {
        this.isSpectator = spectator;

        if(spectator == true) {
            for(ItemStack itemStack : player.getInventory()) {
                if(itemStack == null) {
                    continue;
                }
                player.getLocation().getWorld().dropItemNaturally(player.getLocation(), itemStack);
            }

            player.getInventory().clear();

            SpectatorUtils.setSpectator(player);
            return;
        }

        SpectatorUtils.unsetSpectator(player);
    }

    public void changeTeam(Team team) {
        this.team = team;

        if(!this.isValid()) {
            return;
        }

        BitAPI.getInstance().getTeamRegistry().switchTeam(this.player, team);
    }

    // Run only in onJoinEvents.
    public void apiSetup() {
        this.player = Bukkit.getPlayer(this.uuid);
    }

    // Run only in onLeaveEvents.
    public void apiDeregister() {
        this.player = null;
    }
}
