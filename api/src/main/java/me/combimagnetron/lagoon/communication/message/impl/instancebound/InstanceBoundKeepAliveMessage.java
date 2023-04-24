package me.combimagnetron.lagoon.communication.message.impl.instancebound;

import me.combimagnetron.lagoon.communication.Message;
import me.combimagnetron.lagoon.communication.message.impl.proxybound.ProxyBoundMessage;
import me.combimagnetron.lagoon.instance.Instance;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class InstanceBoundKeepAliveMessage extends InstanceBoundMessage {
    private final Instance target;
    private final KeepAliveId keepAliveId;

    public InstanceBoundKeepAliveMessage(byte[] bytes) {
        super(bytes);
        this.target = null; //readUUID();
        readUUID();
        this.keepAliveId = KeepAliveId.from(readString());
    }

    public InstanceBoundKeepAliveMessage(Instance target, KeepAliveId keepAliveId) {
        super(0x00, null, target);
        this.target = target;
        this.keepAliveId = keepAliveId;
        write();
    }


    @Override
    public @Nullable Instance origin() {
        return null;
    }


    public InstanceBoundKeepAliveMessage clone() {
        return new InstanceBoundKeepAliveMessage(target, keepAliveId);
    }


    @Override
    public void write() {
        writeUUID(target.uniqueIdentifier());
        writeString(Arrays.toString(keepAliveId.data()));
    }

    public record KeepAliveId(byte[] data) {
        public static KeepAliveId create(Instance instance) {
            long time = System.currentTimeMillis();
            byte[] aliveData = (instance.uniqueIdentifier().toString() + time).getBytes();
            return new KeepAliveId(aliveData);
        }

        public static KeepAliveId from(String string) {
            String[] strings = string.replaceAll("[\\[\\] ]+", "").split(",");
            List<Byte> bytes = new LinkedList<>();
            Arrays.stream(strings).toList().forEach(byteString -> bytes.add(Byte.valueOf(byteString)));
            Object[] boxedArray = bytes.toArray();
            int length = boxedArray.length;
            byte[] array = new byte[boxedArray.length];
            for (int i = 0; i < length; i++) {
                array[i] = ((Number) (boxedArray[i])).byteValue();
            }
            return new KeepAliveId(array);
        }

    }

    public class Response extends ProxyBoundMessage {
        private final Instance origin;
        private final KeepAliveId keepAliveId;
        private final PingResponse pingResponse;

        public Response(byte[] bytes) {
            super(bytes);
            this.origin = null /*readUUID()*/;
            this.keepAliveId = KeepAliveId.from(readString());
            this.pingResponse = (PingResponse) readObject();
        }

        public Response(Instance origin, InstanceBoundKeepAliveMessage keepAlivePacket, PingResponse pingResponse) {
            super(0x02, null, origin);
            this.origin = origin;
            this.keepAliveId = keepAlivePacket.keepAliveId;
            this.pingResponse = pingResponse;
            write();
        }


        @Override
        public @Nullable Instance origin() {
            return /*origin.uniqueId()*/ null;
        }

        @Override
        public void write() {
            writeUUID(target.uniqueIdentifier());
            writeString(Arrays.toString(keepAliveId.data()));
            writeObject(pingResponse);
        }
    }

    public record PingResponse(int playerCount, String instanceName) { }
}
