package me.combimagnetron.lagoon.communication;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@SuppressWarnings({"rawtypes", "unchecked"})
public class MessageRegistry {
    private static final ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor();
    //private static final ConcurrentHashMap<Integer, Class<? extends InstanceBoundMessage>> INSTANCE_BOUND_MESSAGE_MAP = new ConcurrentHashMap<>();
    //private static final ConcurrentHashMap<Integer, Class<? extends ServiceBoundMessage>> SERVICE_BOUND_MESSAGE_MAP = new ConcurrentHashMap<>();
    //private static final ConcurrentHashMap<Integer, Class<? extends ProxyBoundMessage>> PROXY_BOUND_MESSAGE_MAP = new ConcurrentHashMap<>();
    //private static final ConcurrentHashMap<Class<? extends Message>, List<MessageListener>> LISTENER_MAP = new ConcurrentHashMap<>();

    /*static {
        INSTANCE_BOUND_MESSAGE_MAP.put(0, InstanceBoundKeepAliveMessage.class);
        INSTANCE_BOUND_MESSAGE_MAP.put(1, InstanceBoundCreateGameLevelMessage.class);
        INSTANCE_BOUND_MESSAGE_MAP.put(2, ServiceBoundRequestInstanceBlueprintsMessage.Response.class);
        PROXY_BOUND_MESSAGE_MAP.put(0, ProxyBoundMovePlayerMessage.class);
        PROXY_BOUND_MESSAGE_MAP.put(1, ProxyBoundMoveGameLevelMessage.class);
        PROXY_BOUND_MESSAGE_MAP.put(2, InstanceBoundKeepAliveMessage.Response.class);
        SERVICE_BOUND_MESSAGE_MAP.put(0, ServiceBoundRegisterInstanceMessage.class);
        SERVICE_BOUND_MESSAGE_MAP.put(1, ServiceBoundRequestServiceStatusChangeMessage.class);
        SERVICE_BOUND_MESSAGE_MAP.put(2, ServiceBoundRequestInstanceBlueprintsMessage.class);
    }*/

    public static void init() {
        //registerListeners(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().getPackageName());
    }

    /*public static Message read(byte[] channel, byte[] bytes) {
        try {
            return EXECUTOR.submit(() -> {
                final String channelName = new String(channel);
                ByteArrayDataInput input = ByteStreams.newDataInput(bytes);
                int messageId = input.readInt();
                int type = input.readInt();
                Message message = switch (type) {
                    default -> construct(INSTANCE_BOUND_MESSAGE_MAP.get(messageId), bytes);
                    case 0x01 -> construct(PROXY_BOUND_MESSAGE_MAP.get(messageId), bytes);
                    case 0x02 -> construct(SERVICE_BOUND_MESSAGE_MAP.get(messageId), bytes);
                };
                LISTENER_MAP.putIfAbsent(message.getClass(), new ArrayList<>());
                LISTENER_MAP.get(message.getClass()).forEach(listener -> {
                    MessageHandler annotation = listener.getClass().getAnnotation(MessageHandler.class);
                    if (!annotation.channel().equals(channelName)) {
                        return;
                    }
                    listener.receive(message);
                });
                return message;
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static Message construct(Class<?> clazz, byte[] bytes) {
        try {
            return (Message) clazz.getConstructor(byte[].class).newInstance((Object) bytes);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void registerListeners(String string) {
        Reflections reflections = new Reflections(string);
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(MessageHandler.class);
        classSet.forEach(clazz -> {
            Object object;
            try {
                object = clazz.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            MessageHandler annotation = clazz.getAnnotation(MessageHandler.class);
            Class<? extends Message> packetType = annotation.filter();
            MessageListener<?> listener = (MessageListener<?>) object;
            LISTENER_MAP.putIfAbsent(packetType, new ArrayList<>());
            if (LISTENER_MAP.get(packetType) != null)
                LISTENER_MAP.get(packetType).add(listener);
        });
    }

    private static String getCallerClass() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        int size = stElements.length;
        for (int i = 1; i < size; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(MessageRegistry.class.getName())&& ste.getClassName().indexOf("java.lang.Thread")!=0) {
                return ste.getClassName();
            }
        }
        return null;
    }

    public static ConcurrentHashMap<Class<? extends Message>, List<MessageListener>> listeners() {
        return LISTENER_MAP;
    }
*/

}
