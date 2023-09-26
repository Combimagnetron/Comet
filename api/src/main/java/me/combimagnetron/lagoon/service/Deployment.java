package me.combimagnetron.lagoon.service;

import me.combimagnetron.lagoon.config.annotation.Config;

@Config
public record Deployment(String name, String image) {

    public static Deployment of(String name, String image) {
        return new Deployment(name, image);
    }

}
