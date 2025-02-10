package com.mawulidev;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileCopier {

    /**
     * Copies the contents of the source file to the destination file using NIO channels.
     *
     * @param sourcePath      the path to the source file
     * @param destinationPath the path to the destination file
     * @throws IOException if an I/O error occurs during file operations
     */
    public static void copyFile(Path sourcePath, Path destinationPath) throws IOException {
        // Open the source file channel in read mode.
        // Open the destination file channel in write mode (create file if it doesn't exist, truncate if it does).
        try (FileChannel sourceChannel = FileChannel.open(sourcePath, StandardOpenOption.READ);
             FileChannel destinationChannel = FileChannel.open(destinationPath,
                     StandardOpenOption.WRITE,
                     StandardOpenOption.CREATE,
                     StandardOpenOption.TRUNCATE_EXISTING)) {

            // Create a direct ByteBuffer with a buffer size of 1024 bytes.
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

            // Read bytes from the source file channel into the buffer.
            // Then write the bytes from the buffer to the destination channel.
            while (sourceChannel.read(buffer) > 0) {
                // Flip the buffer from writing mode to reading mode.
                buffer.flip();

                // Write all the bytes in the buffer to the destination channel.
                while (buffer.hasRemaining()) {
                    destinationChannel.write(buffer);
                }

                // Clear the buffer to be ready for the next read.
                buffer.clear();
            }
        }
    }
}