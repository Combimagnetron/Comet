package me.combimagnetron.lagoon.data.database;

public interface Credentials {

    static Credentials redis(String username, String password, int port, String host) {
        return new RedisCredentials(username, password, port, host);
    }


    record RedisCredentials(String username, String password, int port, String host) implements Credentials {

    }


}
