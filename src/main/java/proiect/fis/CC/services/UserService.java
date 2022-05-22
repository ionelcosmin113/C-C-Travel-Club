package proiect.fis.tripALME.services;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import proiect.fis.tripALME.exceptions.UsernameAlreadyExistsException;
import proiect.fis.tripALME.model.User;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class UserService {

    private static  JSONArray usersList = new JSONArray();
    private static ArrayList<User> users = new ArrayList<>();


    public static void  loadUsersFromFile()  {

        Object p;
        JSONParser parser = new JSONParser();
        try{
            FileReader readFile = new FileReader("src/main/java/data/data.json");
            BufferedReader read = new BufferedReader(readFile);
            p = parser.parse(read);
            if(p instanceof JSONArray)
            {
                usersList =(JSONArray)p;
            }

        } catch (ParseException | IOException ex) {
            ex.printStackTrace();
        }

        }

    public static void read () {

        JSONParser parser1 = new JSONParser();

        try (Reader reader = new FileReader("src/main/java/data/data.json")) {
            JSONArray jsonArray = (JSONArray) parser1.parse(reader);

            Iterator<JSONObject> it = jsonArray.iterator();
            while (it.hasNext()) {
                JSONObject obj = it.next();
                User user = new User(obj.get("username").toString(),obj.get("password").toString(),obj.get("email").toString(), obj.get("address").toString(), obj.get("hotelName").toString(), obj.get("role").toString());
                users.add(user);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static void  addUser(String username, String password, String email,  String address, String hotelName, String role) throws UsernameAlreadyExistsException  {

        loadUsersFromFile();   //Incarcam toti useri din fisierul json intr-o lista pentru a nu ii pierde
        read();                //Incarcam useri intr-un ArrayList
        //Salvam continutul deja existent in  fisier pentru a nu se pierde


        checkUserDoesNotAlreadyExist(username);
        //Punem un nou obiect in fisier
        JSONObject user = new JSONObject();

        user.put("password",encodePassword(password));
        user.put("email",email);
        user.put("address", address);
        user.put("hotelName", hotelName);
        user.put("role", role);
        user.put("username",username);

        usersList.add(user);


        try (FileWriter file = new FileWriter("src/main/java/data/data.json")) {

            file.write(usersList.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String encodePassword(String password) {
        String passwordToHash = password;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch ( NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static void checkUserDoesNotAlreadyExist( String username)throws UsernameAlreadyExistsException {

        for (User userIterator : users) {
            if (Objects.equals(username, userIterator.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }
    }


