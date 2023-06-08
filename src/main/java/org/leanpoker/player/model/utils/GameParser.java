package org.leanpoker.player.model.utils;

import org.leanpoker.player.model.GameState;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class GameParser {

    public static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);    }

    public static GameState parse(JsonNode jsonNode) {
        return OBJECT_MAPPER.convertValue(jsonNode, GameState.class);
    }

}
