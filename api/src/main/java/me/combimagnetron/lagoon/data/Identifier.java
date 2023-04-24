package me.combimagnetron.lagoon.data;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Identifier(Namespace namespace, Key key) {
    private static final Namespace DEFAULT = Namespace.of("lagoon");

    @Contract("_, _ -> new")
    public static @NotNull Identifier of(Namespace namespace, Key key) {
        return new Identifier(namespace, key);
    }

    @Contract("_, _ -> new")
    public static @NotNull Identifier of(String namespace, String key) {
        return new Identifier(new Namespace(namespace), new Key(key));
    }

    @Contract("_ -> new")
    public static @NotNull Identifier of(String key) {
        return new Identifier(DEFAULT, new Key(key));
    }

    public record Key(String string) {
        @Contract("_ -> new")
        public static @NotNull Key of(String string) {
            return new Key(string);
        }
    }

    public record Namespace(String string) {
        @Contract("_ -> new")
        public static @NotNull Namespace of(String string) {
            return new Namespace(string);
        }
    }

    public String string() {
        return namespace.string + ":" + key.string;
    }

}
