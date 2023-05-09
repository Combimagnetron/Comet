package me.combimagnetron.lagoon.communication;

import me.combimagnetron.lagoon.communication.message.MessageChannel;
import me.combimagnetron.lagoon.communication.message.pulsar.PulsarMessageChannel;
import me.combimagnetron.lagoon.communication.message.pulsar.PulsarMessageClient;
import me.combimagnetron.lagoon.communication.message.redis.RedisMessageClient;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.operation.Operation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.net.URI;
import java.util.Set;

public interface MessageClient {

    Operation<Set<MessageChannel>> channels();

    Operation<Void> terminate();

    Operation<Void> send(Message packet, MessageChannel channel);

    Operation<Void> send(Message packet, MessageChannel channel, ProtocolCallback callback);

    Operation<Void> registerChannel(MessageChannel channel);

    Operation<MessageChannel> channel(Identifier identifier);

    static RedisMessageClient redis(String host, int port, String password) {
        return RedisMessageClient.of(host, port, password);
    }

    static PulsarMessageClient pulsar(String connectionString) {
        return PulsarMessageClient.of(connectionString);
    }

    enum Type {
        REDIS, PULSAR
    }

    @ConfigSerializable
    class MessageClientSettings {
        @Nullable private String uri;
        @Nullable private String username;
        @Nullable private String host;
        @Nullable private Integer port;
        private String password;
        private Type type;

        public String uri() {
            return uri;
        }

        public String username() {
            return username;
        }

        public String host() {
            return host;
        }

        public Integer port() {
            return port;
        }

        public String password() {
            return password;
        }

        public Type type() {
            return type;
        }
    }


}
