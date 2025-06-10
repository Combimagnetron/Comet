package me.combimagnetron.comet.satellite.compiler;

public record SatelliteDependency(String dependsOn) {
    public static SatelliteDependency of(String content) {
        return new SatelliteDependency(content);
    }
}
