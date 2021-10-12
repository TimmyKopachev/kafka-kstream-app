package org.game.action.kafka.config;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;
import org.common.model.QuestEventPlayerGroupRequest;
import org.common.serialization.JacksonSerializer;
import org.game.action.kafka.adapter.GameEventKafkaPortImpl;
import org.game.action.kafka.adapter.GameEventKafkaSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class KafkaProducerConfiguration {

    private final GameEventProperties gameEventProperties;

    @Bean("gameActionProducerFactory")
    ProducerFactory<String, QuestEventPlayerGroupRequest> gameActionProducerFactory() {
        Map<String, Object> jacksonConfig = new HashMap<>();

        jacksonConfig.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, gameEventProperties.getBootstrapServers());
        jacksonConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        jacksonConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonSerializer.class);

        return new DefaultKafkaProducerFactory<>(jacksonConfig);
    }

    @Bean("gameActionTemplate")
    KafkaTemplate<String, QuestEventPlayerGroupRequest> gameActionTemplate() {
        return new KafkaTemplate<>(gameActionProducerFactory());
    }

    @Bean("gameEventKafkaSender")
    GameEventKafkaSender gameEventKafkaSender() {
        return new GameEventKafkaPortImpl(gameEventProperties.getEntryPointTopic(), gameActionTemplate());
    }

}
