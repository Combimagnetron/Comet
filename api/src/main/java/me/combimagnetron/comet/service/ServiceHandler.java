package me.combimagnetron.comet.service;

import me.combimagnetron.comet.data.Identifier;

import java.util.Collection;

public interface ServiceHandler {

    Service service(Identifier identifier);

    <T extends Service> Collection<T> service(Class<T> service);

    Service deploy(Deployment deployment, Identifier identifier);

}
