package edu.hw11.tasks.task1;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 1")
public class HelloByteBuddyTest {
    @Test
    void testHelloByteBuddy()
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> helloClass = new ByteBuddy()
            .subclass(Object.class)
            .method(named("toString"))
            .intercept(FixedValue.value("Hello, ByteBuddy"))
            .make()
            .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();
        assertThat(helloClass.getDeclaredConstructor().newInstance().toString()).isEqualTo("Hello, ByteBuddy");
    }
}
