package org.game.action.kafka.adapter;

import org.common.model.QuestEventPlayerGroupRequest;

public interface GameEventKafkaSender {

    void sendEvent(QuestEventPlayerGroupRequest groupRequest);
}
