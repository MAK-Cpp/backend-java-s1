package edu.hw11.tasks.task2;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import static net.bytebuddy.matcher.ElementMatchers.named;

public final class ReplaceMethod {
    private ReplaceMethod() {
    }

    public static Class<?> replace(Class<?> originClass, String methodName, Class<?> replacedClass) {
        return new ByteBuddy()
            .subclass(originClass)
            .method(named(methodName))
            .intercept(MethodDelegation.to(replacedClass))
            .make()
            .load(originClass.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();
    }
}
