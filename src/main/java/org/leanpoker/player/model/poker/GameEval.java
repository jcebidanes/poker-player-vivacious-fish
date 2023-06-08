package org.leanpoker.player.model.poker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.leanpoker.player.model.GameState;
import org.leanpoker.player.model.PlayerState;
import org.leanpoker.player.model.Status;
import org.leanpoker.player.model.card.Card;
import org.leanpoker.player.model.card.RankType;
import org.leanpoker.player.model.card.SuitType;

public final class GameEval {

    public static boolean isPreFlop(GameState gameState) {
        return gameState.getCommunityCards().size() == 0;
    }

    public static boolean isFlop(GameState gameState) {
        return gameState.getCommunityCards().size() == 3;
    }

    public static boolean isTurn(GameState gameState) {
        return gameState.getCommunityCards().size() == 4;
    }

    public static boolean isRiver(GameState gameState) {
        return gameState.getCommunityCards().size() == 5;
    }

    public static Card getHighestCard(List<Card> cards) {
        return cards.stream().max(Comparator.comparing(card -> ((Card) card).getRank().ordinal())).get();
    }

    public static boolean hasPair(List<Card> cards) {
        for (Card card : cards) {
            if (cards.stream()
                    .filter(c -> !c.equals(card))
                    .filter(c -> c.getRank().equals(card.getRank()))
                    .count() == 1) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasStrongPair(List<Card> cards) {
        for (Card card : cards) {
            if (card.getRank().ordinal() >= RankType.TEN.ordinal() && cards.stream()
                    .filter(c -> !c.equals(card))
                    .filter(c -> c.getRank().equals(card.getRank()))
                    .count() == 1) {
                return true;
            }
        }
        return false;
    }

    public static RankType getHighestPairRank(List<Card> cards) {
        Map<RankType, Long> sortedHand = sortHand(cards);
        return sortedHand.entrySet().stream()
                .filter(rankTypeLongEntry -> rankTypeLongEntry.getValue() == 2)
                .max(Comparator.comparing(entry -> ((RankType)entry.getKey()).ordinal()))
                .map(Map.Entry::getKey)
                .get();
    }

    public static boolean hasTwoPairs(List<Card> cards) {
        Map<RankType, Long> sortedHand = sortHand(cards);
        return sortedHand.entrySet().stream()
                .filter(rankTypeLongEntry -> rankTypeLongEntry.getValue() == 2)
                .count() >= 2;
    }

    public static boolean hasStraight (List<Card> cards) {
        List<Card> sortedCards = cards.stream()
                .sorted(Comparator.comparing(c -> c.getRank().getRank()))
                .collect(Collectors.toList());
        int diff = 0;
        int count = 0;
        for(int i = 1; i < sortedCards.size(); i++) {
            diff = sortedCards.get(i).getRank().getRank()
                    - sortedCards.get(i-1).getRank().getRank();
            if(diff == 1) {
                count++;
            }else {
                count = 0;
            }
        }
        return (count >= 4);
    }

    public static List<Card> getStraightCards (List<Card> cards) {
        List<Card> sortedCards = cards.stream()
                .sorted(Comparator.comparing(c -> c.getRank().getRank()))
                .collect(Collectors.toList());
        int diff = 0;
        int count = 0;
        List<Card> straightCards = new ArrayList<>();
        for(int i = 1; i < sortedCards.size(); i++) {
            diff = sortedCards.get(i).getRank().getRank()
                    - sortedCards.get(i-1).getRank().getRank();
            if(diff == 1) {
                count++;
                if(straightCards.isEmpty()) straightCards.add(sortedCards.get(i-1));
                straightCards.add(sortedCards.get(i));
            }else {
                count = 0;
                straightCards.clear();
            }
        }
        return straightCards.size() >= 5 ? straightCards : List.of();
    }

    public static boolean has3OfAKind(List<Card> cards) {
        Map<RankType, Long> sortedHand = sortHand(cards);
        return sortedHand.entrySet().stream()
                .anyMatch(rankTypeLongEntry -> rankTypeLongEntry.getValue() == 3);
    }

    public static RankType getHighest3ofAKindRank(List<Card> cards) {
        Map<RankType, Long> sortedHand = sortHand(cards);
        return sortedHand.entrySet().stream()
                .filter(rankTypeLongEntry -> rankTypeLongEntry.getValue() == 3)
                .max(Comparator.comparing(entry -> ((RankType)entry.getKey()).ordinal()))
                .map(Map.Entry::getKey)
                .get();
    }

    public static boolean hasFullHouse(List<Card> cards) {
        return hasPair(cards) && has3OfAKind(cards);
    }

    public static boolean has4OfAKind(List<Card> cards) {
        Map<RankType, Long> sortedHand = sortHand(cards);
        return sortedHand.entrySet().stream()
                .anyMatch(rankTypeLongEntry -> rankTypeLongEntry.getValue() == 4);
    }

    public static RankType get4ofAKindRank(List<Card> cards) {
        Map<RankType, Long> sortedHand = sortHand(cards);
        return sortedHand.entrySet().stream()
                .filter(rankTypeLongEntry -> rankTypeLongEntry.getValue() == 4)
                .max(Comparator.comparing(entry -> ((RankType)entry.getKey()).ordinal()))
                .map(Map.Entry::getKey)
                .get();
    }

    public static boolean hasStraight2(List<Card> cards) {
        for (Card card : cards) {
            int cardRank = card.getRank().ordinal();
            List<Card> otherCards = cards.stream()
                    .filter(c -> !c.equals(card))
                    .toList();

            if (otherCards.stream().anyMatch(c1 -> c1.getRank().ordinal() == (cardRank + 1))
                    && otherCards.stream().anyMatch(c2 -> c2.getRank().ordinal() == (cardRank + 2))
                    && otherCards.stream().anyMatch(c3 -> c3.getRank().ordinal() == (cardRank + 3))
                    && otherCards.stream().anyMatch(c4 -> c4.getRank().ordinal() == (cardRank + 4))) {
                return true;
            }
        }
        return false;
    }

    public static List<Card> getStraightCards2(List<Card> cards) {
        for (Card card : cards) {
            int cardRank = card.getRank().ordinal();
            List<Card> otherCards = cards.stream()
                    .filter(c -> !c.equals(card))
                    .toList();

            Optional<Card> card1 = otherCards.stream().filter(c1 -> c1.getRank().ordinal() == (cardRank + 1))
                    .findFirst();
            Optional<Card> card2 = otherCards.stream().filter(c1 -> c1.getRank().ordinal() == (cardRank + 2))
                    .findFirst();
            Optional<Card> card3 = otherCards.stream().filter(c1 -> c1.getRank().ordinal() == (cardRank + 3))
                    .findFirst();
            Optional<Card> card4 = otherCards.stream().filter(c1 -> c1.getRank().ordinal() == (cardRank + 4))
                    .findFirst();

            if (!card1.isEmpty()
                    && !card2.isEmpty()
                    && !card3.isEmpty()
                    && !card4.isEmpty()) {
                return List.of(card, card1.get(), card2.get(), card3.get(), card4.get());
            }
        }
        return List.of();
    }

    public static RankType getStraightHighestRank(List<Card> cards) {
        return getStraightCards(cards).stream()
                .map(Card::getRank)
                .max(Comparator.comparing(rank -> ((RankType)rank).ordinal()))
                .get();
    }

    public static boolean hasFlush(List<Card> cards) {
        for (Card card : cards) {
            if (cards.stream()
                    .filter(c -> !c.equals(card))
                    .filter(c -> c.getSuit().equals(card.getSuit()))
                    .count() == 4) {
                return true;
            }
        }
        return false;
    }

    public static SuitType getFlushSuit(List<Card> cards) {
        return cards.stream()
                .collect(Collectors.groupingBy(Card::getSuit, Collectors.counting()))
                .entrySet().stream()
                .filter(suitTypeLongEntry -> suitTypeLongEntry.getValue() == 5)
                .findFirst()
                .map(Map.Entry::getKey)
                .get();
    }

    public static boolean hasStraightFlush(List<Card> cards) {
        if (!hasStraight(cards)) {
            return false;
        }
        if (!hasFlush(cards)) {
            return false;
        }

        SuitType flushSuit = getFlushSuit(cards);
        return getStraightCards(cards).stream()
                .allMatch(card -> card.getSuit() == flushSuit);
    }

    public static boolean hasRoyalFlush(List<Card> cards) {
        return hasStraightFlush(cards) && getStraightHighestRank(cards).equals(RankType.A);
    }

    private static List<Card> sortByRank(List<Card> cards) {
        return cards.stream()
                .sorted(Comparator.comparing(card -> ((Card)card).getRank().ordinal()))
                .toList();
    }

    private static Map<RankType, Long> sortHand(List<Card> cards) {
        return cards.stream()
                .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
    }

    public static int compareHands(List<Card> hand1, List<Card> hand2) {
        return getHandRank(hand1) - getHandRank(hand2);
    }

    public static int getHandRank(List<Card> cards) {
        if (hasRoyalFlush(cards)) {
            return 700 + getStraightHighestRank(cards).ordinal();
        }
        if (hasStraightFlush(cards)) {
            return 600 + getStraightHighestRank(cards).ordinal();
        }
        if (has4OfAKind(cards)) {
            return 500 + get4ofAKindRank(cards).ordinal();
        }
        if (hasFullHouse(cards)) {
            return 400 + getHighest3ofAKindRank(cards).ordinal();
        }
        if (has3OfAKind(cards)) {
            return 300 + getHighest3ofAKindRank(cards).ordinal();
        }
        if (hasTwoPairs(cards)) {
            return 200 + getHighestPairRank(cards).ordinal();
        }
        if (hasStrongPair(cards)) {
            return 100 + getHighestPairRank(cards).ordinal();
        }
        return 0;
    }

    public static List<PlayerState> getRemainingPlayers(GameState gameState) {
        return gameState.getPlayers().stream()
                .filter(playerState -> playerState.getStatus().equals(Status.ACTIVE))
                .toList();
    }
}
