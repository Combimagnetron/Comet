package me.combimagnetron.comet.config.element;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public interface Section extends ConfigElement {

    <T> @NotNull Section node(Node<T> node);

    <T extends ConfigElement> @NotNull T find(String name);

    <T extends ConfigElement> @Nullable T next(@NotNull Class<T> clazz);

    Collection<ConfigElement> elements();

    String name();

    static Section required(String name) {
        return new RequiredSection(name);
    }

    final class RequiredSection implements Section {
        private final Map<String, ConfigElement> elements = new LinkedHashMap<>();
        private final String name;

        private RequiredSection(String name) {
            this.name = name;
        }

        @Override
        public <T> @NotNull Section node(Node<T> node) {
            elements.put(node.name(), node);
            return this;
        }

        @Override
        public <T extends ConfigElement> @NotNull T find(String name) {
            return (T) elements.get(name);
        }

        @Override
        public <T extends ConfigElement> @Nullable T next(@NotNull Class<T> clazz) {
            return (T) elements.values().parallelStream().filter(element -> element.getClass() == clazz).findFirst().orElse(null);
        }

        @Override
        public Collection<ConfigElement> elements() {
            return elements.values();
        }

        @Override
        public String name() {
            return name;
        }
    }

}
