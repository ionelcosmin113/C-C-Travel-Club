package proiect.fis.CC.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import proiect.fis.CC.services.ClientService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HotelSelectionController {

    @FXML
    private ListView<String> hotelList;

    @FXML
    private ListView<String> cityList;

    @FXML
    private Label city;

    @FXML
    private Label hotel;



    @FXML
    private AnchorPane client;

    private  JSONArray rooms = new JSONArray();
    private  String citySelected;
    private String errorMessage = "This city dosen't have any Hotel";

    public static String getHotelSelection() {
        return hotelSelection;
    }

    private static String hotelSelection;

    public void initialize() {
        Object p;
        JSONParser parser = new JSONParser();
        try {
            FileReader readFile = new FileReader("src/main/java/data/city.json");
            BufferedReader read = new BufferedReader(readFile);
            p = parser.parse(read);
            if (p instanceof JSONArray) {
                rooms = (JSONArray) p;
            }

        } catch (ParseException | IOException ex) {
            ex.printStackTrace();
        }

        cityList.getItems().addAll(rooms);
        cityList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


    }

    @FXML
    public void SelectCity() {

        hotelList.getItems().clear();
        if(cityList.getSelectionModel().getSelectedItem() == null){
            city.setText("You should select a city");
        }
        else {
            citySelected = cityList.getSelectionModel().getSelectedItem();


            JSONArray cityList = ClientService.displayHotels(citySelected);

            if (cityList.isEmpty()) {
                hotelList.getItems().add(errorMessage);
            } else {
                hotelList.getItems().addAll(cityList);
            }

        }
    }

    @FXML
    public void SelectHotel() {





        if(hotelList.getSelectionModel().getSelectedItem() == errorMessage || hotelList.getSelectionModel().getSelectedItem() == null) {
            hotel.setText("You should select a hotel");
        }
        else {
            hotelSelection = hotelList.getSelectionModel().getSelectedItem();

            try {
                AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("RoomSelection.fxml"));
                client.getChildren().setAll(pane);
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Cant load the window");
            }

        }
    }


    @FXML
    void Back() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Client.fxml"));
            client.getChildren().setAll(pane);
        } catch (Exception e) {
        }
    }
}
