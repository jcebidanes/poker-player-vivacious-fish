package org.leanpoker.player.model;

import java.util.LinkedList;
import java.util.List;

import org.leanpoker.player.model.card.Card;
import org.leanpoker.player.model.utils.StatusDeserializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

@Data
class PlayerState {

    private long id;

    private String name;

    @JsonDeserialize(using = StatusDeserializer.class)
    private Status status;

    private String version;

    private long stack;

    private long bet;

    @JsonProperty("hole_cards")
    private List<Card> holeCards = new LinkedList<>();

}
