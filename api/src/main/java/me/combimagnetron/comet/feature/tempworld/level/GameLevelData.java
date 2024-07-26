package me.combimagnetron.comet.feature.tempworld.level;

import me.combimagnetron.comet.data.DataObject;
import me.combimagnetron.comet.data.DataRegistry;
import me.combimagnetron.comet.data.Identifier;
import java.util.LinkedHashMap;

public interface GameLevelData {

    DataRegistry dataRegistry = new GameLevelDataRegistry();

    default DataObject<?> addData(Identifier identifier, DataObject<?> dataObject) {
        return dataRegistry.add(identifier, dataObject);
    };

    default DataObject<?> getData(Identifier identifier) {
        return dataRegistry.get(identifier);
    }

    static GameLevelData gameLevelData() {
        return new Impl();
    }

    class GameLevelDataRegistry implements DataRegistry {
        private final LinkedHashMap<Identifier, DataObject<?>> storage = new LinkedHashMap<>();

        @Override
        public DataObject<?> add(Identifier identifier, DataObject<?> object) {
            return storage.put(identifier, object);
        }

        @Override
        public DataObject<?> get(Identifier identifier) {
            return storage.get(identifier);
        }
    }

    class Impl implements GameLevelData {
    };

}
