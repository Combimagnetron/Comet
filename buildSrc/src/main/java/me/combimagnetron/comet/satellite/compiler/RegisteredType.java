package me.combimagnetron.comet.satellite.compiler;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.comet.util.Values;

import java.util.UUID;

public interface RegisteredType<T> {
    RegisteredType<String> STRING = of("String", String.class, ByteBuffer.Adapter.STRING);
    RegisteredType<Integer> INT = of("Int", Integer.class, ByteBuffer.Adapter.INT);
    RegisteredType<Boolean> BOOLEAN = of("Boolean", Boolean.class, ByteBuffer.Adapter.BOOLEAN);
    RegisteredType<Byte> BYTE = of("Byte", Byte.class, ByteBuffer.Adapter.BYTE);
    RegisteredType<Short> SHORT = of("Short", Short.class, ByteBuffer.Adapter.SHORT);
    RegisteredType<Long> LONG = of("Long", Long.class, ByteBuffer.Adapter.LONG);
    RegisteredType<Float> FLOAT = of("Float", Float.class, ByteBuffer.Adapter.FLOAT);
    RegisteredType<Double> DOUBLE = of("Double", Double.class, ByteBuffer.Adapter.DOUBLE);
    RegisteredType<Identifier> IDENTIFIER = of("Identifier", Identifier.class, ByteBuffer.Adapter.IDENTIFIER);
    RegisteredType<UUID> UUID = of("UUID", UUID.class, ByteBuffer.Adapter.UUID);
    //RegisteredType<User<?>> USER = of("User", User.class, ByteBuffer.Adapter.USER);

    Values<RegisteredType<?>> VALUES = Values.of(STRING, INT, BOOLEAN, BYTE, SHORT, LONG, FLOAT, DOUBLE, IDENTIFIER, UUID);

    String name();

    Class<T> type();

    ByteBuffer.Adapter<T> adapter();

    static <T> RegisteredType<T> of(String name, Class<?> value, ByteBuffer.Adapter<T> adapter) {
        return new RegisteredTypeImpl<>(name, (Class<T>) value, adapter);
    }

    record RegisteredTypeImpl<T>(String name, Class<T> type, ByteBuffer.Adapter<T> adapter) implements RegisteredType<T> {
    }

    static void add(RegisteredType<?> type) {
        VALUES.add(type);
    }

}
