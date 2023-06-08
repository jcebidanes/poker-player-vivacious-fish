package org.leanpoker.player;

import com.fasterxml.jackson.databind.JsonNode;

public class Player {

    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonNode request) {
        if (shouldFold()) {
            return 0;
        }
        return request.get("minimum_raise");
    }

    public static void showdown(JsonNode game) {
    }

    private static boolean shouldFold() {
        return false;
    }
}
