package com.github.bitc3t.bitapi;

import com.github.bitc3t.bitapi.commands.ComExecutor;
import com.github.bitc3t.bitapi.game.GameRegistry;
import com.github.bitc3t.bitapi.objects.BitPlayer;
import com.github.bitc3t.bitapi.objects.teams.TeamController;
import com.github.bitc3t.bitapi.objects.teams.TeamRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class BitAPI extends JavaPlugin {

    private static BitAPI instance;

    // Class / Objects
    private GameRegistry gameRegistry;
    private TeamController teamController;
    private TeamRegistry teamRegistry;

    // Muting Chats
    public static boolean chatMuted = false;
    public static List<Player> mutedPlayers = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        // Controllers / Registries
        this.gameRegistry = new GameRegistry(this);
        this.teamController = new TeamController(this);
        this.teamRegistry = new TeamRegistry(this);

        // Initialization
        try {
            this.gameRegistry.init();
        } catch (IOException e) {
            this.getLogger().info("[API] Failed to initialise controllers.");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BitAPI getInstance() {
        return instance;
    }

    public GameRegistry getGameRegistry() {
        return this.gameRegistry;
    }

    public TeamController getTeamController() {
        return this.teamController;
    }

    public TeamRegistry getTeamRegistry() {
        return this.teamRegistry;
    }

    public BitPlayer getBitPlayer(Player player) {
        BitPlayer returning = null;
        for(BitPlayer bitPlayer : this.teamController.getPlayers()) {
            if(bitPlayer == null) {
                continue;
            }

            if(bitPlayer.getUUID().equals(player.getUniqueId())) {
                returning = bitPlayer;
                break;
            }
        }

        return returning;
    }

    private void registerCommands() {
        this.getCommand("bitapi").setExecutor(new ComExecutor(this));
        this.getCommand("bitapi").setTabCompleter(new ComExecutor(this));
    }
}
