package me.combimagnetron.halo.transformer;

import me.combimagnetron.comet.data.DataContainer;
import me.combimagnetron.comet.data.DataObject;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.comet.service.Deployment;
import me.combimagnetron.comet.user.User;
import me.combimagnetron.comet.util.Values;
import net.kyori.adventure.audience.Audience;

import java.util.Objects;
import java.util.UUID;

public interface Transformer<T> {
    Transformer<UUID> UUID = of("Uuid", "UUID", java.util.UUID.class, ByteBuffer.Adapter.UUID);
    Transformer<String> STRING = of("String", "STRING", String.class, ByteBuffer.Adapter.STRING);
    Transformer<Integer> INT = of("Int", "INT", Integer.class, ByteBuffer.Adapter.INT);
    Transformer<Double> DOUBLE = of("Double", "DOUBLE", Double.class, ByteBuffer.Adapter.DOUBLE);
    Transformer<Identifier> IDENTIFIER = of("Identifier", "IDENTIFIER", Identifier.class, ByteBuffer.Adapter.IDENTIFIER);
    Transformer<Deployment> DEPLOYMENT = of("Deployment", "DEPLOYMENT", Deployment.class, ByteBuffer.Adapter.DEPLOYMENT);
    Transformer<DataObject> DATA_OBJECT = of("DataObject", "DATA_OBJECT", DataObject.class, ByteBuffer.Adapter.DATA_OBJECT);
    Transformer<Byte[]> BYTE_ARRAY = of("Bytes", "BYTE_ARRAY", Byte[].class, ByteBuffer.Adapter.BYTE_ARRAY);
    Transformer<DataContainer> DATA_CONTAINER = of("DataContainer", "DATA_CONTAINER", DataContainer.class, ByteBuffer.Adapter.DATA_CONTAINER);
    Transformer<User> USER = of("User", "USER", User.class, ByteBuffer.Adapter.USER);
    Values<Transformer<?>> VALUES = Values.of(UUID, STRING, INT, DOUBLE, IDENTIFIER, DEPLOYMENT, DATA_OBJECT, BYTE_ARRAY, DATA_CONTAINER, USER);

    String identifier();

    String adapterName();

    Class<T> clazz();

    ByteBuffer.Adapter<?> adapter();

    static <T> Transformer<T> of(String identifier, String adapterName, Class<T> clazz, ByteBuffer.Adapter<?> adapter) {
        return new Entry<>(identifier, adapterName, clazz, adapter);
    }

    static <T> Transformer<T> find(String string) {
        return (Transformer<T>) VALUES.values().stream().filter(transformer -> Objects.equals(transformer.identifier(), string)).findAny().orElseThrow();
    }

    record Entry<T>(String identifier, String adapterName, Class<T> clazz, ByteBuffer.Adapter<?> adapter) implements Transformer<T> {

    }

}
