package me.combimagnetron.comet.satellite.compiler;

import java.util.HashMap;
import java.util.Map;

public interface SatelliteDependencyResolver {

    final class DependencyTree {
        private final Map<SatelliteClass.Reference, SatelliteDependency> dependencyMap = new HashMap<>();
    }


}
