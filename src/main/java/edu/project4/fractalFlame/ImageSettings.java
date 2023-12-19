package edu.project4.fractalFlame;

import java.nio.file.Path;

public record ImageSettings(int width, int height, Path folder, String imageName, ImageFormat format) {
}
