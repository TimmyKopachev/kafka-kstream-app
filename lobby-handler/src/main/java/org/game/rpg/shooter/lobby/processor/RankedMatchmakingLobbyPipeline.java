package org.game.rpg.shooter.lobby.processor;

import java.util.Properties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.game.rpg.shooter.lobby.config.properties.RankedMatchmakingLobbyPipelineProperties;
import org.game.rpg.shooter.messaging.model.MatchmakingLobby;
import org.game.rpg.shooter.messaging.model.SearchMatchmaking;
import org.game.rpg.shooter.messaging.pipeline.StreamPipeline;
import org.game.rpg.shooter.messaging.serialization.CommonStreamConfig;

@Slf4j
@AllArgsConstructor
public class RankedMatchmakingLobbyPipeline implements StreamPipeline {

  final ReadyLobbyAggregator readyLobbyAggregator;
  final RankedMatchmakingLobbyPipelineProperties rankedPipelineProperties;

  @Override
  public Properties initProperties() {
    Properties properties = new Properties();
    properties.put(
        StreamsConfig.APPLICATION_ID_CONFIG, rankedPipelineProperties.getApplicationId());
    properties.put(
        StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, rankedPipelineProperties.getBootstrapServer());
    return properties;
  }

  @Override
  public Topology buildTopology() {
    StreamsBuilder streamsBuilder = new StreamsBuilder();

    KStream<SearchMatchmaking, MatchmakingLobby> gameLobbyEventStream =
        streamsBuilder.stream(
                rankedPipelineProperties.getInputTopic(),
                Consumed.with(
                    CommonStreamConfig.getSerde(SearchMatchmaking.class),
                    CommonStreamConfig.getSerde(String.class)))
            .groupBy(
                (k, v) -> k,
                Grouped.with(CommonStreamConfig.getSerde(SearchMatchmaking.class), Serdes.String()))
            .aggregate(
                MatchmakingLobby::init,
                readyLobbyAggregator,
                Materialized.with(
                    CommonStreamConfig.getSerde(SearchMatchmaking.class),
                    CommonStreamConfig.getSerde(MatchmakingLobby.class)))
            .toStream();

    gameLobbyEventStream
        .filter((k, v) -> isReadyToStartLobby(v))
        .peek((k, v) -> logLobbyCreationEvent(v))
        .to(rankedPipelineProperties.getOutputTopic());

    return streamsBuilder.build();
  }

  private boolean isReadyToStartLobby(MatchmakingLobby matchmakingLobby) {
    return matchmakingLobby.getPlayerIDs().size()
        == matchmakingLobby.getMode().getRequiredPlayersCount();
  }

  private static void logLobbyCreationEvent(MatchmakingLobby v) {
    log.info("-------------");
    log.info(
        "L0BBY HAS BEEN STARTED : {} FOR : mode=[{}/{}], ranked=[{}/{}]",
        v.getLobbyID(),
        v.getMode(),
        v.getMode().getRequiredPlayersCount(),
        v.getRankDetails().isRanked(),
        v.getRankDetails().getRank());
    log.info("-------------");
  }
}
