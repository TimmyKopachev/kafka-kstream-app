package org.quest.notification.pipeline;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.common.model.ExternalQuestPlayerRequest;
import org.common.model.QuestEventPlayerGroupRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class QuestPlayerEventMapper implements ValueMapper<QuestEventPlayerGroupRequest, List<ExternalQuestPlayerRequest>> {

    @Override
    public List<ExternalQuestPlayerRequest> apply(QuestEventPlayerGroupRequest groupRequest) {
        log.info("QuestPlayerEventMapper creates requests for each player of {}", groupRequest.getRequestUuid());
        List<ExternalQuestPlayerRequest> questPlayerRequests = new ArrayList<>();
        groupRequest.getGroup().getPlayers().forEach(
                player -> questPlayerRequests.add(
                        new ExternalQuestPlayerRequest(groupRequest.getQuestUuid(), groupRequest.getStatus(), player))
        );
        return questPlayerRequests;
    }

}
