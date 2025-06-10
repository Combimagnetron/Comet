package me.combimagnetron.comet.data;

public interface Type<T> {

    byte[] serialize();

    Class<T> type();

}
