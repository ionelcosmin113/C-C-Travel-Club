package proiect.fis.CC.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.*;



public class DeleteRoomsController {

    @FXML
    private ListView<String> roomList;

    @FXML
    private AnchorPane close;
    
    ArrayList<String> list = new ArrayList<String>();

    ArrayList<String> list1 = new ArrayList<String>();

    JSONParser parser1 = new JSONParser();

    JSONObject list11 = new JSONObject();

    private String username = "";

    private String x;

    public void initialize() {

        try (Reader reader = new FileReader("src/main/java/data/logininfo.json")) {
            JSONObject json = (JSONObject) parser1.parse(reader);
            list11 = json;
            username = list11.get("username").toString();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try (Reader reader = new FileReader("src/main/java/data/rooms.json")) {
            JSONArray jsonArray = (JSONArray) parser1.parse(reader);

            Iterator<JSONObject> it = jsonArray.iterator();
            while (it.hasNext()) {
                JSONObject obj = it.next();
                String a = obj.get("Number").toString();
                String b = obj.get("UserName").toString();
                if(b.equals(username))
                list.add(a);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        roomList.getItems().addAll(list);
    }


    @FXML
    void Delete() {
        Object y = "";
        x = roomList.getSelectionModel().getSelectedItem();
        try (Reader reader = new FileReader("src/main/java/data/rooms.json")) {
            JSONArray jsonArray = (JSONArray) parser1.parse(reader);
            Iterator<JSONObject> it = jsonArray.iterator();
            while (it.hasNext()) {
                JSONObject obj = it.next();
                y = obj;
                if (obj.get("Number").toString().equals(x)) {
                    break;
                } else {
                    continue;
                }
            }
            jsonArray.remove(y);
            list1.addAll(jsonArray);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try (FileWriter file = new FileWriter("src/main/java/data/rooms.json")) {
            file.write(list1.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int index = roomList.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            roomList.getItems().remove(index);}

        }

    @FXML
    void Close() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Manager.fxml"));
            close.getChildren().setAll(pane);
        } catch (Exception e) {
            System.out.println("Cant load the window");
        }

    }
    }
