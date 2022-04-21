package org.game.rpg.shooter.messaging.autoconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

@Profile("local")
@Configuration
@ConditionalOnExpression("${messaging.config.enabled:true}")
public class LocalEmbeddedKafkaConfiguration {

  @Bean
  @ConditionalOnMissingBean
  EmbeddedKafkaBroker embeddedKafkaBroker() {
    var embeddedKafkaBroker =
        new EmbeddedKafkaBroker(
                1,
                true,
                2,
                "RANKED-MATCHMAKING-LOBBY-EVENT_TOPIC",
                "BASIC-MATCHMAKING-LOBBY-EVENT_TOPIC",
                "APPROVE-MATCHMAKING-FOUND_TOPIC")
            .zkPort(9021)
            .kafkaPorts(9092)
            .zkConnectionTimeout(5000)
            .zkSessionTimeout(5000);

    embeddedKafkaBroker.brokerProperty("listeners", "PLAINTEXT://localhost:9092");
    embeddedKafkaBroker.brokerProperty("port", "9092");
    embeddedKafkaBroker.brokerProperty("compression.type", "producer");

    return embeddedKafkaBroker;
  }
}
