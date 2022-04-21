package org.game.rpg.shooter.messaging.pipeline;

import java.util.Properties;
import org.apache.kafka.streams.Topology;

public interface StreamPipeline {

  Properties initProperties();

  Topology buildTopology();
}
