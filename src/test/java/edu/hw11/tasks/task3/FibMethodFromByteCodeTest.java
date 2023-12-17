package edu.hw11.tasks.task3;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 3")
public class FibMethodFromByteCodeTest {
    private final Method fibonacciMethod;

    public FibMethodFromByteCodeTest()
        throws NoSuchMethodException {
        fibonacciMethod = new ByteBuddy()
            .subclass(Object.class)
            .defineMethod("fib", long.class, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC)
            .withParameters(int.class)
            .intercept(new Implementation.Simple(new FibFuncImpl()))
            .make()
            .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded()
            .getMethod("fib", int.class);
        System.out.println(fibonacciMethod);
    }

    public static Stream<Arguments> testFibMethodFromByteCode() {
        return Stream.of(
            Arguments.of(5, 5L),
            Arguments.of(10, 55L),
            Arguments.of(15, 610L),
            Arguments.of(20, 6765L),
            Arguments.of(25, 75025L),
            Arguments.of(30, 832040L),
            Arguments.of(35, 9227465L),
            Arguments.of(40, 102334155L),
            Arguments.of(45, 1134903170L),
            Arguments.of(50, 12586269025L)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testFibMethodFromByteCode(int input, long output) throws InvocationTargetException, IllegalAccessException {
        assertThat(fibonacciMethod.invoke(null, input)).isEqualTo(output);
    }

    public static class FibFuncImpl implements ByteCodeAppender {
        @Override
        public Size apply(
            MethodVisitor methodVisitor,
            Implementation.Context context,
            MethodDescription methodDescription
        ) {
            methodVisitor.visitCode();

            methodVisitor.visitVarInsn(Opcodes.ILOAD, 0);
            methodVisitor.visitInsn(Opcodes.ICONST_2);

            Label ifLabel = new Label();
            methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGT, ifLabel);
            methodVisitor.visitInsn(Opcodes.LCONST_1);
            methodVisitor.visitInsn(Opcodes.LRETURN);

            methodVisitor.visitLabel(ifLabel);
            methodVisitor.visitFrame(Opcodes.F_SAME, 1, new Object[]{Opcodes.INTEGER}, 0, null);
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 0);
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitInsn(Opcodes.ISUB);
            methodVisitor.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                methodDescription.getInternalName(),
                methodDescription.getName(),
                methodDescription.getDescriptor(),
                false
            );
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 0);
            methodVisitor.visitInsn(Opcodes.ICONST_2);
            methodVisitor.visitInsn(Opcodes.ISUB);
            methodVisitor.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                methodDescription.getInternalName(),
                methodDescription.getName(),
                methodDescription.getDescriptor(),
                false
            );
            methodVisitor.visitInsn(Opcodes.LADD);
            methodVisitor.visitInsn(Opcodes.LRETURN);
            methodVisitor.visitEnd();

            return new Size(4, 1);
        }
    }
}
