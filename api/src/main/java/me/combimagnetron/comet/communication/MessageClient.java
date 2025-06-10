package me.combimagnetron.comet.communication;

import me.combimagnetron.comet.communication.message.MessageChannel;
import me.combimagnetron.comet.communication.message.redis.RedisMessageClient;
import me.combimagnetron.comet.config.annotation.Config;
import me.combimagnetron.comet.config.annotation.Optional;
import me.combimagnetron.comet.data.Identifier;

import java.util.Set;

public interface MessageClient {

    Set<MessageChannel> channels();

    void terminate();

    void post(Message packet, MessageChannel channel);

    void post(Message packet, MessageChannel channel, ProtocolCallback callback);

    void registerChannel(MessageChannel channel);

    MessageChannel channel(Identifier identifier);

    static MessageClient redis(Settings settings) {
        return RedisMessageClient.of(settings.host(), settings.port(), settings.password());
    }

    static MessageClient redis(String host, int port, String password) {
        return RedisMessageClient.of(host, port, password);
    }

    static MessageClient redis(String uri) {
        return RedisMessageClient.of(uri);
    }

    enum Type {
        REDIS, PULSAR, NONE
    }

    @Config
    record Settings(@Optional String uri, @Optional String username, @Optional String host, @Optional Integer port, String password, Type type) {

    }

    @Config
    class MessageClientSettings {
        @Optional private String uri;
        @Optional private String username;
        @Optional private String host;
        @Optional private Integer port;
        @Optional private Type type;
        private String password;

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
