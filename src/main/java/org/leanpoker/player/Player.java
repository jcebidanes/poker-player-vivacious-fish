package org.leanpoker.player;

import org.leanpoker.player.model.GameState;
import org.leanpoker.player.model.card.Card;
import org.leanpoker.player.model.utils.GameParser;

import com.fasterxml.jackson.databind.JsonNode;

public class Player {

    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonNode request) {
        GameState gameState = GameParser.parse(request);

        long currentPot = gameState.getPot();
        long currentCall = gameState.getCurrentBuyIn();

        long currentPlayerCount = getPlayerCount(gameState);

        if (shouldFold(currentPot, currentCall, currentPlayerCount)) {
            return 0;
        }
        return (int)currentCall;
    }

    public static void showdown(JsonNode game) {
    }

    private static boolean shouldFold(long currentPot, long currentMinCall, long currentPlayerCount) {
        float winP = getWinProbability(currentPlayerCount);
        float gameCoef = winP*currentPot - (1 - winP)*(currentMinCall);

        return gameCoef <= 0;
    }

    private static long getPlayerCount(GameState gameState) {
        return gameState.getPlayers().stream()
                .filter(playerState -> playerState.getStack() > 0)
                .toList()
                .size();
    }

    private static Card[] getHand(Player player) {}

    private static float getWinProbability(long playersLeft) {


        if (playersLeft > 2) {
            return 0;
        }
        return 1;
    }
}
