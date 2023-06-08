package org.leanpoker.player.model.card.utils;

import java.io.IOException;

import org.leanpoker.player.model.card.SuitType;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class SuitTypeDeserializer extends JsonDeserializer<SuitType> {

    @Override
    public SuitType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        try {
            if (node.asText()!= null) {
                return SuitType.fromValue(node.asText()).orElse(null);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}

