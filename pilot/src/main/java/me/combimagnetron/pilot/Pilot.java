package me.combimagnetron.pilot;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1NodeList;
import io.kubernetes.client.util.Config;
import me.combimagnetron.lagoon.Lagoon;
import me.combimagnetron.lagoon.communication.MessageClient;
import me.combimagnetron.lagoon.communication.message.MessageChannel;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.operation.Operations;

import java.io.IOException;
import java.util.UUID;

public class Pilot {
    private final MessageClient client;
    private final MessageChannel channel;
    private final ApiClient k8sApiClient;
    private final CoreV1Api k8sApi;

    public Pilot(MessageClient client) throws IOException, ApiException {
        this.client = client;
        this.channel = Operations.async(client.channel(Identifier.of("service", "pilot")));
        this.k8sApiClient = Config.defaultClient();
        this.k8sApi = new CoreV1Api(k8sApiClient);
        V1NodeList nodeList = k8sApi.listNode(null, null, null, null, null, null, null, null, 10, false);
        nodeList.getItems().forEach(node -> {
            Instance instance = Lagoon.instance(UUID.fromString(node.getMetadata().getName()));
        });
        V1Deployment deployment = new V1Deployment();
    }
}
