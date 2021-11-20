package ru.job4j.set;

import ru.job4j.collection.SimpleArrayList;

import java.util.Iterator;
import java.util.Objects;

public class SimpleSet<T> implements Set<T> {
    private SimpleArrayList<T> set = new SimpleArrayList<>(10);

    @Override
    public boolean add(T value) {
        boolean rst  = !contains(value);
        if (rst) {
            set.add(value);
        }
        return rst;
    }

    @Override
    public boolean contains(T value) {
        boolean rst = false;
        for (T el : set) {
            if (Objects.equals(value, el)) {
                rst = true;
                break;
            }
        }
        return rst;
    }

    @Override
    public Iterator<T> iterator() {
        return set.iterator();
    }
}
