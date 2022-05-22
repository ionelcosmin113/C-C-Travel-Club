package proiect.fis.CC.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class ClientController {

    @FXML
    private AnchorPane client;

    @FXML
    private Label requestStatus;



    private JSONArray requests;

    private String requestString = "";

    private String username;

    private JSONObject user;

    private JSONParser parseruser = new JSONParser();

    public void initialize(){
        try (Reader reader = new FileReader("src/main/java/data/logininfo.json")) {
            JSONObject json = (JSONObject) parseruser.parse(reader);
            user = json;
            username = user.get("username").toString();
           // System.out.println(username);
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Object obj;
        JSONParser parser = new JSONParser();
        try {
            FileReader readFile = new FileReader("src/main/java/data/request.json");
            BufferedReader read = new BufferedReader(readFile);
            obj = parser.parse(read);
            if (obj instanceof JSONArray) {
                requests = (JSONArray) obj;
            }

        } catch (ParseException | IOException ex) {
            ex.printStackTrace();
        }
        for (int i=0;i<requests.size();i++) {


            JSONObject o=(JSONObject) requests.get(i);

            if(username.equals(o.get("username"))) {
                requestString = requestString + "Room: " + o.get("request").toString() + "Status: " + o.get("status").toString() + "\n";
            }
        }
        requestStatus.setText(requestString);

    }
    @FXML
    void SearchCity() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("HotelSelection.fxml"));
            client.getChildren().setAll(pane);
        } catch (Exception e) {
            System.out.println("Cant load the window");
        }


    }

    @FXML
    void Logout() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
            client.getChildren().setAll(pane);
        } catch (Exception e) {
            System.out.println("Cant load the window");
        }


    }
}
