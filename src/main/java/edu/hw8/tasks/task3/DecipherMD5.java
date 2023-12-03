package edu.hw8.tasks.task3;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@SuppressWarnings("checkstyle:NestedForDepth")
public final class DecipherMD5 {
    private static final char[] ALL_CHARACTERS =
        "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static final int MAX_PASSWORD_LENGTH = 4;

    private DecipherMD5() {
    }

    @SuppressWarnings("checkstyle:MagicNumber") public static String nextPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashtext = new StringBuilder(no.toString(16));
            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }
            return hashtext.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> slowSolution(Map<String, String> database) {
        HashMap<String, String> ans = new HashMap<>();
        for (Map.Entry<String, String> entry : database.entrySet()) {
            boolean isSuggested = false;
            for (int passwordLength = 1; passwordLength <= MAX_PASSWORD_LENGTH; passwordLength++) {
                for (long passwordInt = 0; passwordInt < Math.pow(ALL_CHARACTERS.length, passwordLength);
                     passwordInt++) {
                    StringBuilder password = new StringBuilder();
                    long toDecode = passwordInt;
                    for (int j = 0; j < passwordLength; j++) {
                        password.append(ALL_CHARACTERS[(int) (toDecode % ALL_CHARACTERS.length)]);
                        toDecode /= ALL_CHARACTERS.length;
                    }
                    if (Objects.equals(entry.getKey(), nextPassword(password.toString()))) {
                        ans.put(entry.getValue(), password.toString());
                        isSuggested = true;
                        break;
                    }
                }
                if (isSuggested) {
                    break;
                }
            }
        }
        return ans;
    }

    public static Map<String, String> fastSolution(Map<String, String> database) {
        HashMap<String, String> ans = new HashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<String> keys = database.keySet().stream().toList();
        AtomicInteger index = new AtomicInteger(0);
        var tasks = Stream.generate(() -> CompletableFuture.runAsync(() -> {
                String decodedPassword = keys.get(index.getAndIncrement());
                String user = database.get(decodedPassword);
                boolean isSuggested = false;
                for (int passwordLength = 1; passwordLength <= MAX_PASSWORD_LENGTH; passwordLength++) {
                    for (long passwordInt = 0; passwordInt < Math.pow(ALL_CHARACTERS.length, passwordLength);
                         passwordInt++) {
                        StringBuilder password = new StringBuilder();
                        long toDecode = passwordInt;
                        for (int j = 0; j < passwordLength; j++) {
                            password.append(ALL_CHARACTERS[(int) (toDecode % ALL_CHARACTERS.length)]);
                            toDecode /= ALL_CHARACTERS.length;
                        }
                        if (nextPassword(password.toString()).equals(decodedPassword)) {
                                ans.put(user, password.toString());
                                isSuggested = true;
                                break;
                            }
                    }
                    if (isSuggested) {
                        break;
                    }
                }
            }, executorService)).limit(Math.min(database.size(), Runtime.getRuntime().availableProcessors()))
            .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(tasks).join();
        executorService.shutdown();
        return ans;
    }
}
