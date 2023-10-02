package me.combimagnetron.comet.connection;

public interface Mapping<T, V> {
    Mapping<Integer, String> MENU_TYPE = new MenuTypeMapping();

    V convert(T t);

}
