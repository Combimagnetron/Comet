package me.combimagnetron.lagoon.data.database;

import me.combimagnetron.lagoon.data.DataContainer;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.operation.Operation;

public interface Database {

    Operation<Void> save(DataContainer container, DataStorageArguments arguments);

    Operation<DataTable> table(Identifier identifier);




    interface DataStorageArguments {

        Identifier identifier();

    }

}
