package me.combimagnetron.comet.service;

import me.combimagnetron.comet.config.annotation.Config;
import me.combimagnetron.comet.data.Identifier;

@Config
public interface Deployment {

    Identifier name();

    String image();

    int minReplicas();

    int maxReplicas();

    int playerInstanceThreshold();

    static Deployment of(Identifier name, String image, int minReplicas, int maxReplicas, int playerInstanceThreshold) {
        return new Impl(name, image, minReplicas, maxReplicas, playerInstanceThreshold);
    }

    static Deployment of(me.combimagnetron.generated.baseservice.Deployment deployment) {
        return of(deployment.name(), deployment.image(), deployment.minReplicas(), deployment.maxReplicas(), deployment.playerInstanceThreshold());
    }

    record Impl(Identifier name, String image, int minReplicas, int maxReplicas, int playerInstanceThreshold) implements Deployment {

        public me.combimagnetron.generated.baseservice.Deployment satellite() {
            return new me.combimagnetron.generated.baseservice.Deployment(name, image, minReplicas, maxReplicas, playerInstanceThreshold);
        }

    }

}
