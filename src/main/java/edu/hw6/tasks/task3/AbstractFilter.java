package edu.hw6.tasks.task3;

import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@SuppressWarnings("checkstyle:ConstantName")
public interface AbstractFilter extends DirectoryStream.Filter<Path> {
    AbstractFilter executable = Files::isExecutable;
    AbstractFilter hidden = Files::isHidden;
    AbstractFilter readable = Files::isReadable;
    AbstractFilter regularFile = Files::isRegularFile;
    AbstractFilter writable = Files::isWritable;

    default AbstractFilter and(final AbstractFilter filter) {
        return (Path entry) -> this.accept(entry) && filter.accept(entry);
    }

    static AbstractFilter regexContains(final String regex) {
        final Pattern pattern = Pattern.compile(regex);
        return (Path entry) -> pattern.matcher(entry.getFileName().toString()).find();
    }

    static AbstractFilter extension(final String ext) {
        final Pattern pattern = Pattern.compile(".*\\." + ext);
        return (Path entry) -> pattern.matcher(entry.getFileName().toString()).matches();
    }

    static AbstractFilter largerThan(final long size) {
        return (Path entry) -> Files.size(entry) > size;
    }

    static AbstractFilter largerEqThan(final long size) {
        return (Path entry) -> Files.size(entry) >= size;
    }

    static AbstractFilter lessThan(final long size) {
        return (Path entry) -> Files.size(entry) < size;
    }

    static AbstractFilter lessEqThan(final long size) {
        return (Path entry) -> Files.size(entry) <= size;
    }

    static AbstractFilter equals(final long size) {
        return (Path entry) -> Files.size(entry) == size;
    }

    static AbstractFilter globMatches(final String glob) {
        return FileSystems.getDefault().getPathMatcher("glob:**/" + glob)::matches;
    }

    static AbstractFilter magicNumber(final int... unsignedBytes) {
        final List<Byte> bytesList = Arrays.stream(unsignedBytes).mapToObj(x -> (byte) x).toList();
        return (Path entry) -> {
            final byte[] readBytes = Files.readAllBytes(entry);
            boolean ans = true;
            for (int i = 0; i < unsignedBytes.length; i++) {
                ans = (readBytes[i] == bytesList.get(i));
            }
            return ans;
        };
    }
}
