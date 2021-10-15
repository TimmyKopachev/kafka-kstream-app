package org.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExternalQuestPlayerRequest {

    private String questUuid;
    private QuestEventStatus status;
    private Player player;

}
