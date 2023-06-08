package org.leanpoker.player.model.card;

import org.leanpoker.player.model.card.utils.RankTypeDeserializer;
import org.leanpoker.player.model.card.utils.SuitTypeDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class Card {

    @JsonDeserialize(using = RankTypeDeserializer.class)
    private RankType rank;

    @JsonDeserialize(using = SuitTypeDeserializer.class)
    private SuitType suit;

}
