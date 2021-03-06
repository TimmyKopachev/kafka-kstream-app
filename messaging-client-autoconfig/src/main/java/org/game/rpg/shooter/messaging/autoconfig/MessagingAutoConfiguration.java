package org.game.rpg.shooter.messaging.autoconfig;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.game.rpg.shooter.messaging.pipeline.KafkaStreamsOrchestrator;
import org.game.rpg.shooter.messaging.pipeline.StreamPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.kafka.annotation.EnableKafka;

@Slf4j
@EnableKafka
@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnExpression("${messaging.config.enabled:true}")
public class MessagingAutoConfiguration {

  @Bean
  KafkaStreamsOrchestrator kafkaStreamsOrchestrator(@Autowired List<StreamPipeline> pipelines) {
    var orchestrator = new KafkaStreamsOrchestrator();

    pipelines.forEach(
        pipeline -> orchestrator.savePipeline(pipeline.buildTopology(), pipeline.initProperties()));

    return orchestrator;
  }
}
