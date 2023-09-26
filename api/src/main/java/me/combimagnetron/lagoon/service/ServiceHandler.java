package me.combimagnetron.lagoon.service;

import me.combimagnetron.lagoon.data.Identifier;

public interface ServiceHandler {

    Service service(Identifier identifier);

    Service deploy(Deployment deployment, Identifier identifier);

}
