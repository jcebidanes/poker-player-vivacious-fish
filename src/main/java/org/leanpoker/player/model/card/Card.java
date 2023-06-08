package org.leanpoker.player.model.card;

import org.leanpoker.player.model.card.utils.RankTypeDeserializer;
import org.leanpoker.player.model.card.utils.SuitTypeDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

@Data
public class Card {

    @JsonDeserialize(using = RankTypeDeserializer.class)
    private RankType rank;

    @JsonDeserialize(using = SuitTypeDeserializer.class)
    private SuitType suit;

}
