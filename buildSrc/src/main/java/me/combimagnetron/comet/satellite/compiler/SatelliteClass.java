package me.combimagnetron.comet.satellite.compiler;

import me.combimagnetron.comet.data.Identifier;

import java.util.Collection;

public record SatelliteClass(Identifier identifier, SatelliteDependency dependency, Collection<SatelliteField> fields) {

    public record Reference(Identifier identifier) {
    }

    public static SatelliteClass of(Identifier identifier, SatelliteDependency dependency, Collection<SatelliteField> fields) {
        return new SatelliteClass(identifier, dependency, fields);
    }

}
