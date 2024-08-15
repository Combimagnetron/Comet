package me.combimagnetron.comet.satellite;

import me.combimagnetron.comet.util.Values;

import java.util.concurrent.atomic.AtomicInteger;

public interface ProtocolDirection {
        ProtocolDirection SERVER_BOUND = Impl.of("server");
        ProtocolDirection PROXY_BOUND = Impl.of("proxy");
        ProtocolDirection SERVICE_BOUND = Impl.of("service");
        Values<ProtocolDirection> VALUES = Values.of(SERVER_BOUND, PROXY_BOUND, SERVICE_BOUND);

        String direction();

        int next();

        static ProtocolDirection find(String string) {
            return VALUES.values().stream().filter(protocolDirection -> string.split("-")[0].equals(protocolDirection.direction())).findAny().orElseThrow();
        }

        final class Impl implements ProtocolDirection {
            private final AtomicInteger id = new AtomicInteger(0);
            private final String direction;

            public Impl(String direction) {
                this.direction = direction;
            }

            public static Impl of(String direction) {
                return new Impl(direction);
            }

            @Override
            public int next() {
                return id.getAndIncrement();
            }

            @Override
            public String direction() {
                return direction;
            }
        }
    }