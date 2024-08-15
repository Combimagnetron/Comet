package me.combimagnetron.comet.config;

import me.combimagnetron.comet.CometPlugin;
import me.combimagnetron.comet.config.element.Node;
import me.combimagnetron.comet.config.element.Section;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;

import java.io.File;
import java.nio.file.Path;

public class ConfigWriter {
    private final CometPlugin.CometExtension cometExtension;
    private final Project project;

    protected ConfigWriter(CometPlugin.CometExtension cometExtension, Project project) {
        this.cometExtension = cometExtension;
        this.project = project;
        write();
    }

    public static ConfigWriter of(CometPlugin.CometExtension cometExtension, Project project) {
        return new ConfigWriter(cometExtension, project);
    }

    private void write() {
        SourceSet mainSourceSet = project.getExtensions()
                .getByType(JavaPluginExtension.class)
                .getSourceSets()
                .getByName(SourceSet.MAIN_SOURCE_SET_NAME);
        File resourcesDir = mainSourceSet.getResources().getSrcDirs().iterator().next();
        Config config = Config.config();
        config.section(
                Section.required("service")
                        .node(Node.required("identifier", cometExtension.getService().getIdentifier()))
                        .node(Node.required("version", cometExtension.getService().getVersion()))
        ).section(
                Section.required("deployment")
                        .node(Node.required("min-instance-count", cometExtension.getDeployment().getMinInstanceCount()))
                        .node(Node.required("max-instance-count", cometExtension.getDeployment().getMaxInstanceCount()))
                        .node(Node.required("player-instance-threshold", cometExtension.getDeployment().getPlayerInstanceThreshold()))
                        .node(Node.required("image", cometExtension.getDeployment().getImage()))
        ).section(
                Section.required("component")
                        .node(Node.required("monitor", cometExtension.getComponent().getMonitor().getType()))
                        .node(Node.required("intercept", cometExtension.getComponent().getIntercept().getType()))
        );
        config.save(resourcesDir.toPath().resolve(Path.of("manifest.cmt")));
    }



}
