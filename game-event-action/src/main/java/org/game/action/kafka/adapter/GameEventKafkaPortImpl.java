package org.game.action.kafka.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.common.model.QuestEventPlayerGroupRequest;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class GameEventKafkaPortImpl implements GameEventKafkaSender {

    private final String entryPointTopic;

    private final KafkaTemplate<String, QuestEventPlayerGroupRequest> kafkaTemplate;

    @Override
    public void sendEvent(QuestEventPlayerGroupRequest groupRequest) {
        kafkaTemplate.send(entryPointTopic, groupRequest.getRequestUuid(), groupRequest)
                .completable()
                .exceptionally(exception -> null)
                .thenApply(result -> !Objects.nonNull(result));
        log.info("request has been sent to kafka topic {}", entryPointTopic);
    }

}
