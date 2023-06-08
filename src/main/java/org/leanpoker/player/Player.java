package org.leanpoker.player;

import com.fasterxml.jackson.databind.JsonNode;

public class Player {

    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonNode request) {
        int current_pot = Integer.valueOf(request.get("pot").asText());
        int current_call = Integer.valueOf(request.get("current_buy_in").asText());

        int current_player_count = getPlayerCount(request.get("players"));

        if (shouldFold(current_pot, current_call, current_player_count)) {
            return 0;
        }
        return current_call;
    }

    public static void showdown(JsonNode game) {
    }

    private static boolean shouldFold(int current_pot, int current_min_call, int current_player_count) {
        float winP = getWinProbability(current_player_count);
        float gameCoef = winP*current_pot - (1 - winP)*(current_min_call);

        return gameCoef <= 0;
    }

    private static int getPlayerCount(JsonNode players) {
        int count = 0;
        if (players.isArray()) {
            for (JsonNode item : players) {
                int player_stack = Integer.valueOf(item.get("stack").asText());
                if (player_stack > 0) {
                    count++;
                }
            }
        }
        return count;
    }

    private static float getWinProbability(int playersLeft) {
        if (playersLeft > 2) {
            return 0;
        }
        return 1;
    }
}
