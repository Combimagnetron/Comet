package me.combimagnetron.comet.communication.dispatcher;

import io.lettuce.core.pubsub.RedisPubSubAdapter;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.event.Dispatcher;
import me.combimagnetron.comet.event.impl.internal.MessageEvent;
import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.generated.SatelliteRegistry;

import java.lang.reflect.InvocationTargetException;

public class MessageListener extends RedisPubSubAdapter<byte[], byte[]> {

    @Override
    public void message(byte[] channel, byte[] message) {
        ByteBuffer buffer = ByteBuffer.of(message);
        Message message1;
        try {
             message1 = (Message) SatelliteRegistry.registry.get((int)buffer.read(ByteBuffer.Adapter.BYTE)).getDeclaredMethod("of", byte[].class).invoke(null, (Object) message);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        Dispatcher.dispatcher().post(new MessageEvent<>(message1));
    }
}
