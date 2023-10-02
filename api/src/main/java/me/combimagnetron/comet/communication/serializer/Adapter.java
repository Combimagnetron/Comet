package me.combimagnetron.comet.communication.serializer;

public interface Adapter<T> {

    T deserialize(byte[] bytes);

    byte[] serialize(T object);

}
