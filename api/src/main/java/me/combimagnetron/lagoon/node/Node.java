package me.combimagnetron.lagoon.node;

import me.combimagnetron.sunscreen.config.ConfigElement;
import org.jetbrains.annotations.Nullable;

public interface Node<T> extends ConfigElement {

    String name();

    @Nullable T value();

    Class<T> type();

    static <T> Node required(String name, T value) {
        return new RequiredNode(name, value);
    }

    static <T> Node required(String name, Class<T> clazz) {
        return new RequiredNode(name, clazz);
    }

    static <T> Node anonymous(Class<T> clazz) {
        return new AnonymousNode(clazz);
    }

    abstract class SimpleNode<T> implements Node<T> {
        private final String name;
        private final T t;
        private final Class<T> clazz;

        public SimpleNode(String name, T t) {
            this.name = name;
            this.t = t;
            this.clazz = (Class<T>) t.getClass();
        }

        public SimpleNode(String name, Class<T> clazz) {
            this.name = name;
            this.t = null;
            this.clazz = clazz;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public T value() {
            return t;
        }

        @Override
        public Class type() {
            return t.getClass();
        }

    }

    final class RequiredNode<T> extends SimpleNode<T> {

        public RequiredNode(String name, T value) {
            super(name, value);
        }

        public RequiredNode(String name, Class<T> clazz) {
            super(name, clazz);
        }

    }

    final class OptionalNode<T> extends SimpleNode<T> {

        public OptionalNode(String name, T value) {
            super(name, value);
        }
    }

    final class AnonymousNode<T> extends SimpleNode<T> {

        public AnonymousNode(Class<T> clazz) {
            super("", clazz);
        }
    }

}
