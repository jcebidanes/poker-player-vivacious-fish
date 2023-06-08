package org.leanpoker.player.model;

import java.util.Comparator;
import java.util.List;

import org.leanpoker.player.model.card.Card;
import org.leanpoker.player.model.card.RankType;
import org.leanpoker.player.model.utils.StatusDeserializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerState {

    private long id;

    private String name;

    @JsonDeserialize(using = StatusDeserializer.class)
    private Status status;

    private String version;

    private long stack;

    private long bet;

    @JsonProperty("hole_cards")
    private List<Card> holeCards;

    public boolean hasBadStartingHand() {

        List<Card> tempHoleCards = holeCards.stream().sorted(Comparator.comparingInt(c -> c.getRank().getRank())).toList();

        if(hasPair()) return false;
        else if( isHighCard(0) && isHighCard(1) ) return false;
        else if(hasAce() && areCardsSuited() ) return false;

        else if(areCardsSuited() && tempHoleCards.get(0).getRank().equals(RankType.SEVEN) && tempHoleCards.get(1).getRank().equals(RankType.EIGHT)) return false;
        else if(areCardsSuited() && tempHoleCards.get(0).getRank().equals(RankType.EIGHT) &&
                ( tempHoleCards.get(1).getRank().equals(RankType.NINE) || tempHoleCards.get(1).getRank().equals(RankType.TEN))
        ) {
            return false;
        }
        else if(areCardsSuited() && tempHoleCards.get(0).getRank().equals(RankType.NINE) && (
                tempHoleCards.get(1).getRank().equals(RankType.TEN) || tempHoleCards.get(1).getRank().equals(RankType.J) || tempHoleCards.get(1).getRank().equals(RankType.Q) || tempHoleCards.get(1).getRank().equals(RankType.K)
        )) {
            return false;
        }



        return true;
    }

    public boolean hasPair() {
        return getHoleCards().get(0).getRank().equals(getHoleCards().get(1).getRank());
    }

    public boolean hasAce() {
        return getHoleCards().get(0).getRank().equals(RankType.A) || getHoleCards().get(1).getRank().equals(RankType.A);
    }

    public boolean areCardsSuited() {
        return getHoleCards().get(0).getSuit().equals(getHoleCards().get(1).getSuit());
    }

    public boolean isHighCard(int index) {
        return getHoleCards().get(index).getRank().getRank() >= 10;
    }

}
