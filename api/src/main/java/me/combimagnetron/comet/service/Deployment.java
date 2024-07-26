package me.combimagnetron.comet.service;

import me.combimagnetron.comet.config.annotation.Config;

@Config
public record Deployment(String name, String image, int minReplicas, int maxReplicas, int playerInstanceThreshold) {

    public static Deployment of(String name, String image, int minReplicas, int maxReplicas, int playerInstanceThreshold) {
        return new Deployment(name, image, minReplicas, maxReplicas, playerInstanceThreshold);
    }

}
