package me.combimagnetron.lagoon.condition;

import me.combimagnetron.lagoon.Comet;

import java.lang.reflect.Method;
import java.util.UUID;

public interface Condition {

    Result eval(Supplier<?> value);

    record Result(boolean value) {
        public static Result of(boolean value, String reason) {
            return new Result(value);
        }
    }

    default void a() {
        Condition condition = Condition.of("user.uuid == 7320ba28-1d76-43aa-a72e-9649af484f1d");
        Result result = condition.eval(Supplier.of(Comet.userByUniqueId(UUID.fromString("7320ba28-1d76-43aa-a72e-9649af484f1d"))));
    }

    static <T> Condition of(String condition) {
        try {
            return new SimpleCondition(condition);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    class SimpleCondition implements Condition {
        private final String condition;
        private final Operator operator;
        private Method method;

        SimpleCondition(String condition) throws ReflectiveOperationException {
            this.operator = Operator.find(condition);
            this.condition = adaptString(condition);
        }

        String adaptString(String string) {
            if (string.contains("().")) {
                return string;
            }
            String[] evalSplit = string.split(operator.operator() + " ");
            String parts = evalSplit[0].replaceAll("\\.", "().").replace(" ", "()");;
            return String.join("", parts) + " " + operator.operator() + " " + evalSplit[1];
        }

        Method findMethod(Supplier<?> value) throws NoSuchMethodException {
            String[] path = condition.split(operator.operatorWithSpaces())[0].split("\\(\\)\\.");
            String variableName = value.value().getClass().getName().toLowerCase();
            if (!path[0].equals(variableName)) {
                return null;
            }
            Method lastMethod = null;
            for (String s : path) {
                if (s.equals(variableName)) {
                    continue;
                }
                if (lastMethod == null) {
                    lastMethod = value.value().getClass().getDeclaredMethod(s);
                }
                lastMethod = lastMethod.getClass().getDeclaredMethod(s);
            }
            return lastMethod;
        }

        @Override
        public Condition.Result eval(Supplier<?> value) {
            try {
                this.method = findMethod(value);
                ConditionTypeAdapter<?> typeAdapter = ConditionTypeAdapter.find(this.method.getReturnType());
                Object object = typeAdapter.get().apply(condition.split(operator.operatorWithSpaces())[1]);
                return operator.eval(this.method.invoke(value.value()), object);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
