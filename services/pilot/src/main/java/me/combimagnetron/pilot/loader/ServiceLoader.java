package me.combimagnetron.pilot.loader;

import me.combimagnetron.comet.service.AutoService;
import me.combimagnetron.comet.service.Service;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ServiceLoader {
    //private static final Collection<TypeVariablePair> TYPE_VARIABLE_PAIRS = List.of(TypeVariablePair.pair(MessageClient.class, CometBas.messageClient()));

    public ServiceLoader() {

    }

    private Service load(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (!clazz.isAssignableFrom(Service.class) && !clazz.isAnnotationPresent(AutoService.class)) {
            return null;
        }
        AutoService service = clazz.getAnnotation(AutoService.class);
        Object[] objects = TypeVariablePair.get(service.parameters());
        Service instance = (Service) clazz.getDeclaredConstructor(service.parameters()).newInstance(objects);
        return instance;
    }

    record TypeVariablePair(Class<?> type, Object variable) {
        public static TypeVariablePair pair(Class<?> type, Object variable) {
            return new TypeVariablePair(type, variable);
        }

        public static Object[] get(Class<?>[] classes) {
            Collection<Object> objects = new HashSet<>();
            Arrays.stream(classes).forEach(clazz -> {
                //objects.add(TYPE_VARIABLE_PAIRS.stream().filter(pair -> pair.type().equals(clazz)).findFirst().orElseGet(null).variable());
            });
            return objects.toArray();
        }

    }


}
