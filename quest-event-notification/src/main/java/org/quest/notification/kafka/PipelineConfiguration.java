package org.quest.notification.kafka;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.StreamsConfig;
import org.quest.notification.pipeline.KafkaStreamsOrchestrator;
import org.quest.notification.pipeline.Pipeline;
import org.quest.notification.pipeline.QuestEventPlayerPipeline;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Properties;

@Data
@Configuration
@RequiredArgsConstructor
public class PipelineConfiguration {

    private final Environment environment;

    @Value("${quest-notification.kafka.consumer.internalTopic}")
    private String dataReceiverPipelineInternalTopic;
    @Value("${quest-notification.kafka.consumer.externalTopic}")
    private String dataReceiverPipelineExternalTopic;

    @Bean
    KafkaStreamsOrchestrator kafkaStreamsOrchestrator(Pipeline questEventPlayerPipeline) {
        KafkaStreamsOrchestrator kafkaStreamsOrchestrator = new KafkaStreamsOrchestrator();

        Properties properties = new Properties();
        properties.setProperty(StreamsConfig.APPLICATION_ID_CONFIG,
                environment.getProperty("quest-notification.kafka.consumer.applicationId"));
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                environment.getProperty("quest-notification.kafka.consumer.bootstrap-servers"));

        kafkaStreamsOrchestrator.saveStream(questEventPlayerPipeline.topology(), properties);
        return kafkaStreamsOrchestrator;
    }

    @Bean
    Pipeline dataReceiverPipeline() {
        return new QuestEventPlayerPipeline(
                dataReceiverPipelineInternalTopic, dataReceiverPipelineExternalTopic);
    }

}
