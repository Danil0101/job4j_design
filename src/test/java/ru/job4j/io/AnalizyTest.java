package ru.job4j.io;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class AnalizyTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void unavailable() throws IOException {
        File source = folder.newFile("source.txt");
        File target = folder.newFile("target.txt");
        String ls = System.lineSeparator();
        try (FileWriter out = new FileWriter(source)) {
            out.write(
                    "200 10:56:01" + ls
                    + "500 10:57:01" + ls
                    + "400 10:58:01" + ls
                    + "200 10:59:01" + ls
                    + "500 11:01:02" + ls
                    + "200 11:02:02"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Analizy().unavailable(source.getAbsolutePath(),
                target.getAbsolutePath());
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(target))) {
            in.lines().forEach(str -> rsl.append(str).append(ls));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertThat(rsl.toString(), is("10:57:01;10:59:01" + ls
                + "11:01:02;11:02:02" + ls));
    }
}