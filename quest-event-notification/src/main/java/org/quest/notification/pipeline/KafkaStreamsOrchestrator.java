package org.quest.notification.pipeline;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.StreamsException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.time.Duration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

@Slf4j
public class KafkaStreamsOrchestrator implements DisposableBean, InitializingBean {

    private final Map<String, KafkaStreams> streams = new ConcurrentHashMap<>();
    private final ForkJoinPool forkJoinPool = new ForkJoinPool(20);
    private final ExecutorService executorService = Executors.newFixedThreadPool(20);

    @Override
    public void destroy() {
        streams.values().forEach(this::closeStream);
    }

    private void closeStream(KafkaStreams kafkaStreams) {
        kafkaStreams.close(Duration.ofSeconds(5));
        safeCleanupStream(kafkaStreams);
    }

    private void safeCleanupStream(KafkaStreams kafkaStreams) {
        try {
            kafkaStreams.cleanUp();
        } catch (StreamsException exception) {
            log.warn("Can not clean up stream: {}", exception.getMessage());
        }
    }

    @Override
    public void afterPropertiesSet() {
        forkJoinPool.submit(() -> streams.entrySet().parallelStream().forEach(this::launchStream));
    }

    private void launchStream(Map.Entry<String, KafkaStreams> kafkaStreams) {
        safeCleanupStream(kafkaStreams.getValue());
        CompletableFuture.runAsync(() -> {
            kafkaStreams.getValue().start();
        }, executorService);
    }

    public void saveStream(Topology topology, Properties properties) {
        KafkaStreams kafkaStreams = new KafkaStreams(topology, properties);
        streams.put(properties.getProperty(StreamsConfig.APPLICATION_ID_CONFIG), kafkaStreams);

    }
}
