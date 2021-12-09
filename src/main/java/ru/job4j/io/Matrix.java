package ru.job4j.io;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public class Matrix {
    public static int[][] multiple(int size) {
        int[][] result = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = (i + 1) * (j + 1);
            }
        }
        return result;
    }

    public static void writeMultipleToFile(String path) {
        int[][] multiple = multiple(10);
        try (FileOutputStream fos = new FileOutputStream(path)) {
            for (int i = 0; i < multiple.length; i++) {
                for (int j = 0; j < multiple[i].length; j++) {
                    String str = multiple[i][j] + " ";
                    fos.write(str.getBytes());
                }
                fos.write(System.lineSeparator().getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        writeMultipleToFile("file.txt");
    }
}
