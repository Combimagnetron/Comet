package me.combimagnetron.comet.satellite.compiler;

import me.combimagnetron.comet.satellite.matcher.Token;
import me.combimagnetron.comet.satellite.matcher.TokenizedResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public interface SatelliteDependencyResolver {

    static SatelliteDependency resolve(TokenizedResult result) {
        if (Objects.equals(result.content(4), "extends")) {
            return SatelliteDependency.of(result.content(5));
        }

        return null;
    }

    final class DependencyTree {
        private final Map<SatelliteClass.Reference, SatelliteDependency> dependencyMap = new HashMap<>();
    }


}
