package org.quest.notification.pipeline;

import org.apache.kafka.streams.Topology;

public interface Pipeline {

    Topology topology();
}
