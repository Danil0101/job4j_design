package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Analizy {
    public void unavailable(String source, String target) {
        List<String> unavailableList = new ArrayList<>();
        try (BufferedReader read = new BufferedReader(new FileReader(source))) {
            boolean isCurStatusUnavailable = false;
            String startTimeUnavailability = "";
            while (read.ready()) {
                String[] curLine = read.readLine().split(" ");
                if (!isCurStatusUnavailable
                        && ("400".equals(curLine[0])
                        || "500".equals(curLine[0]))) {
                    isCurStatusUnavailable = true;
                    startTimeUnavailability = curLine[1];
                } else if (isCurStatusUnavailable
                        && ("200".equals(curLine[0])
                        || "300".equals(curLine[0]))) {
                    unavailableList.add(startTimeUnavailability + ";"
                            + curLine[1]);
                    isCurStatusUnavailable = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (PrintWriter out = new PrintWriter(new FileOutputStream(target))) {
            unavailableList.forEach(out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        try (PrintWriter out = new PrintWriter(new FileOutputStream("unavailable.csv"))) {
            out.println("15:01:30;15:02:32");
            out.println("15:10:30;23:12:32");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
