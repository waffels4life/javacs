package com.javacs.projects.notemanager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

public class NoteManager {

    private static final Path NOTES_DIR = Path.of("notes");
    public static void main(String[] args) {
        setupNoteDirectory();
        createNote("shopping", "- coffee\n- cat food for Oreo\n- milk");
        createNote("java-tips", "Always close resources with try-with-resources!");
    }
    public static void setupNoteDirectory() {
        try {
            Files.createDirectories(NOTES_DIR);
            System.out.println("[SUCCESS] Note directory is ready.");
            System.out.println("[Path: "
                            + NOTES_DIR.toAbsolutePath()
                            + "]"
            );
        }
        catch (IOException e) {
            System.err.println("[ERROR] Couldn't create directory.");
            System.err.println("[e: "
                    + e.getMessage()
                    + "]"
            );
        }
    }

    public static void createNote(String name, String content) {
        Path notePath = NOTES_DIR.resolve(name + ".txt");

        try {
            Files.writeString(
                    notePath,
                    content,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE_NEW
            );
        }
        catch (FileAlreadyExistsException e) {
            System.err.println("[ERROR] Note already exists.");
            System.err.println("name: "
                    + name
                    + "]");
        }
        catch (Exception e) {
            System.err.println("[ERROR] Couldn't create note.");
            System.err.println("[e: "
                    + e.getMessage()
                    + "]"
            );
        }
    }
}

class NoteReader {

    private static Path NOTES_DIR = null;

    public NoteReader(String name) {
        NOTES_DIR = Path.of(name + ".txt");
    }

    public static void readAsString() {
        try {
            String content = Files.readString(NOTES_DIR, StandardCharsets.UTF_8);
            System.out.println(content);
        }
        catch (IOException e) {
            System.err.println("[ERROR] Couldn't load notes.");
            System.err.println("[e: "
                    + e.getMessage()
                    + "]"
            );
        }
    }

    public static void readAsLine() {
        try {
            List<String> lines = Files.readAllLines(NOTES_DIR, StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++)
                System.out.printf("Lines %d: %s%n", i + 1, lines.get(i));
        }
        catch (IOException e) {
            System.err.println("[ERROR] Couldn't load notes.");
            System.err.println("[e: "
                    + e.getMessage()
                    + "]"
            );
        }
    }

    public static void readAsStream() {
        try (Stream<String> lines = Files.lines(NOTES_DIR, StandardCharsets.UTF_8)) {
            lines
                    .map(String::trim)
                    .filter(l -> !l.isEmpty())
                    .filter(l -> l.startsWith("-"))
                    .forEach(System.out::println);
        }
        catch (Exception e) {
            System.err.println("[ERROR] Couldn't load notes.");
            System.err.println("[e: "
                    + e.getMessage()
                    + "]"
            );
        }
    }
}