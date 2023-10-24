package edu.hw2.tasks.task4;

public record CallingInfo(String className, String methodName) {
    public static CallingInfo callingInfo() {
        try {
            throw new GetStackTraceException();
        } catch (GetStackTraceException e) {
            final StackTraceElement[] stackTrace = e.getStackTrace();
            final int functionId = Math.min(stackTrace.length - 1, 2);
            return new CallingInfo(
                stackTrace[functionId].getClassName(),
                stackTrace[functionId].getMethodName()
            );
        }
    }
}
