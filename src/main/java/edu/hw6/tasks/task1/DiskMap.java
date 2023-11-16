package edu.hw6.tasks.task1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DiskMap implements Map<String, String> {
    private final Properties map;
    private final String filePath;

    private DiskMap(final String path) {
        this.filePath = path;
        this.map = new Properties();
        try {
            map.load(new FileInputStream(path));
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
        return new DiskMap(path.toString());
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
            return new DiskMap(Files.createFile(directory.resolve(filename + ".dm")).toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DiskMap create(final String filename) {
        return create(System.getProperty("user.dir"), filename);
    }

    public void save() {
        try {
            map.store(new FileOutputStream(filePath), null);
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
            throw new IllegalArgumentException("key must be string");
        }
        return map.getProperty((String) key);
    }

    @Nullable
    @Override
    public String put(String key, String value) {
        return (String) map.setProperty(key, value);
    }

    @Override
    public String remove(Object key) {
        if (!(key instanceof String)) {
            throw new IllegalArgumentException("key must be string");
        }
        return (String) map.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return map.keySet().stream().map(x -> (String) x).collect(Collectors.toSet());
    }

    @NotNull
    @Override
    public Collection<String> values() {
        return map.values().stream().map(x -> (String) x).toList();
    }

    @NotNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        return map.entrySet().stream()
            .map(x -> Map.entry((String) x.getKey(), (String) x.getValue()))
            .collect(Collectors.toSet());
    }
}
