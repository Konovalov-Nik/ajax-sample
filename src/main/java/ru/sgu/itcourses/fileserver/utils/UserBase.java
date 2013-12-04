package ru.sgu.itcourses.fileserver.utils;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nikita Konovalov
 */
public class UserBase {
    private static Map<String, String> passwordBase = new HashMap<>();
    public static final String databaseFilePath = "WEB-INF/users.txt";

    public static void addUser(String login, String password) {
        passwordBase.put(login, password);
    }


    public static boolean authenticate(String login, String password) {
        return (passwordBase.containsKey(login) && passwordBase.get(login).equals(password));
    }

    public static void loadUsers(ServletContext servletContext) throws IOException {
        InputStream inputStream = servletContext.getResourceAsStream(databaseFilePath);
        String json = CharStreams.toString(new InputStreamReader(inputStream));
        JsonObject document = new Gson().fromJson(json, JsonObject.class);

        JsonArray users = (JsonArray) document.get("users");
        for (JsonElement user : users) {
            JsonObject castedUser = (JsonObject) user;
            addUser(castedUser.get("login").getAsString(),
                    castedUser.get("password").getAsString());
        }
    }
}
