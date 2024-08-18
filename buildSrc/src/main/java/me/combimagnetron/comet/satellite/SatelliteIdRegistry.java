package me.combimagnetron.comet.satellite;

import me.combimagnetron.comet.config.Config;
import me.combimagnetron.comet.config.element.Node;
import me.combimagnetron.comet.satellite.compiler.SatelliteField;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Objects;

public class SatelliteIdRegistry {
    private final LinkedHashMap<Integer, SatelliteField.MessageField> registry = new LinkedHashMap<>();
    private final Path path;
    private int i = 0;
    private File registryFile;
    private Config config;


    public SatelliteIdRegistry(Path path) {
        this.path = path;
        File file = path.toFile();
        if (!file.isFile()) return;
        String name = file.getName();
        if (!name.equals("registry.cmt")) return;
        this.registryFile = file;
        this.config = Config.file(registryFile.toPath());
        i = adjust(Config.file(path));
    }

    public void save(SatelliteField.MessageField field) {
        int next;
        if (config.nodes().stream().anyMatch(node -> anyMatch(node, field))) {
            next = Integer.parseInt(config.nodes().stream().filter(node -> anyMatch(node, field)).findFirst().get().name());
        } else {
            next = i++;
        }   
        registry.put(next, field);
        field.id(next);
    }
    
    private boolean anyMatch(Node<?> node, SatelliteField.MessageField field) {
        String message = (String) node.value();
        return Objects.requireNonNull(message).equals(field.name());
    }

    public void write() {
        for (var entry : registry.entrySet()) {
            Collection<Node<?>> nodes = config.nodes();
            if (nodes.stream().anyMatch(node -> anyMatch(node, entry.getValue()))) return;
            config.node(Node.required(i++ + "", entry.getValue().name()));
        }
        config.save(registryFile.toPath());
    }

    private int adjust(Config config) {
        int i = 0;
        for (Node<?> node : config.nodes()) {
            int index = Integer.parseInt(node.name());
            if (index > i) {
                i = index;
            }
        }
        return i;
    }


}
