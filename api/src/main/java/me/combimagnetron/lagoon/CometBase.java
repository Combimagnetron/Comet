package me.combimagnetron.lagoon;

import me.combimagnetron.lagoon.event.EventBus;
import me.combimagnetron.lagoon.internal.network.Network;

public interface CometBase<T> {

    Network network();

    EventBus eventBus();

    T plugin();

}
