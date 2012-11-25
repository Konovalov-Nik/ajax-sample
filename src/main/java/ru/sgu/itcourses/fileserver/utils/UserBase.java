package ru.sgu.itcourses.fileserver.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nikita Konovalov
 */
public class UserBase {
    private static Map<String, String> passwordBase = new HashMap<>();
    private static Map<String, String> pathBase = new HashMap<>();

    public static void addUser(String login, String password, String path) {
        passwordBase.put(login, password);
        pathBase.put(login, path);
    }

    public static String getHomePathForUser(String login) {
        return pathBase.get(login);
    }

    public static boolean authenticate(String login, String password) {
        return (passwordBase.containsKey(login) && passwordBase.get(login).equals(password));
    }
}
