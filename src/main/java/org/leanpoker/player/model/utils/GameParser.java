package org.leanpoker.player.model.utils;

import org.leanpoker.player.model.GameState;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class GameParser {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static GameState parse(JsonNode jsonNode) {
        return OBJECT_MAPPER.convertValue(jsonNode, GameState.class);
    }

}
