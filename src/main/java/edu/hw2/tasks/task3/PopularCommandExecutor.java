package edu.hw2.tasks.task3;

public final class PopularCommandExecutor {
    private final ConnectionManager manager;
    private final int maxAttempts;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    private void tryExecute(String command) throws ConnectionException {
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            try (Connection connection = manager.getConnection()) {
                connection.execute(command);
                break;
            } catch (Exception e) {
                if (attempt + 1 == maxAttempts) {
                    throw new ConnectionException("exceeded the number of attempts", e);
                }
            }
        }
    }
}
