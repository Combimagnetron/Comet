package me.combimagnetron.comet.satellite;

import me.combimagnetron.comet.CometPlugin;
import me.combimagnetron.comet.config.Config;
import me.combimagnetron.comet.config.element.Node;
import me.combimagnetron.comet.satellite.compiler.SatelliteField;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class SatelliteIdRegistry {
    private final LinkedHashMap<Integer, SatelliteField.MessageField> registry = new LinkedHashMap<>();
    private final Path path;
    private final List<Path> paths = new ArrayList<>();
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
        System.out.println("Writing registry");
        for (var entry : registry.entrySet()) {
            Collection<Node<?>> nodes = config.nodes();
            if (nodes.stream().anyMatch(node -> anyMatch(node, entry.getValue()))) continue;
            config.node(Node.required(i++ + "", entry.getValue().name()));
        }
        writeJavaClass(CometPlugin.PROJECT.getRootProject().file("api/src/main/java/me/combimagnetron/generated/SatelliteRegistry.java").toPath());
        config.save(registryFile.toPath());
    }

    private void writeJavaClass(Path path) {
        StringBuilder builder = new StringBuilder();
        builder.append("package me.combimagnetron.generated;\n\n");
        builder.append("import me.combimagnetron.comet.communication.Message;\n");
        for (var entry : paths) {
            if (!entry.getFileName().toString().contains(".sat")) continue;
            builder.append("import me.combimagnetron.generated.").append(entry.getFileName().toString().toLowerCase().replaceAll(".sat", "")).append(".*;\n");
        }
        builder.append("import java.util.HashMap;\n\n");
        builder.append("public class SatelliteRegistry {\n");
        builder.append("    public static HashMap<Integer, Class<? extends Message>> registry = new HashMap<>();\n");
        builder.append("    static {\n");
        System.out.println(config.nodes().size());
        for (var entry : config.nodes()) {
            System.out.println(entry);
            builder.append("        registry.put(").append(entry.name()).append(", ").append(entry.value()).append(".class);\n");
        }

        builder.append("    }\n");
        builder.append("}\n");
        try {
            Files.createDirectories(path.getParent());
            Files.writeString(path, builder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(builder);
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


    public void paths(List<Path> paths) {
        this.paths.addAll(paths);
    }
}
