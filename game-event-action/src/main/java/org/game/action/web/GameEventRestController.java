package org.game.action.web;

import lombok.AllArgsConstructor;
import org.common.model.Group;
import org.common.model.Player;
import org.common.model.QuestEventPlayerGroupRequest;
import org.common.model.QuestEventStatus;
import org.game.action.kafka.adapter.GameEventKafkaSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class GameEventRestController {

    private final GameEventKafkaSender gameEventKafkaSender;

    @RequestMapping("/publish")
    public String sendPlayer() {
        gameEventKafkaSender.sendEvent(createMockRequest());
        return "mock request has been sent to kafka topic!";
    }

    QuestEventPlayerGroupRequest createMockRequest() {
        QuestEventPlayerGroupRequest eventPlayerGroupRequest = new QuestEventPlayerGroupRequest();
        eventPlayerGroupRequest.setStatus(QuestEventStatus.COMPLETED);
        eventPlayerGroupRequest.setQuestUuid("quest-uuid-00001");

        Player player1 = new Player("player-1");
        Player player2 = new Player("player-2");
        Player player3 = new Player("player-3");

        Group group = new Group();
        group.setLeaderGroup(player1);
        group.setPlayers(List.of(player1, player2, player3));

        eventPlayerGroupRequest.setGroup(group);

        return eventPlayerGroupRequest;
    }
}
