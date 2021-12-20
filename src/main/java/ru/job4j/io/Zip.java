package ru.job4j.io;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    public static void packFiles(List<File> sources, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            for (File file : sources) {
                zip.putNextEntry(new ZipEntry(file.getPath()));
                try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(file))) {
                    zip.write(out.readAllBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void packSingleFile(File source, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            zip.putNextEntry(new ZipEntry(source.getPath()));
            try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(source))) {
                zip.write(out.readAllBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ArgsName argsName = ArgsName.of(args);
        if (argsName.get("d") == null
                || argsName.get("e") == null
                || argsName.get("o") == null) {
            throw new IllegalArgumentException("Missing required parameters");
        }
        Path path = Paths.get(argsName.get("d"));
        if (!path.toFile().exists() || !path.toFile().isDirectory()) {
            throw new IllegalArgumentException("Directory not found");
        }
        List<Path> paths = Search.search(path, p -> !p.endsWith(argsName.get("e")));
        List<File> sources = paths.stream().map(Path::toFile).collect(Collectors.toList());
        packFiles(sources, Paths.get(argsName.get("o")).toFile());

        packSingleFile(
                new File("./pom.xml"),
                new File("./pom.zip")
        );
    }
}
