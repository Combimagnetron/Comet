package me.combimagnetron.comet;

import me.combimagnetron.comet.feature.Feature;
import me.combimagnetron.comet.communication.MessageClient;
import me.combimagnetron.comet.communication.message.redis.RedisMessageClient;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.instance.Instance;
import me.combimagnetron.comet.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class ServerImpl implements Server {
    private final transient Set<Feature> featureSet = Collections.synchronizedSet(new HashSet<>());
    private final Map<MessageClient.Type, MessageClient> messageClientMap = new TreeMap<>();
    private final UserManager userManager = new UserManager();
    private final ConfigSummary summary;
    private final JavaPlugin plugin;

    ServerImpl(Path configurationPath, JavaPlugin plugin) throws SerializationException {
        this.summary = ConfigLoader.read(configurationPath);
        this.messageClientMap.putAll(summary.messageClientMap());
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(userManager, plugin);
    }

    @Override
    public User<?> playerByUniqueId(UUID uuid) {
        return userManager.user(uuid);
    }

    @Override
    public Collection<User<?>> playersOnInstance(Instance instance) {
        return null;
    }

    @Override
    public Collection<User<?>> onlinePlayers() {
        return null;
    }

    @Override
    public MessageClient messageClient(MessageClient.Type type) {
        return messageClientMap.get(type);
    }

    @Override
    public @Nullable Feature feature(Identifier identifier) {
        return null;
    }

    @Override
    public <T extends Feature> T feature(Class<T> clazz) {
        return null;
    }

    @Override
    public Feature startFeature(Feature feature) {
        return null;
    }

    @Override
    public Instance instance(Identifier identifier) {
        return null;
    }

    @Override
    public Instance instance(UUID uuid) {
        return null;
    }

    @Override
    public JavaPlugin javaPlugin() {
        return plugin;
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
                    case NONE -> {

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
