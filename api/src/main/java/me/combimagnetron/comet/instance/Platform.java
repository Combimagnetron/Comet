package me.combimagnetron.comet.instance;

import me.combimagnetron.comet.data.Identifier;

import java.util.UUID;

public interface Platform {

    Type type();

    default boolean proxy() {
        return type().proxy();
    }

    UUID uniqueIdentifier();

    static Platform velocity() {
        return new Impl(Type.VELOCITY, UUID.randomUUID());
    }

    static Platform paper() {
        return new Impl(Type.PAPER, UUID.randomUUID());
    }

    record Impl(Type type, UUID uniqueIdentifier) implements Platform {

    }

    interface Type {
        Type PAPER = Type.of(false, Identifier.of("comet", "paper"));
        Type VELOCITY = Type.of(true, Identifier.of("comet", "velocity"));

        boolean proxy();

        Identifier identifier();

        static Type of(boolean proxy, Identifier identifier) {
            return new Impl(proxy, identifier);
        }

        record Impl(boolean proxy, Identifier identifier) implements Type {

        }

    }

}
