package edu.project4.fractalFlame;

import java.security.SecureRandom;
import static java.lang.Math.PI;
import static java.lang.Math.ceil;
import static java.lang.Math.cos;
import static java.lang.Math.cosh;
import static java.lang.Math.exp;
import static java.lang.Math.log10;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sinh;
import static java.lang.Math.sqrt;
import static java.lang.Math.tan;

@SuppressWarnings({"checkstyle:MethodName", "checkstyle:MagicNumber"})
@FunctionalInterface
public interface Variation {
    Point apply(Point point, AffineTransform transform, double variationWeight);

    SecureRandom VARIATION_RANDOM = new SecureRandom();

    RandomVariable<Double> OMEGA = () -> VARIATION_RANDOM.nextBoolean() ? 0 : PI;

    RandomVariable<Double> PSI = Math::random;

    RandomVariable<Double> LAMBDA = () -> VARIATION_RANDOM.nextBoolean() ? 1.0 : -1.0;

    Variation V0 = (point, transform, weight) -> point;

    Variation V1 = (point, transform, weight) -> new Point(sin(point.x()), cos(point.y()));

    Variation V2 = (point, transform, weight) -> point.multiply(1 / pow(point.r(), 2));

    Variation V3 = (point, transform, weight) -> {
        double r2 = pow(point.r(), 2);
        double sin = sin(r2);
        double cos = cos(r2);
        return new Point(point.x() * sin - point.y() * cos, point.x() * cos + point.y() * sin);
    };

    Variation V4 = (point, transform, weight) -> {
        double r = point.r();
        return new Point((point.x() - point.y()) * (point.x() + point.y()) / r, 2 * point.x() * point.y() / r);
    };

    Variation V5 = (point, transform, weight) -> new Point(point.theta() / PI, point.r() - 1);

    Variation V6 = (point, transform, weight) -> {
        double r = point.r();
        double theta = point.theta();
        return new Point(r * sin(theta + r), r * cos(theta - r));
    };

    Variation V7 = (point, transform, weight) -> {
        double r = point.r();
        double theta = point.theta();
        double k = r * theta;
        return new Point(r * sin(k), -r * cos(k));
    };

    Variation V8 = (point, transform, weight) -> {
        double r = point.r();
        double k = point.theta() / PI;
        double p = PI * r;
        return new Point(k * sin(p), k * cos(p));
    };

    Variation V9 = (point, transform, weight) -> {
        double r = point.r();
        double theta = point.theta();
        double reversedR = 1 / r;
        return new Point(reversedR * (cos(theta) + sin(r)), reversedR * (sin(theta) - cos(r)));
    };

    Variation V10 = (point, transform, weight) -> {
        double r = point.r();
        double theta = point.theta();
        return new Point(sin(theta) / r, r * cos(theta));
    };

    Variation V11 = (point, transform, weight) -> {
        double r = point.r();
        double theta = point.theta();
        return new Point(sin(theta) * cos(r), cos(theta) * sin(r));
    };

    Variation V12 = (point, transform, weight) -> {
        double r = point.r();
        double theta = point.theta();
        double p0Cube = pow(sin(theta + r), 3);
        double p1Cube = pow(cos(theta - r), 3);
        return new Point(r * (p0Cube + p1Cube), r * (p0Cube - p1Cube));
    };

    Variation V13 = (point, transform, weight) -> {
        double r = sqrt(point.r());
        double k = point.theta() / 2 + OMEGA.get();
        return new Point(r * cos(k), r * sin(k));
    };

    Variation V14 = (point, transform, weight) -> new Point(
        point.x() >= 0 ? point.x() : 2 * point.x(),
        point.y() >= 0 ? point.y() : point.y() / 2
    );

    Variation V15 = (point, transform, weight) -> new Point(
        point.x() + transform.b() * sin(point.y() / pow(transform.c(), 2)),
        point.y() + transform.e() * sin(point.x() / pow(transform.f(), 2))
    );

    Variation V16 = (point, transform, weight) -> {
        double k = 2.0 / (point.r() + 1);
        return new Point(point.y() * k, point.x() * k);
    };

    Variation V17 = (point, transform, weight) -> new Point(
        point.x() + transform.c() * sin(tan(3 * point.y())),
        point.y() + transform.f() * sin(tan(3 * point.x()))
    );

