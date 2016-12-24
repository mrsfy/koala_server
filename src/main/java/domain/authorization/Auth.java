package domain.authorization;

import data.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by mrsfy on 21-Dec-16.
 */
public class Auth {

    private static Map<UUID, User> users = new HashMap<>();


    public static User getUser(UUID uuid) {
        return users.get(uuid);
    }

    public static void setUser(UUID uuid, User user) {
        users.put(uuid, user);
    }

    public static void removeUser(UUID uuid) {
        users.remove(uuid);
    }

}
