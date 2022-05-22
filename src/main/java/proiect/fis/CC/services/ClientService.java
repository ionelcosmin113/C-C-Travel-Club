package proiect.fis.CC.services;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import proiect.fis.CC.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class ClientService extends UserService {

    private static ArrayList<User> clients = new ArrayList<>();
    public static JSONArray requests = new JSONArray();

    public static void read() {

        JSONParser parser1 = new JSONParser();

        try (Reader reader = new FileReader("src/main/java/data/data.json")) {
            JSONArray jsonArray = (JSONArray) parser1.parse(reader);

            Iterator<JSONObject> it = jsonArray.iterator();
            while (it.hasNext()) {
                JSONObject obj = it.next();
                User user = new User(obj.get("username").toString(), obj.get("password").toString(), obj.get("email").toString(), obj.get("address").toString(), obj.get("hotelName").toString(), obj.get("role").toString());
                clients.add(user);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static JSONArray displayHotels(String city) {
    read();

        JSONArray hotels = new JSONArray();
        for (User userIterator : clients) {
            if (Objects.equals(city, userIterator.getAddress()) && Objects.equals("manager", userIterator.getRole()))
                hotels.add(userIterator.getHotelName());
        }

        clients.clear();
            return hotels;

    }


    public static void  loadUsersFromFile()  {

        Object p;
        JSONParser parser = new JSONParser();
        try{
            FileReader readFile = new FileReader("src/main/java/data/request.json");
            BufferedReader read = new BufferedReader(readFile);
            p = parser.parse(read);
            if(p instanceof JSONArray)
            {
                requests =(JSONArray)p;
            }

        } catch (ParseException | IOException ex) {
            ex.printStackTrace();
        }

    }
    public static boolean writeRequest(String request, String checkin, String checkout, String username, String hotel){

        loadUsersFromFile();
        JSONObject userRequest = new JSONObject();
        userRequest.put("request",request);
        userRequest.put("checkin", checkin);
        userRequest.put("checkout", checkout);
        userRequest.put("status", "Pending");
        userRequest.put("username", username);
        userRequest.put("hotel", hotel);
        requests.add(userRequest);


        try (FileWriter file = new FileWriter("src/main/java/data/request.json")) {


            file.write(requests.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
