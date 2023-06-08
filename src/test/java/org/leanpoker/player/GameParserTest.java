package org.leanpoker.player;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameParserTest {

    private String fileContents;

    @BeforeEach
    void setUp() throws IOException {
        this.fileContents = IOUtils.toString(GameParserTest.class.getClassLoader().getResourceAsStream("gameState.json"), Charset.forName("UTF-8"));
    }

    @Test
    void testParse_HappyPath() throws JsonProcessingException {
        final GameState actualGameState = GameParser.parse(new ObjectMapper().readTree(fileContents));
        assertNotNull(actualGameState);
    }

    @Test
    void hasBadStartingHand_WithPair_shouldBeFalse() throws JsonProcessingException {
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

    @Test
    void hasStartingHand_HaveStrongHand() {
        PlayerState playerState = buildHand(RankType.K, SuitType.CLUBS, RankType.TEN, SuitType.SPADES);
        assertFalse(playerState.hasBadStartingHand());
    }

    private PlayerState buildHand(RankType rank1, SuitType suit1, RankType rank2, SuitType suit2 ) {
        Card card1 = Card.builder().rank(rank1).suit(suit1).build();
        Card card2 = Card.builder().rank(rank2).suit(suit2).build();

        return PlayerState.builder().holeCards(List.of(card1, card2)).build();
    }



}
