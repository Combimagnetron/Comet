package me.combimagnetron.pilot;

import com.marcnuri.yakc.KubernetesClient;
import com.marcnuri.yakc.api.NotFoundException;
import com.marcnuri.yakc.api.WatchEvent;
import com.marcnuri.yakc.api.core.v1.CoreV1Api;
import com.marcnuri.yakc.model.io.k8s.api.core.v1.Container;
import com.marcnuri.yakc.model.io.k8s.api.core.v1.Namespace;
import com.marcnuri.yakc.model.io.k8s.api.core.v1.Pod;
import com.marcnuri.yakc.model.io.k8s.api.core.v1.PodSpec;
import com.marcnuri.yakc.model.io.k8s.apimachinery.pkg.apis.meta.v1.ObjectMeta;
import me.combimagnetron.comet.communication.MessageClient;
import me.combimagnetron.comet.communication.message.MessageChannel;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.service.AutoService;
import me.combimagnetron.comet.service.Deployment;
import me.combimagnetron.comet.service.Service;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;

@AutoService(name = "pilot", parameters = MessageClient.class)
public class Pilot implements Service {
    private static final GitHub GIT_HUB;
    private final MessageChannel channel;
    private final CoreV1Api api;

    static Pilot instance;

    public static Pilot pilot() {
        return instance;
    }

    static {
        try {
            GIT_HUB = GitHubBuilder.fromEnvironment().build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Pilot(MessageClient messageClient) throws IOException {
        this.channel = messageClient.channel(Identifier.of("service", "pilot"));
        try (KubernetesClient kc = new KubernetesClient()) {
            this.api = kc.create(CoreV1Api.class);
        }
        try {
            api.readNamespace("services").get();
        } catch (NotFoundException ex) {
            api.createNamespace(Namespace.builder()
                    .metadata(ObjectMeta.builder().name("services").build())
                    .build()
            ).get();
        }
    }

    public void deploy(Deployment deployment) {
        try {
            api.createNamespacedPod("services",
                    Pod.builder()
                            .spec(PodSpec.builder()
                                    .addToContainers(Container.builder().image(deployment.image()).name(deployment.name()).build()).build()
                            )
                            .metadata(ObjectMeta.builder()
                                    .name(deployment.name()).namespace("services").build())
                            .build()).get();
            api.listNamespacedPod("services").watch()
                    .filter(watchEvent -> watchEvent.getObject().getMetadata().getName().equals(deployment.name()))
                    .takeUntil(watchEvent -> watchEvent.getType() == WatchEvent.Type.ADDED)
                    .subscribe();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Identifier identifier() {
        return Identifier.of("service", "pilot");
    }

    @Override
    public void stop() {

    }

    @Override
    public void start() {

    }

    public static GitHub gitHub() {
        return GIT_HUB;
    }

}
