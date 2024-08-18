package me.combimagnetron.comet.task;

import me.combimagnetron.comet.satellite.compiler.SatelliteClass;
import me.combimagnetron.comet.satellite.compiler.SatelliteCompiler;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class CompileSatelliteTask extends DefaultTask {
    final Project project = getProject().getRootProject();

    public CompileSatelliteTask() {
        setGroup("comet");
        setDescription("Compiles the satellite files.");
    }
    @TaskAction
    public void compileSatellite() {
        SatelliteCompiler compiler = new SatelliteCompiler();
        Path path = project.getRootProject().file("sats").toPath();
        try (Stream<Path> stream = Files.list(path)) {
            stream.forEach(file -> {
                if (!file.toString().endsWith(".sat")) return;
                SatelliteClass clazz = compiler.transform(file);
                compiler.compile(clazz, project.getRootProject().file("api/src/main/java/me/combimagnetron/generated").toPath());
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
