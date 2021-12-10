package ru.job4j.io;

import java.io.FileInputStream;

public class EvenNumberFile {
    public static void main(String[] args) {
        try (FileInputStream input = new FileInputStream("even.txt")) {
            StringBuilder text = new StringBuilder();
            int read;
            while ((read = input.read()) != -1) {
                text.append((char) read);
            }
            for (String str : text.toString().split(System.lineSeparator())) {
                int num = Integer.parseInt(str);
                String message = num % 2 == 0 ? "четное" : "нечетное";
                System.out.println(num + " число " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
