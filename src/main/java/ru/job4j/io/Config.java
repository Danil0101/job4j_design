package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Config {
    private final String path;
    private final Map<String, String> values = new HashMap<>();

    public Config(final String path) {
        this.path = path;
    }

    public void load() {
        try (BufferedReader read =
                     new BufferedReader(new FileReader(this.path))) {
            while (read.ready()) {
                String str = read.readLine();
                if (!str.isEmpty() && !str.startsWith("#")) {
                    String[] pair = str.split("=");
                    if (pair.length != 2
                            || pair[0].isEmpty()
                            || pair[1].isEmpty()) {
                        throw new IllegalArgumentException();
                    }
                    values.put(pair[0], pair[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String value(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return values.get(key);
    }

    @Override
    public String toString() {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach(out::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Config("app.properties"));
    }
}
