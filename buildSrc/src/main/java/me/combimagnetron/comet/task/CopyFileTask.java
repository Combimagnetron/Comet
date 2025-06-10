package me.combimagnetron.comet.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CopyFileTask extends DefaultTask {

    @TaskAction
    public void copyFile() throws IOException {
        Project rootProject = getProject().getRootProject();
        String sourceFilePath = "logback.xml";
        File file = rootProject.file(sourceFilePath);
        String content = new String(Files.readAllBytes(file.toPath()));
        content = content.replaceAll("\\b" + "SECRET" + "\\b", (String) getProject().getProperties().get("logbackToken"));
        String finalContent = content;
        rootProject.subprojects(subproject -> {
            File copied = new File(subproject.getExtensions().getByType(JavaPluginExtension.class).getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME).getResources().getSrcDirs().iterator().next() + "/logback.xml");
            try {
                Files.write(copied.toPath(), finalContent.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        /*rootProject.subprojects(subproject -> {
            subproject.copy(copySpec -> {
                copySpec.from(temp);
                copySpec.into(subproject.getExtensions().getByType(JavaPluginExtension.class).getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME).getResources().getSrcDirs().iterator().next());
            });
        });*/
    }

}
