package edu.hw8.tasks.task3;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public final class DecipherMD5 {
    private static final char[] ALL_CHARACTERS =
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private static final int MAX_PASSWORD_LENGTH = 5;

    private DecipherMD5() {
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private static String nextPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashText = new StringBuilder(no.toString(16));
            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }
            return hashText.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String generatePassword(long hash, int length) {
        StringBuilder password = new StringBuilder();
        for (long j = 0, currentHash = hash; j < length; j++, currentHash /= ALL_CHARACTERS.length) {
            password.append(ALL_CHARACTERS[(int) (currentHash % ALL_CHARACTERS.length)]);
        }
        return password.toString();
    }

    private static Optional<Map.Entry<String, String>> hackOneUser(Map.Entry<String, String> entry) {
        for (int passwordLength = 1; passwordLength <= MAX_PASSWORD_LENGTH; passwordLength++) {
            for (long passwordHash = 0; passwordHash < Math.pow(ALL_CHARACTERS.length, passwordLength);
                 passwordHash++) {
                String password = generatePassword(passwordHash, passwordLength);
                if (Objects.equals(entry.getKey(), nextPassword(password))) {
                    return Optional.of(Map.entry(entry.getValue(), password));
                }
            }
        }
        return Optional.empty();
    }

    public static Map<String, String> slowSolution(Map<String, String> database) {
        HashMap<String, String> ans = new HashMap<>();
        for (Map.Entry<String, String> entry : database.entrySet()) {
            Optional<Map.Entry<String, String>> result = hackOneUser(entry);
            result.ifPresent(userAndPassword -> ans.put(userAndPassword.getKey(), userAndPassword.getValue()));
        }
        return ans;
    }

    public static Map<String, String> fastSolution(Map<String, String> database) {
        HashMap<String, String> ans = new HashMap<>();
        try (ExecutorService executorService = Executors.newFixedThreadPool(Math.min(
            database.size(),
            Runtime.getRuntime().availableProcessors()
        ))) {
            List<Map.Entry<String, String>> entries = database.entrySet().stream().toList();
            AtomicInteger index = new AtomicInteger(0);
            var tasks = Stream.generate(() -> CompletableFuture.runAsync(() -> {
                Optional<Map.Entry<String, String>> result = hackOneUser(entries.get(index.getAndIncrement()));
                result.ifPresent(userAndPassword -> ans.put(userAndPassword.getKey(), userAndPassword.getValue()));
            }, executorService)).limit(database.size()).toArray(CompletableFuture[]::new);
            CompletableFuture.allOf(tasks).join();
            executorService.shutdown();
        }
        return ans;
    }
}
