package org.leanpoker.player.model.poker;

import java.util.ArrayList;
import java.util.List;

import org.leanpoker.player.model.card.Card;
import org.leanpoker.player.model.card.RankType;
import org.leanpoker.player.model.card.SuitType;

public class GameEvalTest {

    public void testHasPair() {
        List<Card> cards = new ArrayList<>();
        Card card = Card.builder().suit(SuitType.CLUBS).rank(RankType.A).build();

    }
}
