package edu.project4.fractalFlame;

import edu.progressBar.ProgressBar;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import javax.imageio.ImageIO;

@SuppressWarnings("checkstyle:RegexpSinglelineJava")
public class FractalFlameGenerator {
    private final int countIterations;
    private final List<FlameFunction> functions;
    private final int[][] hitCount;
    private final BufferedImage flame;
    private final ImageSettings settings;
    private final Gap xGap;
    private final Gap yGap;
    private final AffineTransform resultTransform;
    private final int symmetry;
    private final int pointIterations;
    private static final double GAMMA = 2.2;
    private static final int MAX_COLOR_CHANNEL_VALUE = 256;
    private static final int MIN_ITERATIONS_TO_DRAW = 20;
    private static final int PROGRESS_BAR_LENGTH = 60;

    public FractalFlameGenerator(
        int countIterations,
        int countFunctions,
        Set<Variation> variationSet,
        ImageSettings settings,
        AffineTransform resultTransform,
        int symmetry,
        int pointIterations
    ) {
        this.countIterations = countIterations;
        this.functions =
            Stream.generate(() -> FlameFunction.randomFunction(variationSet)).limit(countFunctions).toList();
        this.flame = new BufferedImage(settings.width(), settings.height(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = flame.createGraphics();
        graphics.setPaint(Color.BLACK);
        graphics.fillRect(0, 0, settings.width(), settings.height());
        this.settings = settings;
        if (settings.width() > settings.height()) {
            double gap = ((double) settings.width()) / settings.height();
            xGap = new Gap(-gap, gap);
            yGap = new Gap(-1., 1.);
        } else {
            double gap = ((double) settings.height()) / settings.width();
            yGap = new Gap(-gap, gap);
            xGap = new Gap(-1., 1.);
        }
        this.hitCount = new int[settings.width()][settings.height()];
        this.resultTransform = resultTransform;
        this.symmetry = symmetry;
        this.pointIterations = pointIterations;
    }

    private void iteration(Random random) {
        Color pointColor = new Color(
            random.nextInt(MAX_COLOR_CHANNEL_VALUE),
            random.nextInt(MAX_COLOR_CHANNEL_VALUE),
            random.nextInt(MAX_COLOR_CHANNEL_VALUE)
        );
        Point point =
            new Point(random.nextDouble(xGap.min(), xGap.max()), random.nextDouble(yGap.min(), yGap.max()));
        for (int j = -MIN_ITERATIONS_TO_DRAW; j < pointIterations; j++) {
            int n = random.nextInt(functions.size());
            FlameFunction function = functions.get(n);
            point = function.apply(point);
            pointColor = function.getNewColor(pointColor);
            if (j >= 0) {
                double rotateAngle = 0;
                double rotateStep = 2 * Math.PI / symmetry;
                // symmetry
                for (int s = 0; s < symmetry; s++) {
                    rotateAngle += rotateStep;
                    Point finalPoint = resultTransform.apply(point);
                    double xRotated =
                        finalPoint.x() * Math.cos(rotateAngle) - finalPoint.y() * Math.sin(rotateAngle);
                    double yRotated =
                        finalPoint.x() * Math.sin(rotateAngle) + finalPoint.y() * Math.cos(rotateAngle);
                    if (xGap.contains(xRotated) && yGap.contains(yRotated)) {
                        int x = (int) ((xRotated - xGap.min()) / xGap.length() * settings.width());
                        int y = (int) ((yRotated - yGap.min()) / yGap.length() * settings.height());
                        synchronized (hitCount[x]) {
                            hitCount[x][y]++;
                        }
                        flame.setRGB(x, y, pointColor.getRGB());
                    }
                }
            }
        }
    }

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    public void generate() {
        SecureRandom random = new SecureRandom();
        final AtomicInteger iterator = new AtomicInteger(0);

        System.out.println("Generation progress:");
        Thread progress = new Thread(new ProgressBar(PROGRESS_BAR_LENGTH, iterator, countIterations));
        progress.start();
        for (; iterator.get() < countIterations; iterator.incrementAndGet()) {
            iteration(random);
        }
        try {
            progress.join();
        } catch (InterruptedException e) {
            progress.interrupt();
            throw new RuntimeException(e);
        }
    }

    public void fastGenerate() {
        final AtomicInteger iterator = new AtomicInteger(0);

        System.out.println("Generation progress:");
        try (ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())) {
            var progress = CompletableFuture.runAsync(new ProgressBar(PROGRESS_BAR_LENGTH, iterator, countIterations));
            var tasks = Stream.concat(Stream.generate(() -> CompletableFuture.runAsync(() -> {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                iteration(random);
                iterator.incrementAndGet();
            }, threadPool)).limit(countIterations), Stream.of(progress)).toArray(CompletableFuture[]::new);
            CompletableFuture.allOf(tasks).join();
        }
    }

    // logarithmic gamma-correction
    public void correct() {
        double max = 0;
        for (int[] ints : hitCount) {
            for (int anInt : ints) {
                if (anInt == 0) {
                    continue;
                }
                max = Math.max(max, Math.log10(anInt));
            }
        }
        for (int i = 0; i < hitCount.length; i++) {
            for (int j = 0; j < hitCount[i].length; j++) {
                double alpha = Math.log10(hitCount[i][j]) / max;
                Color color = new Color(flame.getRGB(i, j));
                double step = Math.pow(alpha, 1 / GAMMA);
                int red = (int) (color.getRed() * step);
                int green = (int) (color.getGreen() * step);
                int blue = (int) (color.getBlue() * step);
                flame.setRGB(i, j, new Color(red, green, blue).getRGB());
            }
        }
    }

    public void save() {
        System.out.print("saving image...");
        try {
            if (!Files.exists(settings.folder())) {
                Files.createDirectories(settings.folder());
            }
            Path imagePath = settings.folder().resolve(settings.imageName() + "." + settings.format());
            if (!Files.exists(imagePath)) {
                Files.createFile(imagePath);
            }
            ImageIO.write(flame, settings.format().toString(), imagePath.toFile());
            System.out.println(" done!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void create(boolean fast) {
        if (fast) {
            fastGenerate();
        } else {
            generate();
        }
        correct();
        save();
    }
}
