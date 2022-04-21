package org.game.rpg.shooter.screen;

import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.streams.StreamsConfig;
import org.game.rpg.shooter.messaging.model.SearchMatchmaking;
import org.game.rpg.shooter.messaging.serialization.JacksonSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfiguration {

  @Value("${kafka.bootstrap.server:localhost:9092}")
  private String bootstrapServers;

  ProducerFactory<SearchMatchmaking, String> searchMatchmakingProducerFactory() {
    return new DefaultKafkaProducerFactory<>(
        Map.of(
            StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,
            bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
            JacksonSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
            JacksonSerializer.class));
  }

  @Bean("searchMatchmakingActionTemplate")
  KafkaTemplate<SearchMatchmaking, String> searchMatchmakingKafkaTemplate() {
    return new KafkaTemplate<>(searchMatchmakingProducerFactory());
  }
}
