package com.mawulidev;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class NIOWriteSample {
    public static void main(String[] args) throws IOException {
        // Define the file path
        Path filePath = Path.of("output.txt");

        // Ensure the file exists before writing
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }

        // Data to write
        String data = "Hello, this is a sample text written using NIO!";
        ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());

        // Open the file channel in write mode
        try (FileChannel channel = FileChannel.open(filePath, StandardOpenOption.WRITE)) {
            // Write data to the file
            while (buffer.hasRemaining()) {
                channel.write(buffer);
            }
        }

        System.out.println("Data successfully written to file.");
    }
}
