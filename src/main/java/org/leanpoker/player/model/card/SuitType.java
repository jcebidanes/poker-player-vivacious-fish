package org.leanpoker.player.model.card;

import java.util.Optional;

public enum SuitType {
    CLUBS, SPADES, HEARTS, DIAMONDS;

    public static Optional<SuitType> fromValue(String status) {
        switch (status) {
            case "clubs":
                return Optional.of(CLUBS);
            case "spades":
                return Optional.of(SPADES);
            case "hearts":
                return Optional.of(HEARTS);
            case "diamonds":
                return Optional.of(DIAMONDS);
            default:
                return Optional.empty();
        }
    }
}
