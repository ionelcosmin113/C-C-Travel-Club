package proiect.fis.CC.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import proiect.fis.CC.exceptions.UsernameAlreadyExistsException;
import proiect.fis.CC.model.User;

public class UserService {
    private static JSONArray usersList = new JSONArray();
    private static ArrayList<User> users = new ArrayList();

    public UserService() {
    }

    public static void loadUsersFromFile() {
        JSONParser parser = new JSONParser();

        try {
            FileReader readFile = new FileReader("src/main/java/data/data.json");
            BufferedReader read = new BufferedReader(readFile);
            Object p = parser.parse(read);
            if (p instanceof JSONArray) {
                usersList = (JSONArray)p;
            }
        } catch (IOException | ParseException var4) {
            var4.printStackTrace();
        }

    }

    public static void read() {
        JSONParser parser1 = new JSONParser();

        try {
            Reader reader = new FileReader("src/main/java/data/data.json");
            Throwable var2 = null;

            try {
                JSONArray jsonArray = (JSONArray)parser1.parse(reader);
                Iterator it = jsonArray.iterator();

                while(it.hasNext()) {
                    JSONObject obj = (JSONObject)it.next();
                    User user = new User(obj.get("username").toString(), obj.get("password").toString(), obj.get("email").toString(), obj.get("address").toString(), obj.get("hotelName").toString(), obj.get("role").toString());
                    users.add(user);
                }
            } catch (Throwable var16) {
                var2 = var16;
                throw var16;
            } finally {
                if (reader != null) {
                    if (var2 != null) {
                        try {
                            reader.close();
                        } catch (Throwable var15) {
                            var2.addSuppressed(var15);
                        }
                    } else {
                        reader.close();
                    }
                }

            }
        } catch (IOException var18) {
            var18.printStackTrace();
        } catch (ParseException var19) {
            var19.printStackTrace();
        }

    }

    public static void addUser(String username, String password, String email, String address, String hotelName, String role) throws UsernameAlreadyExistsException {
        loadUsersFromFile();
        read();
        checkUserDoesNotAlreadyExist(username);
        JSONObject user = new JSONObject();
        user.put("password", encodePassword(password));
        user.put("email", email);
        user.put("address", address);
        user.put("hotelName", hotelName);
        user.put("role", role);
        user.put("username", username);
        usersList.add(user);

        try {
            FileWriter file = new FileWriter("src/main/java/data/data.json");
            Throwable var8 = null;

            try {
                file.write(usersList.toString());
                file.flush();
            } catch (Throwable var18) {
                var8 = var18;
                throw var18;
            } finally {
                if (file != null) {
                    if (var8 != null) {
                        try {
                            file.close();
                        } catch (Throwable var17) {
                            var8.addSuppressed(var17);
                        }
                    } else {
                        file.close();
                    }
                }

            }
        } catch (IOException var20) {
            var20.printStackTrace();
        }

    }

    private static String encodePassword(String password) {
        String passwordToHash = password;
        String generatedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < bytes.length; ++i) {
                sb.append(Integer.toString((bytes[i] & 255) + 256, 16).substring(1));
            }

            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException var7) {
            var7.printStackTrace();
        }

        return generatedPassword;
    }

    public static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        Iterator var1 = users.iterator();

        User userIterator;
        do {
            if (!var1.hasNext()) {
                return;
            }

            userIterator = (User)var1.next();
        } while(!Objects.equals(username, userIterator.getUsername()));

        throw new UsernameAlreadyExistsException(username);
    }
}
