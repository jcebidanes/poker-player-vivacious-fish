package org.leanpoker.player.model;

import java.util.LinkedList;
import java.util.List;

import org.leanpoker.player.model.card.Card;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GameState {

    @JsonProperty("tournament_id")
    private String tournamentId;

    @JsonProperty("game_id")
    private String gameId;

    private long round;

    @JsonProperty("bet_index")
    private int betIndex;

    @JsonProperty("small_blind")
    private long smallBlind;

    @JsonProperty("current_buy_in")
    private long currentBuyIn;

    private long pot;

    @JsonProperty("minimum_raise")
    private long minimumRaise;

    private long dealer;

    private long orbits;

    @JsonProperty("in_action")
    private long inAction;

    private List<PlayerState> players = new LinkedList<>();

    @JsonProperty("community_cards")
    private List<Card> communityCards = new LinkedList<>();
}
