package me.combimagnetron.comet.config.reader;

import me.combimagnetron.comet.config.element.Node;
import me.combimagnetron.comet.config.element.Section;

import java.util.TreeMap;

public final class Reader {
    private final TreeMap<String, Section> sections;
    private final TreeMap<String, Node<?>> nodes;

    public Reader(TreeMap<String, Section> sections, TreeMap<String, Node<?>> nodes) {
        this.sections = sections;
        this.nodes = nodes;
    }

    public <T> Node<T> node(String name) {
        return (Node<T>) nodes.get(name);
    }

    public Section section(String name) {
        return sections.get(name);
    }

}
