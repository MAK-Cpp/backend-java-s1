package edu.project4.fractalFlame;

import java.awt.Color;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class FlameFunction {
    private final AffineTransform transform;
    private final List<Variation> variations;
    private final List<Double> weights;
    private final Color color;
    private static final Double EPS = 1e-5;
    private static final int MAX_COLOR_CHANNEL_VALUE = 256;

    public FlameFunction(
        AffineTransform transform,
        List<Variation> variations,
        List<Double> weights,
        Color color
    ) {
        double weightsSum = weights.stream().mapToDouble(Double::doubleValue).sum();
        if (Math.abs(weightsSum - 1) > EPS) {
            throw new IllegalArgumentException("wrong weights " + weights + ": sum = " + weightsSum);
        }
        assert variations.size() == weights.size();
        this.transform = transform;
        this.variations = variations;
        this.weights = weights;
        this.color = color;
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public static FlameFunction randomFunction(Set<Variation> variationSet) {
        SecureRandom random = new SecureRandom();
        AffineTransform transform = new AffineTransform();
        int countVariations = random.nextInt(1, variationSet.size() + 1);

        ArrayList<Variation> variationArrayList = new ArrayList<>(variationSet);
        Collections.shuffle(variationArrayList);
        List<Variation> variation = variationArrayList.stream().limit(countVariations).toList();

        List<Integer> nonNormalizedWeights =
            Stream.generate(() -> random.nextInt(0, 1000)).limit(countVariations).toList();
        int weightsSum = nonNormalizedWeights.stream().mapToInt(Integer::intValue).sum();
        List<Double> weights = nonNormalizedWeights.stream().map(w -> ((double) w) / weightsSum).toList();

        Color color = new Color(
            random.nextInt(MAX_COLOR_CHANNEL_VALUE),
            random.nextInt(MAX_COLOR_CHANNEL_VALUE),
            random.nextInt(MAX_COLOR_CHANNEL_VALUE)
        );

        return new FlameFunction(transform, variation, weights, color);
    }

    public Point apply(Point point) {
        Point result = new Point(0, 0);
        Point transformed = transform.apply(point);
        for (int i = 0; i < variations.size(); i++) {
            Variation variation = variations.get(i);
            double weight = weights.get(i);
            result = result.add(variation.apply(transformed, transform, weight).multiply(weight));
        }
        return result;
    }

    public Color getNewColor(Color oldColor) {
        return new Color(
            (color.getRed() + oldColor.getRed()) / 2,
            (color.getGreen() + oldColor.getGreen()) / 2,
            (color.getBlue() + oldColor.getBlue()) / 2
        );
    }
}
