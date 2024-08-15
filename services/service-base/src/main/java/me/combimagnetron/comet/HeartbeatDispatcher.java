package me.combimagnetron.comet;

import me.combimagnetron.comet.communication.message.MessageChannel;
import me.combimagnetron.comet.concurrency.Scheduler;
import me.combimagnetron.comet.util.Duration;
import me.combimagnetron.generated.InitialInstanceHeartbeatMessage;
import me.combimagnetron.generated.InstanceHeartbeatMessage;

import java.util.concurrent.TimeUnit;

public class HeartbeatDispatcher {
    private final CometBase<?> base = CometBase.comet();
    private final MessageChannel messageChannel = base.channels().serviceChannel();

    protected HeartbeatDispatcher() {
        Scheduler.repeat(this::dispatch, Duration.of(1L, TimeUnit.MINUTES));
        messageChannel.send(InitialInstanceHeartbeatMessage.of(base.instanceUuid(), null));
    }

    private void dispatch() {
        messageChannel.send(InstanceHeartbeatMessage.of(base.instanceUuid()));
    }
}
