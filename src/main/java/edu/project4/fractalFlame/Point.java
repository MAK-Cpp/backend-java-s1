package edu.project4.fractalFlame;

import static java.lang.Math.atan2;

public record Point(double x, double y) {
    public double r() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public double theta() {
        return atan2(this.y, this.x);
    }

    public double phi() {
        return atan2(this.x, this.y);
    }

    public Point multiply(double k) {
        return new Point(this.x * k, this.y * k);
    }

    public Point add(Point other) {
        return new Point(this.x + other.x, this.y + other.y);
    }

    @Override
    public String toString() {
        return "{" + this.x + "; " + this.y + "}";
    }
}
