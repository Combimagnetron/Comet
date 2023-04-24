package me.combimagnetron.lagoon.game.level;

import com.google.common.collect.LinkedHashMultimap;
import me.combimagnetron.lagoon.data.DataObject;
import me.combimagnetron.lagoon.data.DataRegistry;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.operation.Operation;

import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public interface GameLevelData {

    DataRegistry dataRegistry = new GameLevelDataRegistry();

    default Operation<DataObject<?,?>> addData(Identifier identifier, DataObject<?,?> dataObject) {
        return dataRegistry.add(identifier, dataObject);
    };

    default Operation<DataObject<?,?>> getData(Identifier identifier) {
        return dataRegistry.get(identifier);
    }

    static GameLevelData gameLevelData() {
        return new Impl();
    }

    class GameLevelDataRegistry implements DataRegistry {
        private final LinkedHashMap<Identifier, DataObject<?,?>> storage = new LinkedHashMap<>();

        @Override
        public Operation<DataObject<?,?>> add(Identifier identifier, DataObject<?,?> object) {
            return Operation.executable(() -> storage.put(identifier, object));
        }

        @Override
        public Operation<DataObject<?,?>> get(Identifier identifier) {
            return Operation.executable(() -> storage.get(identifier));
        }
    }

    class Impl implements GameLevelData {
    };

}
