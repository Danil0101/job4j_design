package ru.job4j.question;

import java.util.Objects;
import java.util.Set;

public class Analize {
    public static Info diff(Set<User> previous, Set<User> current) {
    Info result = new Info(current.size(), 0, previous.size());
        for (User curUser : current) {
            for (User preUser : previous) {
                if (curUser.getId() == preUser.getId()) {
                    if (!Objects.equals(curUser.getName(), preUser.getName())) {
                        result.setChanged(result.getChanged() + 1);
                    }
                    result.setAdded(result.getAdded() - 1);
                    result.setDeleted(result.getDeleted() - 1);
                }
            }
        }
        return result;
    }
}
