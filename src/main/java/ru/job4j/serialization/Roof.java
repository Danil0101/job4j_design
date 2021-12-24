package ru.job4j.serialization;

public class Roof {
    private final String color;

    public Roof(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Roof{"
                + " color='" + color + '\''
                + '}';
    }
}
