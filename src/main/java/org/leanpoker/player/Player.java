package org.leanpoker.player;

import com.fasterxml.jackson.databind.JsonNode;

public class Player {

    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonNode request) {
        int current_pot = Integer.valueOf(request.get("pot").asText());
        int current_call = Integer.valueOf(request.get("current_buy_in").asText());

        if (shouldFold(current_pot, current_call)) {
            return 0;
        }
        return current_call;
    }

    public static void showdown(JsonNode game) {
    }

    private static boolean shouldFold(int current_pot, int current_min_call) {
        float winP = getWinProbability();
        float gameCoef = winP*current_pot - (1 - winP)*(current_min_call);

        return gameCoef <= 0;
    }

    private static float getWinProbability() {
        return 1;
    }
}
