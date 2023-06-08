package org.leanpoker.player;

import org.leanpoker.player.model.GameState;
import org.leanpoker.player.model.PlayerState;
import org.leanpoker.player.model.poker.GameSimulator;
import org.leanpoker.player.model.utils.GameParser;

import com.fasterxml.jackson.databind.JsonNode;

public class Player {

    static final String VERSION = "1.0";

    public static int betRequest(JsonNode request) {
        GameState gameState = GameParser.parse(request);

        long currentCall = gameState.getCurrentBuyIn();
        if(gameState.getCommunityCards().isEmpty() && getMyPlayerState(gameState).hasBadStartingHand()) {
            return 0;
        }

        if (shouldFold(gameState)) {
            return 0;
        }
        return (int) currentCall;
    }

    public static void showdown(JsonNode game) {
    }

    private static boolean shouldFold(GameState gameState) {
        double winP = GameSimulator.calculateWinningProbability(gameState);
        double gameCoef = winP * gameState.getPot() - (1 - winP) * (gameState.getCurrentBuyIn());

        return gameCoef <= 0;
    }

    private static PlayerState getMyPlayerState(GameState gameState) {
        int mySeat = (int) gameState.getInAction();
        return gameState.getPlayers().get(mySeat);
    }
}
