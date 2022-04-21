package org.game.rpg.shooter.lobby;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ApplicationRunner {

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(ApplicationRunner.class)
        .bannerMode(Banner.Mode.OFF)
        .run(args);
  }
}
