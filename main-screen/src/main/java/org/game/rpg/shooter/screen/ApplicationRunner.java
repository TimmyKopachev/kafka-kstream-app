package org.game.rpg.shooter.screen;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.game.rpg.shooter.messaging.model.MatchmakingMode;
import org.game.rpg.shooter.messaging.model.Rank;
import org.game.rpg.shooter.messaging.model.RankDetails;
import org.game.rpg.shooter.messaging.model.SearchMatchmaking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@SpringBootApplication
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

  @Autowired KafkaTemplate<SearchMatchmaking, String> kafkaTemplate;

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(ApplicationRunner.class)
        .bannerMode(Banner.Mode.OFF)
        .run(args);
  }

  @SneakyThrows
  @Override
  public void run(ApplicationArguments args) {
    while (Boolean.TRUE) {
      var random = new Random();
      SearchMatchmaking searchMatchmaking =
          SearchMatchmaking.builder()
              .mapID("dummy-game-map-ID")
              .mode((random.nextBoolean()) ? MatchmakingMode.DEATHMATCH : MatchmakingMode.FLAG)
              .rankDetails(
                  RankDetails.builder()
                      .ranked(random.nextBoolean())
                      .rank((random.nextBoolean()) ? Rank.DIVINE : Rank.IMMORTAL)
                      .build())
              .build();

      String topic =
          (searchMatchmaking.getRankDetails().isRanked())
              ? "RANKED-MATCHMAKING-LOBBY-EVENT_TOPIC"
              : "BASIC-MATCHMAKING-LOBBY-EVENT_TOPIC";

      log.info(
          "SEARCH MATCHMAKING: mode=[{}/{}], ranked=[{}/{}]",
          searchMatchmaking.getMode(),
          searchMatchmaking.getMode().getRequiredPlayersCount(),
          searchMatchmaking.getRankDetails().isRanked(),
          searchMatchmaking.getRankDetails().getRank());

      kafkaTemplate
          .send(topic, searchMatchmaking, UUID.randomUUID().toString())
          .completable()
          .exceptionally(
              exception -> {
                log.error("Issue occurred during search a game: {}", exception.getMessage());
                return null;
              })
          .thenApply(result -> !Objects.nonNull(result));

      Thread.sleep(3000);
    }
  }
}
