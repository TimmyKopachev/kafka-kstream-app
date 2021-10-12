package org.quest.notification.pipeline;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.common.model.QuestEventPlayerGroupRequest;
import org.common.serialization.CommonStreamConfig;

@Slf4j
@RequiredArgsConstructor
public class QuestEventPlayerPipeline implements Pipeline {

    private final String topic;

    @Override
    public Topology topology() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        streamsBuilder.stream(topic,
                Consumed.with(Serdes.String(), CommonStreamConfig.getSerde(QuestEventPlayerGroupRequest.class)))
                .peek(this::output);

        return streamsBuilder.build();
    }

    void output(String key, QuestEventPlayerGroupRequest eventPlayerGroup) {
        log.info("request has been received {}", key);
        eventPlayerGroup.getGroup().getPlayers().forEach(
                player -> log.info("player {} process a quest {} changed status to {}",
                        player.getName(), eventPlayerGroup.getQuestUuid(), eventPlayerGroup.getStatus())
        );
    }

}
