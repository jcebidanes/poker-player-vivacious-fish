package org.leanpoker.player;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.leanpoker.player.model.GameState;
import org.leanpoker.player.model.PlayerState;
import org.leanpoker.player.model.card.Card;
import org.leanpoker.player.model.card.RankType;
import org.leanpoker.player.model.card.SuitType;
import org.leanpoker.player.model.utils.GameParser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameParserTest {

    private String fileContents;

    @BeforeEach
    void setUp() throws IOException {
        this.fileContents = IOUtils.toString(
                Objects.requireNonNull(GameParserTest.class.getClassLoader().getResourceAsStream("gameState.json")),
                StandardCharsets.UTF_8
        );
    }

    @Test
    void testParse_HappyPath() throws JsonProcessingException {
        final GameState actualGameState = GameParser.parse(new ObjectMapper().readTree(fileContents));
        assertNotNull(actualGameState);
    }

    @Test
    void hasBadStartingHand_WithPair_shouldBeFalse() {
        PlayerState playerState = buildHand(RankType.THREE, null, RankType.THREE, null);
        assertFalse(playerState.hasBadStartingHand());
    }

    @Test
    void hasBadStartingHand_WithAceSuited_shouldBeFalse() {
        PlayerState playerState = buildHand(RankType.THREE, SuitType.CLUBS, RankType.A, SuitType.CLUBS);
        assertFalse(playerState.hasBadStartingHand());
    }

    @Test
    void hasBadStartingHand_LowCardNoAce() {
        PlayerState playerState = buildHand(RankType.THREE, SuitType.CLUBS, RankType.TWO, SuitType.CLUBS);
        assertTrue(playerState.hasBadStartingHand());
    }

    @Test
    void hasBadStartingHand_MidCardSeven() {
        PlayerState playerState = buildHand(RankType.EIGHT, SuitType.CLUBS, RankType.SEVEN, SuitType.CLUBS);
        assertFalse(playerState.hasBadStartingHand());
    }

    @Test
    void hasBadStartingHand_MidCardEight() {
        PlayerState playerState = buildHand(RankType.TEN, SuitType.CLUBS, RankType.EIGHT, SuitType.CLUBS);
        assertFalse(playerState.hasBadStartingHand());
    }

    @Test
    void hasBadStartingHand_MidCardNine() {
        PlayerState playerState = buildHand(RankType.K, SuitType.CLUBS, RankType.NINE, SuitType.CLUBS);
        assertFalse(playerState.hasBadStartingHand());
    }

    private PlayerState buildHand(RankType rank1, SuitType suit1, RankType rank2, SuitType suit2 ) {

        Card card1 = new Card();
        Card card2 = new Card();

        card1.setRank(rank1);
        card1.setSuit(suit1);

        card2.setRank(rank2);
        card2.setSuit(suit2);

        return PlayerState.builder().holeCards(List.of(card1, card2)).build();
    }



}
