package me.combimagnetron.comet.feature.tempworld.level;

import me.combimagnetron.comet.data.DataObject;
import me.combimagnetron.comet.data.DataRegistry;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.operation.Operation;

import java.util.LinkedHashMap;

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
