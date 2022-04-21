package org.game.rpg.shooter.messaging.model;

import lombok.Getter;

@Getter
public enum MatchmakingMode {
  DEATHMATCH(8),
  FLAG(10);

  private final int requiredPlayersCount;

  MatchmakingMode(int requiredPlayersCount) {
    this.requiredPlayersCount = requiredPlayersCount;
  }
}
