package org.common.model;

import lombok.Data;

import java.util.List;

@Data
public class Group {

    private Player leaderGroup;
    private List<Player> players;
}
