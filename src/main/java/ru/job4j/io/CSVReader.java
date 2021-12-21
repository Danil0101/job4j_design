package ru.job4j.io;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVReader {

    public static void handle(ArgsName argsName) {
        List<List<String>> data = new ArrayList<>();
        try (Scanner in = new Scanner(
                new FileReader(argsName.get("path")))) {
            while (in.hasNextLine()) {
                Scanner line = new Scanner(
                        in.nextLine())
                        .useDelimiter(argsName.get("delimiter"));
                data.add(new ArrayList<>());
                while (line.hasNext()) {
                    data.get(data.size() - 1).add(line.next());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> listData = filterData(data, argsName.get("filter"));
        if ("stdout".equals(argsName.get("out"))) {
            listData.forEach(System.out::println);
        } else {
            Path path = Paths.get(argsName.get("out"));
            try (PrintWriter out = new PrintWriter(new FileWriter(path.toFile()))) {
                listData.forEach(out::println);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static List<String> filterData(List<List<String>> data, String filters) {
        List<String> result = new ArrayList<>();
        String[] arFilter = filters.split(",");
        for (int i = 0; i < arFilter.length; i++) {
            int index = data.get(0).indexOf(arFilter[i]);
            StringBuilder stringBuilder;
            for (int j = 0; j < data.size(); j++) {
                if (i == 0) {
                    stringBuilder = new StringBuilder();
                } else {
                    stringBuilder = new StringBuilder(result.get(j));
                }
                String delimiter = i < arFilter.length - 1 ? ";" : "";
                stringBuilder.append(data.get(j).get(index)).append(delimiter);
                if (i == 0) {
                    result.add(stringBuilder.toString());
                } else {
                    result.set(j, stringBuilder.toString());
                }
            }
        }
        return result;
    }
}
