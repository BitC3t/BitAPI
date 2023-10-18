package com.github.bitc3t.bitapi.events;

import com.github.bitc3t.bitapi.BitAPI;
import com.github.bitc3t.bitapi.objects.BitPlayer;
import com.github.bitc3t.bitapi.objects.teams.Team;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatEvents implements Listener {

    private final BitAPI plugin;

    public ChatEvents(BitAPI plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void chatEvents(AsyncChatEvent event) {
        Player player = event.getPlayer();

        BitPlayer bp = BitAPI.getInstance().getBitPlayer(player);
        Team team = bp.getTeam();

        if(this.plugin.chatMuted) {
            event.setCancelled(true);
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendActionBar(PlainTextComponentSerializer.plainText().deserialize(ChatColor.RED + "Chat is now muted."));
            }
            return;
        }

        if(this.plugin.mutedPlayers.contains(player)) {
            event.setCancelled(true);
            player.sendActionBar(PlainTextComponentSerializer.plainText().deserialize(ChatColor.RED + "You have been muted."));

            return;
        }


        event.setCancelled(true);
        if(player.isOp()) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        team.getTeamColor() + "🛡 [ADMIN] " + player.getName() + "&f: " + PlainTextComponentSerializer
                                .plainText().serialize(event.message())));
            }
            return;
        }
    }
}
