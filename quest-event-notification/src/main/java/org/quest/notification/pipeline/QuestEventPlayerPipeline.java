package org.quest.notification.pipeline;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.common.model.ExternalQuestPlayerRequest;
import org.common.model.QuestEventPlayerGroupRequest;
import org.common.serialization.CommonStreamConfig;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class QuestEventPlayerPipeline implements Pipeline {

    private final String internalTopic;
    private final String externalTopic;
    private final QuestPlayerEventMapper questPlayerEventMapper;

    @Override
    public Topology topology() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        streamsBuilder
                .stream(internalTopic,
                        Consumed.with(Serdes.String(), CommonStreamConfig.getSerde(QuestEventPlayerGroupRequest.class)))
                .peek(this::output)
                .filter((key, groupRequest) -> Objects.nonNull(groupRequest.getGroup())
                        && !CollectionUtils.isEmpty(groupRequest.getGroup().getPlayers()))
                .flatMapValues(questPlayerEventMapper)
                .to(externalTopic,
                        Produced.with(Serdes.String(), CommonStreamConfig.getSerde(ExternalQuestPlayerRequest.class)));

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
