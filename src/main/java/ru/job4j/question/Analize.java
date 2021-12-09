package ru.job4j.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Analize {
    public static Info diff(Set<User> previous, Set<User> current) {
        Info result = new Info(0, 0, previous.size());
        Map<Integer, User> map = new HashMap<>();
        for (User user : previous) {
            map.put(user.getId(), user);
        }
        for (User user : current) {
            User prevUser = map.put(user.getId(), user);
            if (prevUser == null) {
                result.setAdded(result.getAdded() + 1);
            } else {
                if (!Objects.equals(user.getName(), prevUser.getName())) {
                    result.setChanged(result.getChanged() + 1);
                }
                result.setDeleted(result.getDeleted() - 1);
            }
        }
        return result;
    }
}
