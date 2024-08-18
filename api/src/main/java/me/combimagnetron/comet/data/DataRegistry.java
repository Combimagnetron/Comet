package me.combimagnetron.comet.data;

public interface DataRegistry {
    DataObject<?> add(Identifier identifier, DataObject<?> object);

    DataObject<?> get(Identifier identifier);
}
