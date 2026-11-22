package com.javacs.api.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class NewFiles {
    private static final Path readPath = Path.of("src/main/java/com/javacs/api/files/demo.txt");
    private static final Path writePath = Path.of("src/main/java/com/javacs/api/files/write.txt");

    public static void readFile(Path path) {
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(path.toFile(), StandardCharsets.UTF_8)
            );

            String line;
            while ((line = bufferedReader.readLine()) != null)
                System.out.println(line);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeFile(Path path) {
        Objects.requireNonNull(path);

        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                String line = scanner.nextLine();
                if (line.strip().equalsIgnoreCase("done")) break;

                try {
                    writer.write(line);
                    writer.newLine();
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printFileLines(Path path) {
        Objects.requireNonNull(path);

        if (!Files.isRegularFile(path))
            throw new IllegalArgumentException(
                    "Not a regular file: " + path
            );

        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            lines.forEach(System.out::println);
        }
        catch (IOException e) {
            throw new UncheckedIOException(
                    "Failed to read file: " + path, e
            );
        }
    }

    public static void manageDirectory(Path path) {
        Objects.requireNonNull(path);

        if (!Files.isDirectory(path))
            throw new IllegalArgumentException(
                    "Not a directory: " + path
            );

        try (Stream<Path> pathStream = Files.list(path)) {
            List<Path> lisOfFiles = pathStream
                    .filter(Files::isRegularFile)
                    .toList();

            if (lisOfFiles.isEmpty()) {
                System.out.println("Directory is empty: " + path);
                return;
            }

            System.out.println("..:: Files ::..");

            long[] sizes = new long[lisOfFiles.size() - 1];

            for (int i = 0; i < lisOfFiles.size() - 1; i++)
                sizes[i] = Files.size(lisOfFiles.get(i));

            for (int i = 0; i < lisOfFiles.size() - 1; i++) {
                System.out.printf("%-20s %s%n",
                        lisOfFiles.get(i).getFileName(),
                        humanReadable(sizes[i]));
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String humanReadable(long bytes) {
        if (bytes < 1024) return bytes + " B";
        double kb = bytes / 1024.0;
        if (kb < 1024) return String.format("%.1f KB", kb);
        double mb = kb / 1024.0;
        if (mb < 1024) return String.format("%.1f MB", mb);
        return String.format("%.2f GB", mb / 1024.0);
    }

    public static void main(String[] args) {
        manageDirectory(Path.of("src/main/java/com/javacs/api/files"));
    }
}
