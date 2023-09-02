package me.combimagnetron.lagoon.communication;

import me.combimagnetron.lagoon.communication.message.MessageChannel;
import me.combimagnetron.lagoon.communication.message.redis.RedisMessageClient;
import me.combimagnetron.lagoon.data.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.Set;

public interface MessageClient {

    Set<MessageChannel> channels();

    void terminate();

    void post(Message packet, MessageChannel channel);

    void post(Message packet, MessageChannel channel, ProtocolCallback callback);

    void registerChannel(MessageChannel channel);

    MessageChannel channel(Identifier identifier);

    static RedisMessageClient redis(String host, int port, String password) {
        return RedisMessageClient.of(host, port, password);
    }

    enum Type {
        REDIS, PULSAR, NONE
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
