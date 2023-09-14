package me.combimagnetron.lagoon.data.database;

import io.lettuce.core.api.StatefulRedisConnection;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.data.Row;
import me.combimagnetron.lagoon.data.database.redis.RedisTable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface Table {

    Table insert(Row<?> row);

    interface TableFactory<T extends Table> {

        T table(Identifier identifier, StatefulRedisConnection<byte[], byte[]> redisConnection);

        final class RedisTableFactory implements TableFactory<RedisTable> {
            private final Map<Identifier, RedisTable> redisTableMap = new ConcurrentHashMap<>();

            public static TableFactory<RedisTable> factory() {
                return new RedisTableFactory();
            }


            @Override
            public RedisTable table(Identifier identifier, StatefulRedisConnection<byte[], byte[]> redisConnection) {
                return redisTableMap.put(identifier, new RedisTable(redisConnection));
            }
        }

    }

}
