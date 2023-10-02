package me.combimagnetron.comet.service;

import me.combimagnetron.comet.config.annotation.Config;

@Config
public record Deployment(String name, String image) {

    public static Deployment of(String name, String image) {
        return new Deployment(name, image);
    }

}
