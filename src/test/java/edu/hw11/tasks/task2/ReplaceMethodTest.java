package edu.hw11.tasks.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 2")
public class ReplaceMethodTest {
    public static class Source {
        public String hello(String name) { return null; }
    }

    public static class Target {
        public static String hi(String name) {
            return "Hello " + name + "!";
        }
    }

    public static class ArithmeticUtils {
        public int sum(Integer a, Integer b) {
            return a + b;
        }
    }

    public static class NewArithmeticUtils {
        public static int mul(Integer a, Integer b) {
            return a * b;
        }
    }

    public static Stream<Arguments> testReplaceMethod() {
        return Stream.of(
            Arguments.of(
                Source.class,
                "hello",
                Target.class,
                new Object[] {"Maxim"},
                "Hello Maxim!"
            ),
            Arguments.of(
                ArithmeticUtils.class,
                "sum",
                NewArithmeticUtils.class,
                new Object[] {313, 23},
                7199
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testReplaceMethod(Class<?> origin, String methodName, Class<?> replacing, Object[] args, Object output)
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> replaced = ReplaceMethod.replace(origin, methodName, replacing);
        Class<?>[] argsClasses = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            argsClasses[i] = args[i].getClass();
        }
        Method method = replaced.getMethod(methodName, argsClasses);
        assertThat(method.invoke(replaced.getDeclaredConstructor().newInstance(), args)).isEqualTo(output);
    }
}
