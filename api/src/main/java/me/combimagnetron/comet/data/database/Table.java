package me.combimagnetron.comet.data.database;

import io.lettuce.core.api.StatefulRedisConnection;
import me.combimagnetron.comet.data.Row;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.data.database.redis.RedisTable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface Table {

    Table insert(Row<?> row);

    Table put(Object... objects);

    interface TableFactory<T extends Table> {

        T table(Identifier identifier, StatefulRedisConnection<byte[], byte[]> redisConnection);

        final class RedisTableFactory implements TableFactory<RedisTable> {
            private final Map<Identifier, RedisTable> redisTableMap = new ConcurrentHashMap<>();

            public static TableFactory<RedisTable> factory() {
                return new RedisTableFactory();
            }

            @Override
            public RedisTable table(Identifier identifier, StatefulRedisConnection<byte[], byte[]> redisConnection) {
                return new RedisTable(redisConnection);//redisTableMap.put(identifier, new RedisTable(redisConnection));
            }
        }

    }

}
