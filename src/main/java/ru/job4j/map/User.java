package ru.job4j.map;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private int children;
    private Calendar birthday;

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    public static void main(String[] args) {
        User user1 = new User("John", 2,
                new GregorianCalendar(2000, Calendar.JANUARY, 1));
        User user2 = new User("John", 2,
                new GregorianCalendar(2000, Calendar.JANUARY, 1));
        Map<User, Object> map = new HashMap<>();
        map.put(user1, new Object());
        map.put(user2, new Object());
        System.out.println(map);

        int hashU1 = user1.hashCode() ^ user1.hashCode() >>> 16;
        int hashU2 = user2.hashCode() ^ user2.hashCode() >>> 16;
        int index1 = hashU1 & 15;
        int index2 = hashU2 & 15;
        System.out.println("hashCode1: "
                + user1.hashCode()
                + " hash1: " + hashU1
                + " index1: " + index1);
        System.out.println("hashCode2: "
                + user2.hashCode()
                + " hash2: " + hashU2
                + " index2: " + index2);
    }
}
