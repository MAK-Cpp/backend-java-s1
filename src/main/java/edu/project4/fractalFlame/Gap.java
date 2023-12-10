package edu.project4.fractalFlame;

public record Gap(double min, double max) {
    public boolean contains(double val) {
        return min <= val && val <= max;
    }

    public double length() {
        return max - min;
    }

    @Override
    public String toString() {
        return "[" + min + "; " + max + "]";
    }
}
