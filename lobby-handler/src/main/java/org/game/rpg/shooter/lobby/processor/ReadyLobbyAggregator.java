package org.game.rpg.shooter.lobby.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.Aggregator;
import org.game.rpg.shooter.messaging.model.MatchmakingLobby;
import org.game.rpg.shooter.messaging.model.SearchMatchmaking;

@Slf4j
public class ReadyLobbyAggregator
    implements Aggregator<SearchMatchmaking, String, MatchmakingLobby> {

  @Override
  public MatchmakingLobby apply(
      SearchMatchmaking searchMatchmaking, String playerUUID, MatchmakingLobby matchmakingLobby) {

    if (searchMatchmaking.getMode().getRequiredPlayersCount()
        == matchmakingLobby.getPlayerIDs().size()) {

      var aggMatchmakingLobby = MatchmakingLobby.init();
      aggMatchmakingLobby.setLobbyDetails(aggMatchmakingLobby, searchMatchmaking, playerUUID);

      output(playerUUID, aggMatchmakingLobby);

      return aggMatchmakingLobby;
    }

    matchmakingLobby.setLobbyDetails(matchmakingLobby, searchMatchmaking, playerUUID);

    output(playerUUID, matchmakingLobby);

    return matchmakingLobby;
  }

  private static void output(String playerUUID, MatchmakingLobby aggMatchmakingLobby) {
    log.info(
        "ADDING NEW PLAYER [{}] TO L0BBY: {}/{} -- mode=[{}], ranked=[{}/{}]",
        playerUUID,
        aggMatchmakingLobby.getPlayerIDs().size(),
        aggMatchmakingLobby.getMode().getRequiredPlayersCount(),
        aggMatchmakingLobby.getMode(),
        aggMatchmakingLobby.getRankDetails().isRanked(),
        aggMatchmakingLobby.getRankDetails().getRank());
  }
}
