package org.game.rpg.shooter.lobby.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.game.rpg.shooter.lobby.config.properties.BasicMatchmakingLobbyPipelineProperties;
import org.game.rpg.shooter.lobby.config.properties.RankedMatchmakingLobbyPipelineProperties;
import org.game.rpg.shooter.lobby.processor.BasicMatchmakingLobbyPipeline;
import org.game.rpg.shooter.lobby.processor.RankedMatchmakingLobbyPipeline;
import org.game.rpg.shooter.lobby.processor.ReadyLobbyAggregator;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(
    value = {
      RankedMatchmakingLobbyPipelineProperties.class,
      BasicMatchmakingLobbyPipelineProperties.class
    })
@AllArgsConstructor
public class LobbyAppConfiguration {

  final BasicMatchmakingLobbyPipelineProperties basicLobbyPipelineProperties;
  final RankedMatchmakingLobbyPipelineProperties rankedLobbyPipelineProperties;

  @Bean
  BasicMatchmakingLobbyPipeline basicMatchmakingLobbyPipeline(
      ReadyLobbyAggregator readyLobbyAggregator) {
    return new BasicMatchmakingLobbyPipeline(readyLobbyAggregator, basicLobbyPipelineProperties);
  }

  @Bean
  RankedMatchmakingLobbyPipeline rankedMatchmakingLobbyPipeline(
      ReadyLobbyAggregator readyLobbyAggregator) {
    return new RankedMatchmakingLobbyPipeline(readyLobbyAggregator, rankedLobbyPipelineProperties);
  }

  @Bean
  ReadyLobbyAggregator readyLobbyAggregator() {
    return new ReadyLobbyAggregator();
  }
}
