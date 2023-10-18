package com.github.bitc3t.bitapi.events;

import com.github.bitc3t.bitapi.BitAPI;
import com.github.bitc3t.bitapi.objects.BitPlayer;
import com.github.bitc3t.bitapi.spectator.SpectatorUtils;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class RegistryEvents implements Listener {

    private final BitAPI plugin;

    public RegistryEvents(BitAPI plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        for(Player p : SpectatorUtils.getSpectators()) {
            player.hidePlayer(this.plugin, p);
        }

        // Check if this player has a valid BitPlayer Object.
        if(this.plugin.getTeamController().doesPlayerExist(player)) {
            // Update the BitPlayer object with the BukkitPlayer
            BitPlayer bitPlayer = this.plugin.getBitPlayer(player); // (Should not be null)

            if(bitPlayer == null) {
                this.plugin.getLogger().info("[ERROR] BitPlayer is null. (RegistryEvents.java)");
                return;
            }

            bitPlayer.apiSetup();
            this.plugin.getLogger().info("[WP] " + player.getName() + " is already registered to a BitPlayer. Object reconnected!");

            event.joinMessage(PlainTextComponentSerializer.plainText()
                    .deserialize(ChatColor.translateAlternateColorCodes('&',
                            "&7[&a+&7] " + bitPlayer.getTeam().getTeamColor() + "🛡 " + bitPlayer.getPlayer().getName() + " &ahas joined the server.")));
            return;
        }

        // Make a new WalliPlayer Object and update config.
        try {
            this.plugin.getTeamRegistry().registerPlayer(player.getUniqueId(),
                    this.plugin.getTeamRegistry().getSpectatorTeam());
        } catch (IOException e) {
            this.plugin.getLogger().info("[BP] Failed to create a BitPlayer object.");
        }

        this.plugin.getLogger().info("[BP] " + player.getName() + " has been connected to a new BitPlayer object.");

        BitPlayer walliPlayer = this.plugin.getBitPlayer(player);
        event.joinMessage(PlainTextComponentSerializer.plainText()
                .deserialize(ChatColor.translateAlternateColorCodes('&',
                        "&7[&a+&7] " + walliPlayer.getTeam().getTeamColor() + "🛡 " + walliPlayer.getPlayer().getName() + " &ahas joined the server.")));
    }

    @EventHandler
    public void onLeaveEvent(PlayerQuitEvent event) {
        BitPlayer bitPlayer = this.plugin.getBitPlayer(event.getPlayer());

        event.quitMessage(PlainTextComponentSerializer.plainText()
                .deserialize(ChatColor.translateAlternateColorCodes('&',
                        "&7[&c-&7] " + bitPlayer.getTeam().getTeamColor() + "🛡 " + bitPlayer.getPlayer().getName() + " &chas left the server.")));

        bitPlayer.apiDeregister();

        this.plugin.getLogger().info("[BP] " + event.getPlayer().getName() + " has disconnected and hence deregistered.");
    }
}
