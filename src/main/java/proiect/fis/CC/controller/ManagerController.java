package proiect.fis.tripALME.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
public class ManagerController {

    @FXML
    private AnchorPane go;

        @FXML
    void AddRooms() {
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("AddRooms.fxml"));
                go.getChildren().setAll(pane);
            } catch (Exception e) {
                System.out.println("Cant load the window");
            }
    }

    @FXML
    void DeleteRooms() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("DeleteRooms.fxml"));
            go.getChildren().setAll(pane);
        } catch (Exception e) {
            System.out.println("Cant load the window");
        }
    }

    @FXML
    void Logout() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
            go.getChildren().setAll(pane);
        } catch (Exception e) {
            System.out.println("Cant load the window");
        }
    }

    @FXML
    void Request() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Request.fxml"));
            go.getChildren().setAll(pane);
        } catch (Exception e) {
            System.out.println("Cant load the window");
        }
    }

}