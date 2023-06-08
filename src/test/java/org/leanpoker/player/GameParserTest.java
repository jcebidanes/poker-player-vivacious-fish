package org.leanpoker.player;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.leanpoker.player.model.GameState;
import org.leanpoker.player.model.utils.GameParser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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

}
