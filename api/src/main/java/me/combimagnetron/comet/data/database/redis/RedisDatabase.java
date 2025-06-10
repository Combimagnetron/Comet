package me.combimagnetron.comet.data.database.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import me.combimagnetron.comet.data.database.Credentials;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.data.database.Table;
import me.combimagnetron.comet.data.database.Database;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ExecutionException;

public class RedisDatabase implements Database {
    private final Table.TableFactory<RedisTable> factory = Table.TableFactory.RedisTableFactory.factory();
    private final StatefulRedisConnection<byte[], byte[]> redisClient;

    public RedisDatabase(Credentials credentials) throws ExecutionException, InterruptedException {
        if (!(credentials instanceof Credentials.RedisCredentials redisCredentials)) {
            redisClient = null;
            return;
        }
        redisClient = RedisClient.create()
                .connectAsync(ByteArrayCodec.INSTANCE,
                        RedisURI.builder()
                                .withHost(redisCredentials.host())
                                .withPort(redisCredentials.port())
                                .withPassword(redisCredentials.password())
                                .build()).get();

    }

    @Override
    public Summary save(@Nullable DataStorageArguments arguments) {
        return Summary.failed(0L, 0);
    }

    @Override
    public Summary save() {
        return save(null);
    }

    @Override
    public Table table(Identifier identifier) {
        return factory.table(identifier, redisClient);
    }
}
