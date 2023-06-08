package org.leanpoker.player.model.utils;

import java.io.IOException;

import org.leanpoker.player.model.Status;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class StatusDeserializer extends JsonDeserializer<Status> {

    @Override
    public Status deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        try {
            if (node.asText() != null) {
                return Status.fromValue(node.asText()).orElse(null);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}

