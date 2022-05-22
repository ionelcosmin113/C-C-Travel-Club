package proiect.fis.tripALME.services;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import proiect.fis.tripALME.controller.AddRoomsController;
import proiect.fis.tripALME.model.Rooms;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ManagerService  {

    private static JSONArray roomsList = new JSONArray();
    private static ArrayList<Rooms> rooms = new ArrayList<>();
    static List <Rooms> room;

    public static void  loadRoomsFromFile()  {

        Object p;
        JSONParser parser = new JSONParser();
        try{
            FileReader readFile = new FileReader("src/main/java/data/rooms.json");
            BufferedReader read = new BufferedReader(readFile);
            p = parser.parse(read);
            if(p instanceof JSONArray)
            {
                roomsList = (JSONArray)p;
                room = (JSONArray)p;
            }

        } catch (ParseException | IOException ex) {
            ex.printStackTrace();
        }

    }

    public static void addRooms(String number, String price, String description, String newcategory, String newdescription, String username){
        loadRoomsFromFile();
        JSONObject rooms = new JSONObject();
        rooms.put("Number", number);
        rooms.put("Price", price);
        rooms.put("Description", description);
        rooms.put("Category", newcategory);
        rooms.put("CategDes", newdescription);
        rooms.put("UserName", username);
        roomsList.add(rooms);


        try (FileWriter file = new FileWriter("src/main/java/data/rooms.json")) {

            file.write(roomsList.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