    Variation V18 = (point, transform, weight) -> {
        double k = exp(point.x() - 1);
        double t = PI * point.y();
        return new Point(k * cos(t), k * sin(t));
    };

    Variation V19 = (point, transform, weight) -> {
        double theta = point.theta();
        double sinTetta = sin(theta);
        double k = pow(point.r(), sinTetta);
        return new Point(k * cos(theta), k * sinTetta);
    };

    Variation V20 = (point, transform, weight) -> {
        double k = PI * point.x();
        return new Point(cos(k) * cosh(point.y()), -sin(k) * sinh(point.y()));
    };

    Variation V21 = (point, transform, weight) -> {
        double cc = transform.c() * transform.c();
        double r = point.r();
        double theta = point.theta();
        double k = ((r + cc) % (2 * cc)) - cc + r * (1 - cc);
        return new Point(k * cos(theta), k * sin(theta));
    };

    Variation V22 = (point, transform, weight) -> {
        double r = point.r();
        double theta = point.theta();
        double t = PI * transform.c() * transform.c();
        double halfT = t / 2;
        double k = theta + ((theta + transform.f()) % t > halfT ? -halfT : halfT);
        return new Point(r * cos(k), r * sin(k));
    };

    static Variation V23(double blobHigh, double blobLow, double blobWaves) {
        return (point, transform, weight) -> {
            double theta = point.theta();
            double k = point.r() * (blobLow + ((blobHigh - blobLow) / 2) * (sin(blobWaves * theta) + 1));
            return new Point(k * cos(theta), k * sin(theta));
        };
    }

    static Variation V24(double pdjA, double pdjB, double pdjC, double pdjD) {
        return (point, transform, weight) -> new Point(
            sin(pdjA * point.y()) - cos(pdjB * point.x()),
            sin(pdjC * point.x()) - cos(pdjD * point.y())
        );
    }

    static Variation V25(double fan2X, double fan2Y) {
        final double p1 = PI * fan2X * fan2X;
        final double halfP1 = p1 / 2;
        return (point, transform, weight) -> {
            double r = point.r();
            double theta = point.theta();
            double t = theta + fan2Y - p1 * ((int) (2 * theta * fan2Y / p1));
            double k = theta + (t > halfP1 ? -halfP1 : halfP1);
            return new Point(r * sin(k), r * cos(k));
        };
    }

    static Variation V26(double rings2Val) {
        final double p = rings2Val * rings2Val;
        return (point, transform, weight) -> {
            double r = point.r();
            double theta = point.theta();
            double t = r - 2 * p * ((int) (r + p / (2 * p))) + r * (1 - p);
            return new Point(t * sin(theta), t * cos(theta));
        };
    }

    Variation V27 = (point, transform, weight) -> point.multiply(2 / (point.r() + 1));

    Variation V28 = (point, transform, weight) -> point.multiply(4 / (pow(point.r(), 2) + 4));

    Variation V29 = (point, transform, weight) -> new Point(sin(point.x()), point.y());

    static Variation V30(double perspectiveAngle, double perspectiveDist) {
        return (point, transform, weight) -> {
            double k = perspectiveDist / (perspectiveDist - point.y() * sin(perspectiveAngle));
            return new Point(point.x() * k, k * point.y() * cos(perspectiveAngle));
        };
    }

    Variation V31 = (point, transform, weight) -> {
        double psi = PSI.get();
        double k = 2 * PI * PSI.get();
        return new Point(psi * point.x() * cos(k), psi * point.y() * sin(k));
    };

    static Variation V32(double juliaNPower, double juliaNDist) {
        final double p3 = (int) (Math.abs(juliaNPower) * PSI.get());
        return (point, transform, weight) -> {
            double t = (point.phi() + 2 * PI * p3) / juliaNPower;
            double k = pow(point.r(), juliaNDist / juliaNPower);
            return new Point(k * cos(t), k * sin(t));
        };
    }

    static Variation V33(double juliaScopePower, double juliaScopeDist) {
        final double p3 = (int) (Math.abs(juliaScopePower) * PSI.get());
        return (point, transform, weight) -> {
            double t = (LAMBDA.get() * point.phi() + 2 * PI * p3) / juliaScopePower;
            double k = pow(point.r(), juliaScopeDist / juliaScopePower);
            return new Point(k * cos(t), k * sin(t));
        };
    }

