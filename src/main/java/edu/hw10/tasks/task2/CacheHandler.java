package edu.hw10.tasks.task2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CacheHandler implements InvocationHandler {
    private final HashMap<Map.Entry<Method, Object[]>, Object> cachedValues = new HashMap<>();
    private final Object object;
    private final Path objectDirectory;

    public CacheHandler(Object object) {
        this.object = object;
        this.objectDirectory =
            Path.of(System.getProperty("user.dir")).resolve(".cached").resolve(object.getClass().getName());
        if (Files.exists(objectDirectory)) {
            for (Method method : object.getClass().getMethods()) {
                Path directory = objectDirectory.resolve(method.getName());
                if (!Files.exists(directory)) {
                    continue;
                }
                Object result;
                Object[] arguments;
                int argumentsCount;
                for (File file : Objects.requireNonNull(directory.toFile().listFiles())) {
                    try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
                        result = objectInputStream.readObject();
                        argumentsCount = objectInputStream.readInt();
                        arguments = new Object[argumentsCount];
                        for (int i = 0; i < argumentsCount; i++) {
                            arguments[i] = objectInputStream.readObject();
                        }
                        cachedValues.put(Map.entry(method, arguments), result);
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Cache cache = method.getAnnotation(Cache.class);
        if (cache == null) {
            return method.invoke(object, args);
        }
        Object result;
        if (cache.persist()) {
            String key = String.join(";", Arrays.stream(args).map(Object::toString).toArray(String[]::new));
            Path directory = objectDirectory.resolve(method.getName());
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            Path file = directory.resolve(key);
            if (!Files.exists(file)) {
                Files.createFile(file);
                result = method.invoke(object, args);
                try (
                    ObjectOutputStream objectOutputStream =
                        new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
                    objectOutputStream.writeObject(result);
                    objectOutputStream.writeInt(args.length);
                    for (Object arg : args) {
                        objectOutputStream.writeObject(arg);
                    }
                }
            } else {
                try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file.toFile()))) {
                    result = objectInputStream.readObject();
                }
            }
        } else {
            var key = Map.entry(method, args);
            if (cachedValues.containsKey(key)) {
                result = cachedValues.get(key);
            } else {
                result = method.invoke(object, args);
                cachedValues.put(key, result);
            }
        }
        return result;
    }
}
