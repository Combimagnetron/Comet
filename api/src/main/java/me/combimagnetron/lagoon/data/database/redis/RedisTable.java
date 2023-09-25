package me.combimagnetron.lagoon.data.database.redis;

import io.lettuce.core.api.StatefulRedisConnection;
import me.combimagnetron.lagoon.data.Row;
import me.combimagnetron.lagoon.data.database.Table;

public class RedisTable implements Table {
    private final StatefulRedisConnection<byte[], byte[]> redisConnection;

    public RedisTable(StatefulRedisConnection<byte[], byte[]> redisConnection) {
        this.redisConnection = redisConnection;
    }

    @Override
    public Table insert(Row<?> row) {
        return this;
    }

    @Override
    public Table put(Object... objects) {
        return null;
    }
}
