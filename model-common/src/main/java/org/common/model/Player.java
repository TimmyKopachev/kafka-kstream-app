package org.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Player {

    private String uuid = UUID.randomUUID().toString();
    private String name;

    public Player(String name) {
        this.name = name;
    }
}
