package me.combimagnetron.comet.satellite;

import me.combimagnetron.comet.config.Config;
import me.combimagnetron.comet.config.element.Node;
import me.combimagnetron.comet.satellite.compiler.SatelliteField;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashMap;

public class SatelliteIdRegistry {
    private final LinkedHashMap<Integer, SatelliteField.MessageField> registry = new LinkedHashMap<>();
    private final Path path;
    private int i = 0;
    private File registryFile;


    public SatelliteIdRegistry(Path path) {
        this.path = path;
        if (!path.toFile().isDirectory()) return;
        File[] files = path.toFile().listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (!file.isFile()) return;
            String name = file.getName();
            if (!name.equals("registry.json")) return;
            this.registryFile = file;
        }
    }

    public void save(SatelliteField.MessageField field) {
        registry.put(i++, field);
    }

    public void write() {
        Config config = Config.file(registryFile.toPath());
        i = adjust(config);
        for (var entry : registry.entrySet()) {
            Collection<Node<?>> nodes = config.nodes();
            if (nodes.stream().anyMatch(node -> {
                String message = (String) node.value();
                return message.equals(entry.getValue().name());
            })) return;
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
