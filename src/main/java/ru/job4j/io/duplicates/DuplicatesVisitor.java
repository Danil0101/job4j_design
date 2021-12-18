package ru.job4j.io.duplicates;


import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {
    private Map<FileProperty, Path> files = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        FileProperty fp = new FileProperty(file.toFile().length(), file.toFile().getName());
        if (files.containsKey(fp)) {
            if (files.get(fp) != null) {
                System.out.println(files.get(fp).toAbsolutePath());
                files.put(fp, null);
            }
            System.out.println(file.toAbsolutePath());
        } else {
            files.put(fp, file);
        }
        return super.visitFile(file, attrs);
    }
}
