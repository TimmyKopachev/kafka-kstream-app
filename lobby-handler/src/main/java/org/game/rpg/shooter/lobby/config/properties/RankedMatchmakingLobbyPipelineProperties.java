package org.game.rpg.shooter.lobby.config.properties;

import org.game.rpg.shooter.messaging.pipeline.StreamPipelineProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pipeline.matchmaking.ranked")
public class RankedMatchmakingLobbyPipelineProperties extends StreamPipelineProperties {}
