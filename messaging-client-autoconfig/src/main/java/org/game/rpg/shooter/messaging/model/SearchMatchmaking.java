package org.game.rpg.shooter.messaging.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchMatchmaking {

  private String mapID;
  private RankDetails rankDetails;
  private MatchmakingMode mode;
}