    Variation V34 = (point, transform, weight) -> {
        double psi = PSI.get();
        double k = 2 * PI * PSI.get();
        return new Point(psi * cos(k), psi * sin(k));
    };

    Variation V35 = (point, transform, weight) -> {
        double k = PSI.get() + PSI.get() + PSI.get() + PSI.get() - 2;
        double t = 2 * PI * PSI.get();
        return new Point(k * cos(t), k * sin(t));
    };

    static Variation V36(double radialBlurAngle) {
        final double p1 = radialBlurAngle * PI / 2;
        return (point, transform, weight) -> {
            double r = point.r();
            double t1 = weight * (PSI.get() + PSI.get() + PSI.get() + PSI.get() - 2);
            double t2 = point.phi() + t1 * sin(p1);
            double t3 = t1 * cos(p1) - 1;
            return new Point(
                (r * cos(t2) + t3 * point.x()) / weight,
                (r * sin(t2) + t3 * point.y()) / weight
            );
        };
    }

    static Variation V37(double pieSlices, double pieRotation, double pieThickness) {
        double t1 = ((int) (PSI.get() * pieSlices + 0.5));
        double t2 = pieRotation + (2 * PI / pieSlices) * (t1 + PSI.get() * pieThickness);
        double psi = PSI.get();
        return (point, transform, weight) -> new Point(cos(t2) * psi, sin(t2) * psi);
    }

    static Variation V38(double ngonPower, double ngonSides, double ngonCorners, double ngonCircle) {
        final double p2 = 2 * PI / ngonSides;
        return (point, transform, weight) -> {
            double phi = point.phi();
            double t3 = phi - p2 * ceil(phi / p2);
            double t4 = t3 > p2 / 2 ? t3 : t3 - p2;
            return point.multiply((ngonCorners * (1 / cos(t4) - 1) + ngonCircle) / Math.pow(point.r(), ngonPower));
        };
    }

    static Variation V39(double curlC1, double curlC2) {
        return (point, transform, weight) -> {
            double t1 = 1 + curlC1 * point.x() + curlC2 * (point.x() * point.x() - point.y() * point.y());
            double t2 = curlC1 * point.y() + 2 * curlC2 * point.x() * point.y();
            double k = 1 / (t1 * t1 + t2 * t2);
            return new Point((point.x() * t1 + point.y() * t2) * k, (point.y() * t1 - point.x() * t2) * k);
        };
    }

    static Variation V40(double rectanglesX, double rectanglesY) {
        return (point, transform, weight) -> new Point(
            (2 * ceil(point.x() / rectanglesX) + 1) * rectanglesX - point.x(),
            (2 * ceil(point.y() / rectanglesY) + 1) * rectanglesY - point.y()
        );
    }

    Variation V41 = (point, transform, weight) -> {
        double psi = PSI.get();
        double k = psi * PI * weight;
        double sin = sin(k);
        return new Point(sin, sin * sin / cos(k));
    };

    Variation V42 = (point, transform, weight) -> new Point(sin(point.x()) / cos(point.y()), tan(point.y()));

    Variation V43 = (point, transform, weight) -> new Point(PSI.get() - 0.5, PSI.get() - 0.5);

    Variation V44 = (point, transform, weight) -> {
        double k = weight * tan(PSI.get() * PI * weight) / pow(point.r(), 2);
        return new Point(k * cos(point.x()), k * sin(point.y()));
    };

    Variation V45 = (point, transform, weight) -> {
        double psi = PSI.get();
        double r = point.r();
        double k = psi * r * weight;
        double cos = cos(k);
        double sin = sin(k);
        return new Point(point.x() * (cos + sin), point.x() * (cos - sin));
    };

    Variation V46 = (point, transform, weight) -> new Point(point.x(), 1 / (weight * cos(weight * point.r())));

    Variation V47 = (point, transform, weight) -> {
        double psi = PSI.get();
        double r = point.r();
        double k = psi * r * weight;
        double sin = sin(k);
        double t = log10(sin * sin) + cos(k);
        return new Point(t * point.x(), t - PI * sin);
    };

    Variation V48 =
        (point, transform, weight) -> point.multiply(sqrt(1 / pow(point.x() * point.x() - point.y() * point.y(), 2)));

    @FunctionalInterface
    interface RandomVariable<T> {
        T get();
    }
}
