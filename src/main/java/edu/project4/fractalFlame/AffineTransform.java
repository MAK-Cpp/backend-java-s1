package edu.project4.fractalFlame;

import java.security.SecureRandom;

public class AffineTransform {
    // coefficients for affine transform
    private final double a;
    private final double b;
    private final double c;
    private final double d;
    private final double e;
    private final double f;

    @SuppressWarnings("checkstyle:MagicNumber")
    public AffineTransform() {
        SecureRandom random = new SecureRandom();
        double ai;
        double bi;
        double di;
        double ei;
        do {
            ai = random.nextDouble(-1, 1);
            bi = random.nextDouble(-1, 1);
            di = random.nextDouble(-1, 1);
            ei = random.nextDouble(-1, 1);
        } while (!((ai * ai + di * di) <= 1 && (bi * bi + ei * ei) <= 1
            && (ai * ai + bi * bi + di * di + ei * ei) <= (1 + (ai * ei - di * bi) * (ai * ei - di * bi))));
        a = ai;
        b = bi;
        c = random.nextDouble(-2, 2);
        d = di;
        e = ei;
        f = random.nextDouble(-2, 2);
    }

    public AffineTransform(double a, double b, double c, double d, double e, double f) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }

    public Point apply(Point point) {
        return new Point(
            a * point.x() + b * point.y() + c,
            d * point.x() + e * point.y() + f
        );
    }

    public double a() {
        return a;
    }

    public double b() {
        return b;
    }

    public double c() {
        return c;
    }

    public double d() {
        return d;
    }

    public double e() {
        return e;
    }

    public double f() {
        return f;
    }
}
