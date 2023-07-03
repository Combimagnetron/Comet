package me.combimagnetron.pilot;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1NodeList;
import io.kubernetes.client.util.Config;
import me.combimagnetron.lagoon.Lagoon;
import me.combimagnetron.lagoon.communication.MessageClient;
import me.combimagnetron.lagoon.communication.MessageRegistry;
import me.combimagnetron.lagoon.communication.message.MessageChannel;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.operation.Operations;
import me.combimagnetron.lagoon.service.Action;
import me.combimagnetron.lagoon.service.AutoService;
import me.combimagnetron.lagoon.service.Service;
import me.combimagnetron.lagoon.service.config.StringStringParameter;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@AutoService(name = "pilot")
public class Pilot implements Service<StringStringParameter> {
    private static final GitHub GIT_HUB;
    private final MessageClient client;
    private final MessageChannel channel;
    private final ApiClient k8sApiClient;
    private final CoreV1Api k8sApi;

    static {
        try {
            GIT_HUB = GitHubBuilder.fromEnvironment().build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Pilot(MessageClient client) throws IOException, ApiException {
        MessageRegistry.init();
        this.client = client;
        this.channel = Operations.async(client.channel(Identifier.of("service", "pilot")));
        this.k8sApiClient = Config.defaultClient();
        this.k8sApi = new CoreV1Api(k8sApiClient);
        V1Deployment deployment = new V1Deployment();
    }

    @Override
    public Identifier identifier() {
        return Identifier.of("service", "pilot");
    }

    @Override
    public Operation<Void> restart() {
        return Operation.simple(() -> {});
    }

    @Override
    public Set<Class<? extends Action<?, ?>>> actions() {
        return null;
    }

    @Override
    public StringStringParameter config() {
        return new StringStringParameter();
    }

    public static GitHub gitHub() {
        return GIT_HUB;
    }

}
