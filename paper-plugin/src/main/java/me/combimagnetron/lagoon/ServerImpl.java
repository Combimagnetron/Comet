package me.combimagnetron.lagoon;

import me.combimagnetron.lagoon.communication.MessageClient;
import me.combimagnetron.lagoon.communication.message.redis.RedisMessageClient;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.game.level.GameLevel;
import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.player.GlobalPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class ServerImpl implements Server {
    private final Map<MessageClient.Type, MessageClient> messageClientMap = new TreeMap<>();
    private final ConfigSummary summary;

    ServerImpl(Path configurationPath) throws SerializationException {
        this.summary = ConfigLoader.read(configurationPath);
        this.messageClientMap.putAll(summary.messageClientMap());
    }

    @Override
    public GlobalPlayer<?> playerByUniqueId(UUID uuid) {
        return null;
    }

    @Override
    public Collection<GlobalPlayer<?>> playersOnInstance(Instance instance) {
        return null;
    }

    @Override
    public Collection<GlobalPlayer<?>> onlinePlayers() {
        return null;
    }

    @Override
    public Operation<GameLevel> createGameLevel(Identifier identifier) {
        return null;
    }

    @Override
    public MessageClient messageClient(MessageClient.Type type) {
        return messageClientMap.get(type);
    }

    void registerMessageClient(MessageClient.Type type, MessageClient client) {
        messageClientMap.put(type, client);
    }

    static final class ConfigLoader {
        private final ConfigSummary summary = new ConfigSummary();

        private ConfigLoader(Path configurationPath) throws SerializationException {
            final HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
                    .path(configurationPath)
                    .build();
            CommentedConfigurationNode root = null;
            try {
                root = loader.load();
            } catch (IOException exception) {
                exception.printStackTrace();
                Bukkit.getConsoleSender().sendMessage(Component.text("Failed to load configuration, shutting down", TextColor.fromHexString("#FF1122")));
                Bukkit.shutdown();
            }
            {
                final ConfigurationNode messageClientSettingsNode = root.node("communication");
                MessageClient.MessageClientSettings messageClientSettings = messageClientSettingsNode.get(MessageClient.MessageClientSettings.class);
                switch (messageClientSettings.type()) {
                    default -> {
                        RedisMessageClient client = MessageClient.redis(messageClientSettings.host(), messageClientSettings.port(), messageClientSettings.password());
                        summary.messageClientMap().put(MessageClient.Type.REDIS, client);
                    }
                }
            }
            {
                final ConfigurationNode featureNode = root.node("feature");
            }
        }

        ConfigSummary summary() {
            return summary;
        }

        static ConfigSummary read(Path configurationPath) {
            try {
                return new ConfigLoader(configurationPath).summary();
            } catch (SerializationException e) {
                throw new RuntimeException(e);
            }
        }


    }

    static final class ConfigSummary {
        private final Map<MessageClient.Type, MessageClient> messageClientMap = new TreeMap<>();

        Map<MessageClient.Type, MessageClient> messageClientMap() {
            return messageClientMap;
        }

    }

}
