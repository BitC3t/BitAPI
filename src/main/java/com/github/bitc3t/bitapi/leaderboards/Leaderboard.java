package com.github.bitc3t.bitapi.leaderboards;

import com.github.bitc3t.bitapi.BitAPI;
import com.github.bitc3t.bitapi.objects.BitPlayer;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Leaderboard {

    private HashMap<BitPlayer, FastBoard> boardMap = new HashMap<>();

    public void enable() {
        BitAPI.getInstance().getTeamController().getPlayers().forEach(bp -> {
            if(bp.isValid()) {
                Player player = bp.getPlayer();
                FastBoard fb = new FastBoard(player);

                boardMap.put(bp, fb);
            }
        });
    }

    public HashMap<BitPlayer, FastBoard> getBoardMap() {
        return this.boardMap;
    }

    public FastBoard addPlayer(BitPlayer bp) {
        FastBoard fastBoard = new FastBoard(bp.getPlayer());
        boardMap.put(bp, fastBoard);

        return fastBoard;
    }
}
