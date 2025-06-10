package me.combimagnetron.comet.data.database;

import me.combimagnetron.comet.data.Row;
import me.combimagnetron.comet.data.database.redis.RedisDatabase;
import me.combimagnetron.comet.data.Identifier;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface Database {

    Summary save(DataStorageArguments arguments);

    Summary save();

    Table table(Identifier identifier);

    static Database redis(Credentials credentials) {
        try {
            return new RedisDatabase(credentials);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    default void test() {
        Database database = Database.redis(Credentials.redis(null, null, 0, null));
        Table table = database.table(Identifier.of("users"));
        table.insert(Row.row("uuid", UUID.class)).insert(Row.row("name", String.class))
                .put();
    }

    interface DataStorageArguments {

        Identifier identifier();

    }

    record Summary(boolean success, long average, long total) {

        public static Summary failed(long total, int tasks) {
            return new Summary(false, total / tasks, total);
        }

        public static Summary success(long total, int tasks) {
            return new Summary(true, total / tasks, total);
        }


    }

}
