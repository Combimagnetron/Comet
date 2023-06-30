package me.combimagnetron.lagoon.instance;

import me.combimagnetron.lagoon.communication.MessageListener;
import me.combimagnetron.lagoon.util.VersionCollection;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

public class InstanceBlueprint implements Serializable {
    private final Info info;

    public InstanceBlueprint(Info info) {
        this.info = info;
    }



    public record Info(String version, Date created, String author) implements Serializable {

    }

    public static VersionCollection<InstanceBlueprint> request() {

    }

    class ResponseMessageListener implements MessageListener<>

}
