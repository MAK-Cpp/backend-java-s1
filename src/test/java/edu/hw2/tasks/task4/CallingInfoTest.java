package edu.hw2.tasks.task4;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

public class CallingInfoTest {
    private static CallingInfo a() {
        return b();
    }

    private static CallingInfo b() {
        return c();
    }

    private static CallingInfo c() {
        return CallingInfo.callingInfo();
    }

    @ParameterizedTest
    @MethodSource("functions")
    void testCallingInfo(final CallingInfoFunction callingInfoFunction, final CallingInfo info) {
        assertThat(callingInfoFunction.execute()).isEqualTo(info);
    }

    private static Stream<Arguments> functions() {
        CallingInfoFunction functionA = CallingInfoTest::a;
        CallingInfoFunction functionB = CallingInfoTest::b;
        CallingInfoFunction functionC = CallingInfoTest::c;
        return Stream.of(
            Arguments.of(functionA, new CallingInfo("edu.hw2.tasks.task4.CallingInfoTest", "b")),
            Arguments.of(functionB, new CallingInfo("edu.hw2.tasks.task4.CallingInfoTest", "b")),
            Arguments.of(functionC, new CallingInfo("edu.hw2.tasks.task4.CallingInfoTest", "testCallingInfo"))
        );
    }
}
