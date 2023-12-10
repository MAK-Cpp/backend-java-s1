package edu.project4;

import edu.project4.fractalFlame.AffineTransform;
import edu.project4.fractalFlame.FractalFlameGenerator;
import edu.project4.fractalFlame.ImageFormat;
import edu.project4.fractalFlame.ImageSettings;
import edu.project4.fractalFlame.Variation;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import static java.lang.Double.parseDouble;
import static java.util.Map.entry;

public final class Main {
    private static final Map<Integer, Variation> VARIATIONS_WITHOUT_PARAMETERS = Map.ofEntries(
        entry(0, Variation.V0), entry(1, Variation.V1), entry(2, Variation.V2), entry(3, Variation.V3),
        entry(4, Variation.V4), entry(5, Variation.V5), entry(6, Variation.V6), entry(7, Variation.V7),
        entry(8, Variation.V8), entry(9, Variation.V9), entry(10, Variation.V10), entry(11, Variation.V11),
        entry(12, Variation.V12), entry(13, Variation.V13), entry(14, Variation.V14), entry(15, Variation.V15),
        entry(16, Variation.V16), entry(17, Variation.V17), entry(18, Variation.V18), entry(19, Variation.V19),
        entry(20, Variation.V20), entry(21, Variation.V21), entry(22, Variation.V22), entry(27, Variation.V27),
        entry(28, Variation.V28), entry(29, Variation.V29), entry(31, Variation.V31), entry(34, Variation.V34),
        entry(35, Variation.V35), entry(41, Variation.V41), entry(42, Variation.V42), entry(43, Variation.V43),
        entry(44, Variation.V44), entry(45, Variation.V45), entry(46, Variation.V46), entry(47, Variation.V47),
        entry(48, Variation.V48)
    );
    private static final Map<Integer, Function<Double, Variation>>
        VARIATIONS_WITH_1_PARAMETER = Map.of(26, Variation::V26, 36, Variation::V36);
    private static final Map<Integer, BiFunction<Double, Double, Variation>> VARIATIONS_WITH_2_PARAMETERS = Map.of(
        25, Variation::V25, 30, Variation::V30, 32, Variation::V32, 33, Variation::V33,
        39, Variation::V39, 40, Variation::V40
    );
    private static final Map<Integer, TriFunction<Double, Double, Double, Variation>>
        VARIATIONS_WITH_3_PARAMETERS = Map.of(23, Variation::V23, 37, Variation::V37);
    private static final Map<Integer, QuadFunction<Double, Double, Double, Double, Variation>>
        VARIATIONS_WITH_4_PARAMETERS = Map.of(24, Variation::V24, 38, Variation::V38);

    private Main() {
    }

    private static int getIntValue(String name, String value) throws IllegalArgumentException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("expected integer for " + name + ", got " + value);
        }
    }

    @SuppressWarnings({"checkstyle:ModifiedControlVariable", "checkstyle:MagicNumber", "checkstyle:InnerAssignment",
        "checkstyle:CyclomaticComplexity"})
    public static void main(String[] args) throws IllegalArgumentException {
        HashSet<Variation> variations = new HashSet<>();

        int widht = 1920;
        int height = 1080;
        int countFunctions = 10;
        int countIterations = 100000;
        int symmetry = 1;
        int pointIterations = 1000;
        AffineTransform resultTransform = new AffineTransform(1, 0, 0, 0, 1, 0);
        Path folder = Path.of(System.getProperty("user.dir"));
        String imageName = "fractal flame";
        ImageFormat format = ImageFormat.PNG;

        for (int i = 0; i < args.length; i++) {
            String flag = args[i];
            if (flag.charAt(0) != '-') {
                throw new IllegalArgumentException("expected flag, got " + flag);
            }
            switch (flag.charAt(1)) {
                case 'V' -> {
                    int variationId = Integer.parseInt(flag.substring(2));
                    variations.add(
                        switch (variationId) {
                            case 26, 36 -> VARIATIONS_WITH_1_PARAMETER.get(variationId).apply(parseDouble(args[++i]));
                            case 25, 30, 32, 33, 39, 40 -> VARIATIONS_WITH_2_PARAMETERS.get(variationId)
                                .apply(parseDouble(args[++i]), parseDouble(args[++i]));
                            case 23, 37 -> VARIATIONS_WITH_3_PARAMETERS.get(variationId)
                                .apply(parseDouble(args[++i]), parseDouble(args[++i]), parseDouble(args[++i]));
                            case 24, 38 -> VARIATIONS_WITH_4_PARAMETERS.get(variationId).apply(
                                parseDouble(args[++i]), parseDouble(args[++i]),
                                parseDouble(args[++i]), parseDouble(args[++i])
                            );
                            default -> {
                                if (!VARIATIONS_WITHOUT_PARAMETERS.containsKey(variationId)) {
                                    throw new IllegalArgumentException("unknown variation: " + flag);
                                }
                                yield VARIATIONS_WITHOUT_PARAMETERS.get(variationId);
                            }
                        });
                }
                case 'W' -> widht = getIntValue("width", args[++i]);
                case 'H' -> height = getIntValue("height", args[++i]);
                case 'C' -> countFunctions = getIntValue("function count", args[++i]);
                case 'O' -> {
                    folder = Path.of(args[++i]);
                    imageName = args[++i];
                    format = ImageFormat.valueOf(args[++i].toUpperCase());
                }
                case 'S' -> symmetry = getIntValue("symmetry", args[++i]);
                case 'F' -> resultTransform = new AffineTransform(
                    parseDouble(args[++i]),
                    parseDouble(args[++i]),
                    parseDouble(args[++i]),
                    parseDouble(args[++i]),
                    parseDouble(args[++i]),
                    parseDouble(args[++i])
                );
                case 'I' -> countIterations = getIntValue("count iterations", args[++i]);
                case 'P' -> pointIterations = getIntValue("point iterations", args[++i]);
                default -> throw new IllegalArgumentException("unknown flag: " + flag);
            }
        }
        FractalFlameGenerator generator = new FractalFlameGenerator(countIterations,
            countFunctions,
            variations,
            new ImageSettings(widht, height, folder, imageName, format),
            resultTransform,
            symmetry,
            pointIterations
        );
        generator.create(true);
    }

    @FunctionalInterface
    public interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);

        default <K> TriFunction<T, U, V, K> andThen(Function<? super R, ? extends K> after) {
            Objects.requireNonNull(after);
            return (T t, U u, V v) -> after.apply(apply(t, u, v));
        }
    }

    @FunctionalInterface
    public interface QuadFunction<T, U, V, N, R> {
        R apply(T t, U u, V v, N n);

        default <K> QuadFunction<T, U, V, N, K> andThen(Function<? super R, ? extends K> after) {
            Objects.requireNonNull(after);
            return (T t, U u, V v, N n) -> after.apply(apply(t, u, v, n));
        }
    }
}
/*
-V0
-V1
-V2
-V3
-V4
-V5
-V6
-V7
-V8
-V9
-V10
-V11
-V12
-V13
-V14
-V15
-V16
-V17
*/
