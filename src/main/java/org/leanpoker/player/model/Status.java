package org.leanpoker.player.model;

import java.util.Optional;

public enum Status {
    ACTIVE, FOLDED, OUT;
    public static Optional<Status> fromValue(String status) {
        switch (status) {
            case "active":
                return Optional.of(ACTIVE);
            case "folded":
                return Optional.of(FOLDED);
            case "out":
                return Optional.of(OUT);
            default:
                return Optional.empty();
        }
    }
}
