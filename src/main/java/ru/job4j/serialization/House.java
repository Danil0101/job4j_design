package ru.job4j.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;

public class House {
    private boolean built;
    private int floors;
    private String series;
    private Roof roof;
    private String[] properties;

    public House(boolean built, int floors, String series, Roof roof, String[] properties) {
        this.built = built;
        this.floors = floors;
        this.series = series;
        this.roof = roof;
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "House{"
                + " built=" + built
                + ", floors=" + floors
                + ", series='" + series + '\''
                + ", roof=" + roof
                + ", properties=" + Arrays.toString(properties)
                + '}';
    }

    public static void main(String[] args) {
        final House house = new House(
                true, 9, "A100",
                new Roof("gray"), new String[] {"p1", "p2"});
        final Gson gson = new GsonBuilder().create();
        String jsonStr = gson.toJson(house);
        System.out.println(jsonStr);
        final House houseFromJson = gson.fromJson(jsonStr, House.class);
        System.out.println(houseFromJson);
    }
}
