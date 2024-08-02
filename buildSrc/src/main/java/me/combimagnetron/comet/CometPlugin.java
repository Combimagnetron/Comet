package me.combimagnetron.comet;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import me.combimagnetron.comet.CopyFileTask;

import java.util.List;

public class CometPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        CometExtension cometExtension = project.getExtensions().create("comet", CometExtension.class);
        project.getTasks().register("copyFileToSubprojects", CopyFileTask.class, task -> {
            task.setGroup("comet");
            task.setDescription("Copies a specified file to all subprojects");
        });
        project.afterEvaluate((action) -> {
            if (project.getName().contains("service") || project.getName().contains("pilot")) {
                ConfigWriter.of(cometExtension, project);
            }
            project.getTasksByName("build", false).forEach(task -> task.dependsOn("copyFileToSubprojects"));
        });
    }

    public static class CometExtension {
        private final ServiceExtension service = new ServiceExtension();
        private final DeploymentExtension deployment = new DeploymentExtension();
        private final ComponentExtension component = new ComponentExtension();

        public ServiceExtension getService() {
            return service;
        }

        public DeploymentExtension getDeployment() {
            return deployment;
        }

        public ComponentExtension getComponent() {
            return component;
        }

        public void service(Action<? super ServiceExtension> action) {
            action.execute(service);
        }

        public void deployment(Action<? super DeploymentExtension> action) {
            action.execute(deployment);
        }

        public void component(Action<? super ComponentExtension> action) {
            action.execute(component);
        }

    }

    public static class ServiceExtension {
        private String identifier = "comet:" + CometPlugin.class.getPackage().getName();
        private String version = "undefined";

        public String getIdentifier() {
            return identifier;
        }

        public String getVersion() {
            return version;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public void setVersion(String version) {
            this.version = version;
        }

    }

    public static class DeploymentExtension {
        private int minInstanceCount = 1;
        private int maxInstanceCount = 2;
        private int playerInstanceThreshold = -1;
        private String image = "self";

        public int getMinInstanceCount() {
            return minInstanceCount;
        }

        public int getMaxInstanceCount() {
            return maxInstanceCount;
        }

        public int getPlayerInstanceThreshold() {
            return playerInstanceThreshold;
        }

        public String getImage() {
            return image;
        }

        public void setMinInstanceCount(int minInstanceCount) {
            this.minInstanceCount = minInstanceCount;
        }

        public void setMaxInstanceCount(int maxInstanceCount) {
            this.maxInstanceCount = maxInstanceCount;
        }

        public void setPlayerInstanceThreshold(int playerInstanceThreshold) {
            this.playerInstanceThreshold = playerInstanceThreshold;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }

    public static class ComponentExtension {
        private final MonitorExtension monitor = new MonitorExtension();
        private final InterceptExtension intercept = new InterceptExtension();


        public MonitorExtension getMonitor() {
            return monitor;
        }

        public InterceptExtension getIntercept() {
            return intercept;
        }

        public void monitor(Action<? super MonitorExtension> action) {
            action.execute(monitor);
        }

        public void intercept(Action<? super InterceptExtension> action) {
            action.execute(intercept);
        }

    }

    public static class MonitorExtension {
        private List<String> type = List.of();

        public List<String> getType() {
            return type;
        }

        public void setType(List<String> type) {
            this.type = type;
        }

    }

    public static class InterceptExtension {
        private List<String> type = List.of();

        public List<String> getType() {
            return type;
        }

        public void setType(List<String> type) {
            this.type = type;
        }
    }
}