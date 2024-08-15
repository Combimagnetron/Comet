package me.combimagnetron.comet.satellite.compiler;

import java.util.Collection;

public record SatelliteClass(String name, Collection<SatelliteDependency> dependencies, Collection<SatelliteField> fields) {

    public record Reference(String name) {
    }

}
