package org.leanpoker.player.model.poker;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.leanpoker.player.model.card.Card;
import org.leanpoker.player.model.card.RankType;
import org.leanpoker.player.model.card.SuitType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameEvalTest {

    @Test
    public void testHasPairAndHas3OfAKind() {
        List<Card> cards = getCardList(
            Card.builder().suit(SuitType.CLUBS).rank(RankType.A).build(),
            Card.builder().suit(SuitType.HEARTS).rank(RankType.A).build(),
            Card.builder().suit(SuitType.DIAMONDS).rank(RankType.FOUR).build()
        );
        assertTrue(GameEval.hasPair(cards));
        assertFalse(GameEval.has3OfAKind(cards));
        assertFalse(GameEval.has4OfAKind(cards));
        cards.add(Card.builder().suit(SuitType.DIAMONDS).rank(RankType.A).build());
        assertFalse(GameEval.hasPair(cards));
        assertTrue(GameEval.has3OfAKind(cards));
        assertFalse(GameEval.has4OfAKind(cards));
        cards.add(Card.builder().suit(SuitType.CLUBS).rank(RankType.TEN).build());
        assertFalse(GameEval.hasPair(cards));
        assertTrue(GameEval.has3OfAKind(cards));
        assertFalse(GameEval.has4OfAKind(cards));
        cards.add(Card.builder().suit(SuitType.SPADES).rank(RankType.A).build());
        assertFalse(GameEval.hasPair(cards));
        assertFalse(GameEval.has3OfAKind(cards));
        assertTrue(GameEval.has4OfAKind(cards));
    }

    @Test
    public void testHasStraight() {
        List<Card> cards = getCardList(
                Card.builder().suit(SuitType.CLUBS).rank(RankType.A).build(),
                Card.builder().suit(SuitType.HEARTS).rank(RankType.Q).build(),
                Card.builder().suit(SuitType.DIAMONDS).rank(RankType.TEN).build()
        );
        assertFalse(GameEval.hasStraight(cards));
        assertEquals(GameEval.getStraightCards(cards).size(), 0);
        cards.add(Card.builder().suit(SuitType.SPADES).rank(RankType.EIGHT).build());
        assertFalse(GameEval.hasStraight(cards));
        assertEquals(GameEval.getStraightCards(cards).size(), 0);
        cards.add(Card.builder().suit(SuitType.SPADES).rank(RankType.K).build());
        assertFalse(GameEval.hasStraight(cards));
        assertEquals(GameEval.getStraightCards(cards).size(), 0);
        cards.add(Card.builder().suit(SuitType.SPADES).rank(RankType.J).build());
        assertTrue(GameEval.hasStraight(cards));
        List<Card> straightList = GameEval.getStraightCards(cards);
        assertEquals(straightList.size(), 5);
        assertTrue(straightList.contains(cards.get(0)));
        assertTrue(straightList.contains(cards.get(1)));
        assertTrue(straightList.contains(cards.get(2)));
        assertTrue(straightList.contains(cards.get(4)));
        assertTrue(straightList.contains(cards.get(5)));
    }

    private List<Card> getCardList (Card... cards) {
        return Stream.of(cards).collect(Collectors.toList());
    }
}
