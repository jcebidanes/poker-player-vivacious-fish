package org.leanpoker.player;

import java.util.List;

import org.leanpoker.player.model.GameState;
import org.leanpoker.player.model.PlayerState;
import org.leanpoker.player.model.card.Card;
import org.leanpoker.player.model.utils.GameParser;

import com.fasterxml.jackson.databind.JsonNode;

public class Player {

    static final String VERSION = "1.0";

    public static int betRequest(JsonNode request) {
        GameState gameState = GameParser.parse(request);

        long currentPot = gameState.getPot();
        long currentCall = gameState.getCurrentBuyIn();

        long currentPlayerCount = getPlayerCount(gameState);


        if(gameState.getCommunityCards().isEmpty() && getMyPlayerState(gameState).hasBadStartingHand()) {
            return 0;
        }

        if (shouldFold(currentPot, currentCall, currentPlayerCount, getHand(getMyPlayerState(gameState)))) {
            return 0;
        }
        return (int) currentCall;
    }

    public static void showdown(JsonNode game) {
    }

    private static boolean shouldFold(long currentPot, long currentMinCall, long currentPlayerCount, List<Card> currentHand) {
//        if (currentHand)
        float winP = getWinProbability(currentPlayerCount);
        float gameCoef = winP * currentPot - (1 - winP) * (currentMinCall);

        return gameCoef <= 0;
    }

    private static long getPlayerCount(GameState gameState) {
        return gameState.getPlayers().stream()
                .filter(playerState -> playerState.getStack() > 0)
                .toList()
                .size();
    }

    private static PlayerState getMyPlayerState(GameState gameState) {
        int mySeat = (int) gameState.getInAction();
        return gameState.getPlayers().get(mySeat);
    }

    private static List<Card> getHand(PlayerState playerState) {
        return playerState.getHoleCards();
    }

    private static float getWinProbability(long playersLeft) {

        if (playersLeft > 2) {
            return 0;
        }
        return 1;
    }
}
