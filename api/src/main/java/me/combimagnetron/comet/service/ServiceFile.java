package me.combimagnetron.comet.service;

import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.config.Config;
import me.combimagnetron.comet.config.element.Section;
import me.combimagnetron.comet.config.reader.Reader;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.comet.service.broker.BrokerAgreement;
import me.combimagnetron.comet.util.Version;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public class ServiceFile {
    private final Identifier service;
    private final Version version;
    private final BrokerAgreement agreement;
    private final Deployment deployment;

    public static ServiceFile fromFile(Path path) {
        return new ServiceFile(path);
    }

    private ServiceFile(Path path) {
        Config file = Config.file(path);
        Reader reader = file.reader();

        Section info = reader.section("info");
        service = Identifier.split(info.find(("identifier")));
        version = Version.parse(info.find("version"));

        Section broker = reader.section("broker");
        agreement = BrokerAgreement.brokerAgreement();
        List<String> intercept = broker.find("intercept");
        agreement.intercept((BrokerAgreement.MessageReference.InterceptMessageReference<?>[]) intercept.stream().map(ServiceFile::find).map(BrokerAgreement.MessageReference.InterceptMessageReference::new).toArray());
        List<String> monitor = broker.find("monitor");
        agreement.monitor((BrokerAgreement.MessageReference.MonitorMessageReference<?>[]) monitor.stream().map(ServiceFile::find).map(BrokerAgreement.MessageReference.MonitorMessageReference::new).toArray());

        Section deploy = reader.section("deployment");
        deployment = Deployment.of(service.string(), deploy.find("image"), Integer.parseInt(deploy.find("min-instance-count")), Integer.parseInt(deploy.find("max-instance-count")), Integer.parseInt(deploy.find("player-instance-threshold")));
    }

    public ServiceInfo serviceInfo() {
        return ServiceInfo.serviceInfo(service, version, agreement, deployment);
    }

    public record ServiceInfo(Identifier identifier, Version version, BrokerAgreement brokerAgreement, Deployment deployment) {

        public static ServiceInfo serviceInfo(Identifier identifier, Version version, BrokerAgreement brokerAgreement, Deployment deployment) {
            return new ServiceInfo(identifier, version, brokerAgreement, deployment);
        }

        public byte[] serialize() {
            ByteBuffer byteBuffer = ByteBuffer.empty();
            byteBuffer.write(ByteBuffer.Adapter.IDENTIFIER, identifier);
            byteBuffer.write(ByteBuffer.Adapter.VERSION, version);
            byteBuffer.write(ByteBuffer.Adapter.BROKER_AGREEMENT, brokerAgreement);
            byteBuffer.write(ByteBuffer.Adapter.DEPLOYMENT, deployment);
            return byteBuffer.bytes();
        }

    }

    public static Class<? extends Message> find(String name) {
        try {
            return (Class<? extends Message>) Class.forName("me.combimagnetron.generated." + name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
