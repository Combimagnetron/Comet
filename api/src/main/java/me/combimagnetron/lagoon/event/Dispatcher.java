package me.combimagnetron.lagoon.event;

import me.combimagnetron.lagoon.operation.Operation;

public interface Dispatcher {

    Operation<Void> postCancellable(Class<? extends Event> type, Object event);

    Operation<Void> postAsync(Class<? extends Event> type, Object event);

    Operation<Void> postSync(Class<? extends Event> type, Object event);

}
