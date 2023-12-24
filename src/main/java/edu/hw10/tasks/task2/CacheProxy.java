package edu.hw10.tasks.task2;

import java.lang.reflect.Proxy;

public final class CacheProxy {
    private CacheProxy() {
    }

    public static <T> T create(T object, Class<? extends T> objectClass) {
        CacheHandler handler = new CacheHandler(object);
        return (T) Proxy.newProxyInstance(objectClass.getClassLoader(), objectClass.getInterfaces(), handler);
    }
}
