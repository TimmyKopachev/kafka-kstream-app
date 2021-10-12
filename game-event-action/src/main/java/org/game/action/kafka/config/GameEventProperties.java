package org.game.action.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "game-events")
public class GameEventProperties {

    private String entryPointTopic;
    private String bootstrapServers;
}
