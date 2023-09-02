package me.combimagnetron.lagoon;

import dagger.Module;
import dagger.Provides;

@Module
public class DependencyProvider {
    private final CometBase<?> cometBase = null;

    @Provides CometBase<?> cometBase() {
        return cometBase;
    }

}
