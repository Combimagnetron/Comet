package me.combimagnetron.lagoon.data.database.mongodb;

import me.combimagnetron.lagoon.data.DataContainer;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.data.database.Table;
import me.combimagnetron.lagoon.data.database.Database;
import me.combimagnetron.lagoon.operation.Operation;

public class MongoDatabase implements Database {
    @Override
    public Operation<Void> save(DataContainer container, DataStorageArguments arguments) {
        return null;
    }

    @Override
    public Operation<Table> table(Identifier identifier) {
        return null;
    }
}
