package org.leanpoker.player.model.card;

import java.util.Optional;

public enum RankType {

    TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), J(11), Q(12), K(13), A(14);

    private int rank;

    RankType(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public static Optional<RankType> fromValue(String status) {
        switch (status) {
            case "2":
                return Optional.of(TWO);
            case "3":
                return Optional.of(THREE);
            case "4":
                return Optional.of(FOUR);
            case "5":
                return Optional.of(FIVE);
            case "6":
                return Optional.of(SIX);
            case "7":
                return Optional.of(SEVEN);
            case "8":
                return Optional.of(EIGHT);
            case "9":
                return Optional.of(NINE);
            case "10":
                return Optional.of(TEN);
            case "J":
                return Optional.of(J);
            case "Q":
                return Optional.of(Q);
            case "K":
                return Optional.of(K);
            case "A":
                return Optional.of(A);
            default:
                return Optional.empty();
        }
    }
}
