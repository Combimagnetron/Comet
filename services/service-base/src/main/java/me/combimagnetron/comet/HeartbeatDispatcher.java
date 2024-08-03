package me.combimagnetron.comet;

import me.combimagnetron.comet.concurrency.Scheduler;
import me.combimagnetron.comet.util.Duration;
import me.combimagnetron.generated.InstanceHeartbeatMessage;

import java.util.concurrent.TimeUnit;

public class HeartbeatDispatcher {
    private final CometBase<?> base = CometBase.comet();

    protected HeartbeatDispatcher() {
        Scheduler.repeat(this::dispatch, Duration.of(1L, TimeUnit.MINUTES));
    }

    private void dispatch() {
        base.channels().serviceChannel().send(InstanceHeartbeatMessage.of(base.instanceUuid()));
    }
}
