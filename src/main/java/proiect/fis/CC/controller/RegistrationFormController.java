package proiect.fis.CC.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import proiect.fis.CC.exceptions.UsernameAlreadyExistsException;
import proiect.fis.CC.services.UserService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class RegistrationFormController {

    @FXML
     TextField username;

    @FXML
     TextField password;

    @FXML
     TextField email;

    @FXML
     ListView<String> address;

    @FXML
     TextField hotelName;

    @FXML
     Label usernameLabel;

    @FXML
     Label passwordLabel;

    @FXML
     Label emailLable;

    @FXML
     Label addressLable;

    @FXML
     Label hotelNameLable;

    @FXML
     Label registrationInfo;

    @FXML
     CheckBox id_Client;

    @FXML
     CheckBox id_Hotel;

    @FXML
    AnchorPane register;


    private boolean emptyField;
    private String role;

    private static JSONArray cities = new JSONArray();

    public void initialize() {
        Object p;
        JSONParser parser = new JSONParser();
        try {
            FileReader readFile = new FileReader("src/main/java/data/city.json");
            BufferedReader read = new BufferedReader(readFile);
            p = parser.parse(read);
            if (p instanceof JSONArray) {
                cities = (JSONArray) p;
            }

        } catch (ParseException | IOException ex) {
            ex.printStackTrace();
        }

        address.getItems().addAll(cities);
        address.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


    }
    @FXML
    public void Login(ActionEvent event) {

        emptyField = false;

        usernameLabel.setText("");
        passwordLabel.setText("");
        emailLable.setText("");
        addressLable.setText("");
        hotelNameLable.setText("Just for hotel managers");

        if (username.getText().isEmpty()) {
            usernameLabel.setText("Empty filed");
            emptyField = true;
        }
        if (password.getText().isEmpty()) {
            passwordLabel.setText("Empty filed");
            emptyField = true;
        }
        if (email.getText().isEmpty()) {
            emailLable.setText("Empty filed");
            emptyField = true;
        }
        if (address.getSelectionModel().getSelectedItems().isEmpty()) {
            addressLable.setText("Empty filed");
            emptyField = true;
        }
        if (hotelName.getText().isEmpty() && id_Hotel.isSelected()) {
            hotelNameLable.setText("Empty filed");
            emptyField = true;
        }

        if (emptyField) {
            registrationInfo.setText("Registration failed");
            return;
        }

        if (!id_Hotel.isSelected() && !id_Client.isSelected()) {
            registrationInfo.setText("Choose one role");
            return;
        }

        if (id_Hotel.isSelected() && id_Client.isSelected()) {
            registrationInfo.setText("Choose just one role");
            return;
        }

        if (id_Client.isSelected())
            role = "client";
        if (id_Hotel.isSelected())
            role = "manager";
        try {
            UserService.addUser(username.getText(), password.getText(), email.getText(), address.getSelectionModel().getSelectedItem(), hotelName.getText(), role);
            registrationInfo.setText("Registration successful");
        } catch (UsernameAlreadyExistsException e) {
            registrationInfo.setText(e.getMessage());
        }
    }

    public void Back() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
            register.getChildren().setAll(pane);
        } catch (Exception e) {
            System.out.println("Cant load the window");
        }


    }
}


