package com.javacs.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class JProperties {

    private final Properties properties = new Properties();
    private static final String CONFIG_FILE = "my.properties";

    public void loadProperties() {
        try (FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE)) {
            properties.load(fileInputStream);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadPropertiesPro() throws IOException {
        try (Reader reader = Files.newBufferedReader(
                        Path.of(CONFIG_FILE),
                        StandardCharsets.UTF_8)) {
            properties.load(reader);
        }
    }

    public void saveProperties() {
        properties.setProperty("app.name", "Oreo");
        properties.setProperty("app.version", "0.2.0");

        try (FileOutputStream fileOutputStream = new FileOutputStream(CONFIG_FILE)) {
            properties.store(fileOutputStream, null);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void savePropertiesPro() {
        properties.setProperty("app.name", "Oreo");
        properties.setProperty("app.version", "0.2.0");

        try (Writer writer = Files.newBufferedWriter(
                Path.of(CONFIG_FILE),
                StandardCharsets.UTF_8
        )) {
            properties.store(writer, null);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void printProperties() {
        String name = properties.getProperty("app.name");
        String version = properties.getProperty("app.version");
    }
}
