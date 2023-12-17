package edu.hw10.tasks.task1;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomObjectGenerator {
    private final SecureRandom random = new SecureRandom();
    private static final double DEFAULT_MIN_VALUE = 1;
    private static final double DEFAULT_MAX_VALUE = 100;
    private static final int ENGLISH_ALPHABET_LENGTH = 26;

    public RandomObjectGenerator() {
    }

    @SuppressWarnings("checkstyle:ReturnCount")
    private Object parsePrimitive(Class<?> type, Min min, Max max) {
        double minValue = min == null ? DEFAULT_MIN_VALUE : min.value();
        double maxValue = max == null ? DEFAULT_MAX_VALUE : max.value();
//        System.out.println(minValue + " " + maxValue);
        if (type.equals(int.class) || type.equals(Integer.class)) {
            return random.nextInt((int) minValue, (int) maxValue);
        } else if (type.equals(double.class) || type.equals(Double.class)) {
            return random.nextDouble(minValue, maxValue);
        } else if (type.equals(String.class)) {
            int length = random.nextInt((int) minValue, (int) maxValue);
            StringBuilder ans = new StringBuilder(length);
            for (int j = 0; j < length; j++) {
                ans.append((char) (random.nextInt(ENGLISH_ALPHABET_LENGTH) + 'a'));
            }
            return ans.toString();
        } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
            return random.nextBoolean();
        }
        return null;
    }

    private Object[] generateParameters(Class<?>[] parameters, NotNull notNull, Min min, Max max) {
        return Stream.iterate(0, i -> i + 1).limit(parameters.length).map(i -> {
            Object primitive = parsePrimitive(parameters[i], min, max);
            if (primitive != null) {
                return primitive;
            }
            return random.nextBoolean() && notNull == null ? null : nextObject(parameters[i]);
        }).toArray();
    }

    public <T> T nextObject(Class<T> object, String fabricMethodName) {
        ArrayList<Method> methods = Arrays.stream(object.getMethods())
            .filter(x -> x.getName().equals(fabricMethodName)
                && Modifier.isStatic(x.getModifiers())
                && Modifier.isPublic(x.getModifiers())
                && x.getReturnType().equals(object))
            .collect(Collectors.toCollection(ArrayList::new));
        if (methods.isEmpty()) {
            throw new IllegalArgumentException(object + " hasn't any fabric method with name " + fabricMethodName);
        }
        Method fabricMethod = methods.get(random.nextInt(methods.size()));
        NotNull notNull = fabricMethod.getAnnotation(NotNull.class);
        Min min = fabricMethod.getAnnotation(Min.class);
        Max max = fabricMethod.getAnnotation(Max.class);
        try {
            return (T) fabricMethod.invoke(
                null,
                generateParameters(fabricMethod.getParameterTypes(), notNull, min, max)
            );
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T nextObject(Class<T> object) {
        Object primitive = parsePrimitive(object, null, null);
        if (primitive != null) {
            return (T) primitive;
        }
        Constructor<?>[] constructors = object.getConstructors();
        if (constructors.length == 0) {
            throw new IllegalArgumentException(object + " hasn't any constructor");
        }
        Constructor<?> constructor = constructors[random.nextInt(constructors.length)];
        NotNull notNull = constructor.getAnnotation(NotNull.class);
        Min min = constructor.getAnnotation(Min.class);
        Max max = constructor.getAnnotation(Max.class);
        try {
            return (T) constructor.newInstance(generateParameters(constructor.getParameterTypes(), notNull, min, max));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
