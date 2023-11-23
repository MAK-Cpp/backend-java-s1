package edu.hw6.tasks.task1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

public final class DiskMap implements Map<String, String> {
    private final HashMap<String, String> map;
    private final Path filePath;
    private static final String KEY_MUST_BE_STRING_ERROR = "key must be string";

    private DiskMap(final Path path) {
        this.filePath = path;
        this.map = new HashMap<>();
        try {
            final List<String> keysValues = Files.readAllLines(path);
            for (String toParse : keysValues) {
                final String[] keyValue = toParse.split("=");
                map.put(keyValue[0], keyValue[1]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Optional<String> getExtension(final String filename) {
        return Optional.ofNullable(filename)
            .filter(f -> f.contains("."))
            .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public static DiskMap open(final Path path) {
        Optional<String> extension = getExtension(path.getFileName().toString());
        if (!Files.exists(path)) {
            throw new IllegalArgumentException(path + " does not exist");
        } else if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException(path + " is not a '*.dm' file");
        } else if (extension.isEmpty() || !extension.get().equals("dm")) {
            throw new IllegalArgumentException("wrong file extension: " + path.getFileName());
        }
        return new DiskMap(path);
    }

    public static DiskMap open(final String pathToFile) {
        return open(Path.of(pathToFile));
    }

    public static DiskMap create(final String directory, final String filename) {
        return create(Path.of(directory), filename);
    }

    public static DiskMap create(final Path directory, final String filename) {
        try {
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            return new DiskMap(Files.createFile(directory.resolve(filename + ".dm")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DiskMap create(final String filename) {
        return create(System.getProperty("user.dir"), filename);
    }

    private void save() {
        try (FileChannel outChannel = FileChannel.open(filePath, WRITE, TRUNCATE_EXISTING)) {
            for (Map.Entry<String, String> pair : map.entrySet()) {
                final String toWrite = pair.getKey() + "=" + pair.getValue() + "\n";
                outChannel.write(ByteBuffer.wrap(toWrite.getBytes(StandardCharsets.UTF_8)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public String get(@NotNull Object key) {
        if (!(key instanceof String)) {
            throw new IllegalArgumentException(KEY_MUST_BE_STRING_ERROR);
        }
        return map.get(key);
    }

    @Nullable
    @Override
    public String put(String key, String value) {
        final String result = map.put(key, value);
        save();
        return result;
    }

    @Override
    public String remove(Object key) {
        if (!(key instanceof String)) {
            throw new IllegalArgumentException(KEY_MUST_BE_STRING_ERROR);
        }
        final String result = map.remove(key);
        save();
        return result;
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        map.putAll(m);
        save();
    }

    @Override
    public void clear() {
        map.clear();
        save();
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @NotNull
    @Override
    public Collection<String> values() {
        return map.values();
    }

    @NotNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        return map.entrySet();
    }
}
