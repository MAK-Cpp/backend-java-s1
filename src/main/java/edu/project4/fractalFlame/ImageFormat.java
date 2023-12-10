package edu.project4.fractalFlame;

public enum ImageFormat {
    PNG("png"), JPG("jpg"), GIF("gif");

    private final String format;

    ImageFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return format;
    }
}
