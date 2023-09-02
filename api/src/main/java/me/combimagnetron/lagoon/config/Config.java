package me.combimagnetron.lagoon.config;

import me.combimagnetron.lagoon.config.element.Node;
import me.combimagnetron.lagoon.config.element.Section;

import java.util.Collection;
import java.util.TreeMap;

public sealed interface Config permits Config.Impl {

    <T> Config node(Node<T> node);

    Config section(Section section);

    Collection<Node<?>> nodes();

    Collection<Section> sections();

    static Config config() {
        return new Impl();
    }

    static Config config(Settings settings) {
        return new Impl(settings);
    }

    final class Impl implements Config {
        private final TreeMap<String, Section> sections = new TreeMap<>();
        private final TreeMap<String, Node<?>> nodes = new TreeMap<>();
        private Settings settings = Settings.settings();

        void test() {
            Config.config()
                    .node(Node.required("a", 0))
                    .section(
                            Section.required("foo")
                                    .node(Node.required("bar", Boolean.class))
                                    .node(Node.required("baz", Double.class))
                    )
                    .node(Node.required("b", Integer.class))
                    .node(Node.anonymous(Integer.class));
        }
        /*
        a = 0 // Default value means it'll always override when saved
        foo { // Section
            bar = false // Boolean
            baz = 1.0 // Double
        }
        b = 6 // Integer
        anything = 36 // Anonymous class, can be any name and any value, must conform to the type.
        */

        Impl(Settings settings) {
            this.settings = settings;
        }

        Impl() {

        }


        @Override
        public <T> Config node(Node<T> node) {
            this.nodes.put(node.name(), node);
            return this;
        }

        @Override
        public Config section(Section section) {
            this.sections.put(section.name(), section);
            return this;
        }

        @Override
        public Collection<Node<?>> nodes() {
            return nodes.values();
        }

        @Override
        public Collection<Section> sections() {
            return sections.values();
        }
    }

}
