package org.leanpoker.player.model.poker;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.leanpoker.player.model.GameState;
import org.leanpoker.player.model.PlayerState;
import org.leanpoker.player.model.card.Card;
import org.leanpoker.player.model.card.RankType;
import org.leanpoker.player.model.card.SuitType;

public class GameSimulator {

    private static final int TOTAL_HANDS = 1000;

    public static double calculateWinningProbability(GameState gameState) {
        List<Card> playerHand = getMyPlayerState(gameState).getHoleCards();
        List<Card> communityCards = gameState.getCommunityCards();

        List<Card> myCards = mergeCards(playerHand, communityCards);
        List<Card> remainingCards = generateRemainingCards(myCards);

        int winCount = 0;

        for (int i = 0; i < TOTAL_HANDS; i++) {
            List<Card> gameCommunityCards = new ArrayList<>(List.copyOf(communityCards));
            List<Card> gameRemainingCards = new ArrayList<>(List.copyOf(remainingCards));

            if (GameEval.isPreFlop(gameState)) {
                gameCommunityCards = List.of(
                        gameRemainingCards.get(0),
                        gameRemainingCards.get(1),
                        gameRemainingCards.get(2),
                        gameRemainingCards.get(3),
                        gameRemainingCards.get(4));
                gameRemainingCards.remove(0);
                gameRemainingCards.remove(0);
                gameRemainingCards.remove(0);
                gameRemainingCards.remove(0);
                gameRemainingCards.remove(0);
            } else if (GameEval.isFlop(gameState)) {
                gameCommunityCards.add(gameRemainingCards.get(0));
                gameRemainingCards.remove(0);
                gameCommunityCards.add(gameRemainingCards.get(0));
                gameRemainingCards.remove(0);
            } else if (GameEval.isTurn(gameState)) {
                gameCommunityCards.add(gameRemainingCards.get(0));
                gameRemainingCards.remove(0);
            }

            List<PlayerState> remainingPlayers = GameEval.getRemainingPlayers(gameState);
            for (int j = 0; i < remainingPlayers.size() - 1; j++) {
                List<Card> otherPlayerHand = List.of(
                        remainingCards.get(0),
                        remainingCards.get(1),
                        gameCommunityCards.get(0),
                        gameCommunityCards.get(1),
                        gameCommunityCards.get(2));

                if (GameEval.compareHands(playerHand, otherPlayerHand) < 0) {
                    break;
                }

                gameRemainingCards.remove(0);
                gameRemainingCards.remove(0);
            }

            winCount++;
        }

        return (double) winCount / (TOTAL_HANDS - 1); // Exclude player's own hand
    }

    public static List<Card> generateRemainingCards(List<Card> usedCards) {
        List<Card> allGameCards = new ArrayList<>(usedCards);
        List<Card> extraCards = new ArrayList<>();

        while(allGameCards.size() < 17) {
            Card card = generateRandomCard();
            if (allGameCards.stream().noneMatch(c -> c.getRank() == card.getRank() && c.getSuit() == card.getSuit())) {
                allGameCards.add(card);
                extraCards.add(card);
            }
        }

        return extraCards;
    }

    private static Card generateRandomCard() {
        Card card = new Card();
        card.setRank(RankType.values()[RandomUtils.nextInt(0,12)]);
        card.setSuit(SuitType.values()[RandomUtils.nextInt(0,3)]);
        return card;
    }

    private static PlayerState getMyPlayerState(GameState gameState) {
        int mySeat = (int) gameState.getInAction();
        return gameState.getPlayers().get(mySeat);
    }

    private static List<Card> mergeCards(List<Card> playerHand, List<Card> communityCards) {
        List<Card> cards = new ArrayList<>(playerHand);
        cards.addAll(communityCards);
        return List.copyOf(cards);
    }



}
