package ru.job4j.io.find;

import ru.job4j.io.ArgsName;
import ru.job4j.io.SearchFiles;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class Find {
    public static void main(String[] args) throws IOException {
        String ls = System.lineSeparator();
        if (args.length < 4) {
            throw new IllegalArgumentException(
                    "Используйте: java -jar find.jar "
                            + "-d=c:/ -n=*.txt -t=mask -o=log.txt" + ls
                    + "-d - директория, в которой начинать поиск." + ls
                    + "-n - имя файла, маска, либо регулярное выражение." + ls
                    + "-t - тип поиска: mask искать по маске, name по полному"
                            + " совпадение имени, regex по регулярному выражению." + ls
                    + "-o - результат записать в файл."
            );
        }
        ArgsName argsName = ArgsName.of(args);
        Path path = Paths.get(argsName.get("d"));
        if (!path.toFile().exists() || !path.toFile().isDirectory()) {
            throw new IllegalArgumentException("Directory not found");
        }
        Predicate<Path> condition = null;
        if ("mask".equals(argsName.get("t"))) {
            String regex = "^"
                    + argsName.get("n")
                    .replaceAll("\\*", ".+")
                    .replace('?', '.')
                    + "$";
            condition = p -> p.toFile().getName().matches(regex);
        } else if ("name".equals(argsName.get("t"))) {
            condition = p -> p.toFile().getName().equals(argsName.get("n"));
        } else if ("regex".equals(argsName.get("t"))) {
            condition = p -> p.toFile().getName().matches(argsName.get("n"));
        }
        List<Path> result = find(path, condition);
        try (FileWriter out = new FileWriter(argsName.get("o"))) {
            for (Path p : result) {
                out.write(p.toString() + ls);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Path> find(Path root, Predicate<Path> condition) throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }
}
