package me.combimagnetron.comet.config.reader;

import me.combimagnetron.comet.config.element.Node;
import me.combimagnetron.comet.config.element.Section;

import javax.ws.rs.core.Link;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public final class Reader {
    private final Map<String, Section> sections;
    private final Map<String, Node<?>> nodes;

    public Reader(LinkedHashMap<String, Section> sections, LinkedHashMap<String, Node<?>> nodes) {
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
