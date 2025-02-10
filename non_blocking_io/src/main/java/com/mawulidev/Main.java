package com.mawulidev;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static com.mawulidev.FileCopier.copyFile;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        // Use command-line arguments if provided, otherwise use default file paths.
        Path sourceFile;
        Path destinationFile;

        if (args.length == 2) {
            sourceFile = Path.of(args[0]);
            destinationFile = Path.of(args[1]);
        } else {
            // Default file paths (ensure these paths point to files, not directories)
            sourceFile = Path.of("src/main/java/com/mawulidev/demo.txt");
            // Make sure to provide a file name in the destination path.
            destinationFile = Path.of("src/main/resources/demo_copy.txt");
            System.out.println("No command-line arguments provided. Using default paths:");
            System.out.println("Source: " + sourceFile);
//            System.out.println("Destination: " + destinationFile);
        }

        // Check if the source file exists before attempting to copy.
        if (!Files.exists(sourceFile)) {
            System.err.println("Source file does not exist: " + sourceFile);
            System.exit(1);
        }

        // Ensure the destination directory exists; if not, create it.
        Path parentDir = destinationFile.getParent();
        System.out.println("src/main/resources/demo_copy.txt");
        if (parentDir != null && !Files.exists(parentDir)) {
            try {
                Files.createDirectories(parentDir);
                System.out.println("Created missing destination directory: " + parentDir);
            } catch (IOException e) {
                System.err.println("Failed to create destination directory: " + parentDir);
                e.printStackTrace();
                System.exit(1);
            }
        }

        try {
            // Copy the file using the NIO-based copyFile method.
            copyFile(sourceFile, destinationFile);
            System.out.println("File copied successfully from " + sourceFile + " to " + destinationFile);
        } catch (IOException e) {
            // Handle any I/O exceptions that may occur.
            System.err.println("An I/O error occurred during file copying: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
