package me.combimagnetron.comet.service;

import me.combimagnetron.comet.data.Identifier;

public interface ServiceHandler {

    Service service(Identifier identifier);

    Service deploy(Deployment deployment, Identifier identifier);

}
