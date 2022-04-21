package org.game.rpg.shooter.messaging.model;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchmakingLobby {
  private static final String PREFIX = "L0BBY";

  private String lobbyID;
  private String mapID;

  private RankDetails rankDetails;
  private MatchmakingMode mode;

  private Queue<String> playerIDs;

  public static MatchmakingLobby init() {
    return MatchmakingLobby.builder()
        .lobbyID(String.format("%s-%s", PREFIX, UUID.randomUUID().getMostSignificantBits()))
        .playerIDs(new ConcurrentLinkedQueue<>())
        .build();
  }

  public void setLobbyDetails(
      MatchmakingLobby aggMatchmakingLobby,
      SearchMatchmaking searchMatchmaking,
      String playerUUID) {

    aggMatchmakingLobby.setMode(searchMatchmaking.getMode());
    aggMatchmakingLobby.setMapID(searchMatchmaking.getMapID());
    aggMatchmakingLobby.setRankDetails(searchMatchmaking.getRankDetails());
    aggMatchmakingLobby.getPlayerIDs().add(playerUUID);
  }
}
