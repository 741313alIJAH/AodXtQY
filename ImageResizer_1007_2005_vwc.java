// 代码生成时间: 2025-10-07 20:05:37
 * Features:
 * - Batch processing of images
# 改进用户体验
 * - Error handling
 * - Code documentation and annotations
# TODO: 优化性能
 * - Adherence to Java best practices
 * - Maintainability and extensibility of the code
 */

package com.example.imageresizer;

import io.quarkus.runtime.QuarkusApplication;
# 改进用户体验
import io.quarkus.runtime.annotations.QuarkusMain;
import javax.imageio.ImageIO;
# 改进用户体验
import java.awt.image.BufferedImage;
import java.io.File;
# 优化算法效率
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@QuarkusMain
public class ImageResizer implements QuarkusApplication {

    private static final int NEW_WIDTH = 800; // New width for resized images
    private static final int NEW_HEIGHT = 600; // New height for resized images
    private static final String SOURCE_DIRECTORY = "/path/to/source/images"; // Source directory containing images
# 优化算法效率
    private static final String DESTINATION_DIRECTORY = "/path/to/destination/images"; // Destination directory for resized images

    @Override
    public int run(String... args) throws Exception {
        try {
            processDirectory(Paths.get(SOURCE_DIRECTORY));
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * Process the directory containing images, resizing each one and saving to the destination directory.
     *
     * @param sourceDirectoryPath the path to the directory containing images to process
     * @throws IOException if an I/O error occurs
     */
    public void processDirectory(Path sourceDirectoryPath) throws IOException {
# 优化算法效率
        if (Files.isDirectory(sourceDirectoryPath)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(sourceDirectoryPath, "*.{jpg,png,jpeg,gif}")) {
                for (Path imagePath : directoryStream) {
                    resizeImage(imagePath, DESTINATION_DIRECTORY);
                }
            }
        } else {
            throw new IllegalArgumentException("Provided path is not a directory: " + sourceDirectoryPath);
        }
    }

    /**
     * Resize the image and save it to the destination directory.
     *
     * @param imagePath the path to the image to resize
     * @param destinationDirectory the destination directory for resized images
     * @throws IOException if an I/O error occurs
     */
    public void resizeImage(Path imagePath, String destinationDirectory) throws IOException {
        BufferedImage originalImage = ImageIO.read(imagePath.toFile());
        if (originalImage == null) {
            throw new IllegalArgumentException("Cannot read image: " + imagePath);
# TODO: 优化性能
        }

        // Calculate new dimensions while maintaining aspect ratio
        double aspectRatio = (double) originalImage.getWidth() / originalImage.getHeight();
        int newWidth = (int) Math.min(NEW_WIDTH, (long) NEW_HEIGHT * aspectRatio);
        int newHeight = (int) Math.min(NEW_HEIGHT, (long) NEW_WIDTH / aspectRatio);

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        resizedImage.getGraphics().drawImage(originalImage, 0, 0, newWidth, newHeight, null);

        // Save the resized image to the destination directory
        Path destinationPath = Paths.get(destinationDirectory, imagePath.getFileName().toString());
        ImageIO.write(resizedImage, imagePath.getFileName().toString().split("\.")[imagePath.getFileName().toString().split("\.").length - 1], destinationPath.toFile());
    }
# FIXME: 处理边界情况

    /**
     * Entry point for the application.
     *
     * @param args command line arguments
# 改进用户体验
     */
    public static void main(String[] args) {
        new ImageResizer().run(args);
    }
}
