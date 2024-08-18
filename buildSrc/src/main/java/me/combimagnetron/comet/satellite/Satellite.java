package me.combimagnetron.comet.satellite;

import me.combimagnetron.comet.CometPlugin;
import org.gradle.api.invocation.Gradle;

public class Satellite {
    private final static SatelliteIdRegistry REGISTRY = new SatelliteIdRegistry(CometPlugin.PROJECT.getRootProject().file("sats/registry.cmt").toPath());

    public static SatelliteIdRegistry registry() {
        return REGISTRY;
    }

}
