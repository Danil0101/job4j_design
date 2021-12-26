package ru.job4j.serialization;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "contact")
public class Roof {

    @XmlAttribute
    private String color;

    public Roof() {
    }

    public Roof(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Roof{"
                + " color='" + color + '\''
                + '}';
    }
}
