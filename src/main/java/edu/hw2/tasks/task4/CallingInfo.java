package edu.hw2.tasks.task4;

public record CallingInfo(String className, String methodName) {
    public static CallingInfo callingInfo() {
        try {
            throw new Exception("get StackTrace");
        } catch (Exception e) {
            int functionId = Math.min(e.getStackTrace().length - 1, 2);
            return new CallingInfo(
                e.getStackTrace()[functionId].getClassName(),
                e.getStackTrace()[functionId].getMethodName()
            );
        }
    }
}
