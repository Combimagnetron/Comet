package me.combimagnetron.lagoon.communication.serializer;

public interface Adapter<T> {

    T deserialize(byte[] bytes);

    byte[] serialize(T object);

}
