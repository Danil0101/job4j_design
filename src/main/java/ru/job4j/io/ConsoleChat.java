package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsoleChat {
    private final String path;
    private final String botAnswers;
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    public void run() {
        List<String> answers = readPhrases();
        List<String> log = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String command = null;
        boolean silence = false;
        while (!OUT.equals(command)) {
            command = scanner.nextLine();
            log.add(command);
            if (STOP.equals(command)) {
                silence = true;
            } else if (CONTINUE.equals(command)) {
                silence = false;
            }
            if (!silence && !OUT.equals(command)) {
                log.add(answers.get((int) (Math.random() * answers.size())));
                System.out.println(log.get(log.size() - 1));
            }
        }
        saveLog(log);
    }

    private List<String> readPhrases() {
        Path path = Paths.get(botAnswers);
        List<String> result = null;
        if (!path.toFile().exists()) {
            throw new IllegalArgumentException("botAnswers not found");
        }
        try (BufferedReader in = new BufferedReader(
                new FileReader(path.toFile(), StandardCharsets.UTF_8))) {
            result = in.lines().collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void saveLog(List<String> log) {
        try (PrintWriter out = new PrintWriter(
                new FileWriter(path, StandardCharsets.UTF_8))) {
            log.forEach(out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConsoleChat cc = new ConsoleChat("./data/log.txt", "./data/botAnswers.txt");
        cc.run();
    }
}
