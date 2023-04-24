package me.combimagnetron.lagoon.data.database.redis;

import me.combimagnetron.lagoon.data.DataContainer;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.data.database.DataTable;
import me.combimagnetron.lagoon.data.database.Database;
import me.combimagnetron.lagoon.data.impl.ExpirableDataContainer;
import me.combimagnetron.lagoon.operation.Operation;

public class RedisDatabase implements Database {
    @Override
    public Operation<Void> save(DataContainer container, DataStorageArguments arguments) {
        return Operation.simple(() -> {
            if (!(container instanceof ExpirableDataContainer)) {
                return;
            }

        });
    }

    @Override
    public Operation<DataTable> table(Identifier identifier) {
        return null;
    }
}
