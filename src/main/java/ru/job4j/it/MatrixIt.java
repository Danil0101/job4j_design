package ru.job4j.it;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MatrixIt implements Iterator<Integer> {
    private final int[][] data;
    private int row = 0;
    private int column = 0;

    public MatrixIt(int[][] data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        boolean rsl = true;
        while (true) {
            if (row >= data.length) {
                rsl = false;
                break;
            }
            if (data[row].length == 0) {
                row++;
                continue;
            }
            if (column >= data[row].length) {
                row++;
                column = 0;
                continue;
            }
            break;
        }
        return rsl;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return data[row][column++];
    }
}
