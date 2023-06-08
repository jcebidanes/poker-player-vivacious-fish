package org.leanpoker.player.model.card;

import org.leanpoker.player.model.card.utils.RankTypeDeserializer;
import org.leanpoker.player.model.card.utils.SuitTypeDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Card {

    @JsonDeserialize(using = RankTypeDeserializer.class)
    private RankType rank;

    @JsonDeserialize(using = SuitTypeDeserializer.class)
    private SuitType suit;

}
