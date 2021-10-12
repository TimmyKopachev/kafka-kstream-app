package org.common.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class QuestEventPlayerGroupRequest {

    private String requestUuid = UUID.randomUUID().toString();

    private String questUuid;
    private Group group;
    private QuestEventStatus status;

}
