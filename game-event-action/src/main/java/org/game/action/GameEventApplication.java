package org.game.action;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class GameEventApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(GameEventApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }
}
