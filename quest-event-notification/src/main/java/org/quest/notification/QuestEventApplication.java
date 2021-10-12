package org.quest.notification;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class QuestEventApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(QuestEventApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }
}
