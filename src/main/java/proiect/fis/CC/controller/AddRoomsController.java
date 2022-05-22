package proiect.fis.tripALME.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import proiect.fis.tripALME.services.ManagerService;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddRoomsController implements Initializable {


    @FXML
    private TextField newcategory;

    @FXML
    private TextField number;

    @FXML
    private TextField price;

    @FXML
    private TextField description;

    @FXML
    private Text mess;

    @FXML
    private ChoiceBox<String> category;

    @FXML
    private Text screen;


    @FXML
    private TextField newdes;

    @FXML
    private AnchorPane back;



    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }

    void loadData() {
        List<String> list = new ArrayList<String>();

        list.removeAll(list);
        String a = "Single";
        String b = "Double";
        String c = "Triple";
        String d = "Quad";
        String e = "Twin";
        String f = "Double-double";
        String g = "None";
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);
        list.add(f);
        list.add(g);
        category.getItems().addAll(list);
    }

    @FXML
    void Done() {


        if (number.getText().isEmpty()) {
            mess.setText("Empty filed");
            return;
        }
        if (price.getText().isEmpty()) {
            mess.setText("Empty filed");
            return;
        }
        if (description.getText().isEmpty()) {
            mess.setText("Empty filed");
            return;
        }
        String x = category.getValue();
        if(newdes.getText().isEmpty() && x == null && !newcategory.getText().isEmpty()){
            mess.setText("You have not entered a new description.");
            return;
        }
        if(newdes.getText().isEmpty() && x == "None" && !newcategory.getText().isEmpty()){
            mess.setText("You have not entered a new description.");
            return;
        }

        if(x == "None" && newcategory.getText().isEmpty()){
            mess.setText("Please select a category from the list or enter a new one.");
            return;
        }

        JSONParser parser1 = new JSONParser();
        JSONObject list = new JSONObject();
        String username = "";

        try (Reader reader = new FileReader("src/main/java/data/logininfo.json")) {
            JSONObject jsonArray = (JSONObject) parser1.parse(reader);
                 list = jsonArray;
                 username = list.get("username").toString();
            }
         catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (!newcategory.getText().isEmpty() && x == null) {
            try {
                ManagerService.addRooms(number.getText(), price.getText(), description.getText(), newcategory.getText(), newdes.getText(), username);
                mess.setText("Successfully completed");
            } catch (Exception e) {
                System.out.println("error");
            }
            }

        if (newcategory.getText().isEmpty() && x != null) {
            try {
                ManagerService.addRooms(number.getText(), price.getText(), description.getText(), x, screen.getText(),username);
                mess.setText("Successfully completed");
            } catch (Exception e) {
                System.out.println("error");
            }
        }

        if (newcategory.getText().isEmpty() && x == null) {
            mess.setText("Please select a category from the list or enter a new one.");
        }


        if (!newcategory.getText().isEmpty() && x != null) {
            mess.setText("The room can have only one category.");
        }


        if (!newcategory.getText().isEmpty() && x == "None") {
            try {
                ManagerService.addRooms(number.getText(), price.getText(), description.getText(), newcategory.getText(), newdes.getText(), username);
                mess.setText("Successfully completed");
            } catch (Exception e) {
                System.out.println("error");
            }
        }
    }

    @FXML
    void Display() {

        String cate = category.getValue();
        if(cate == null){
            screen.setText("Please select an item");
        }
        if(cate == "Single"){
            screen.setText("A room assigned to one person. May have one or more beds.");
        }
        if(cate == "Double"){
            screen.setText("A room assigned to two people. May have one or more beds.");
        }
        if(cate == "Triple"){
            screen.setText("A room assigned to three people. May have two or more beds.");
        }
        if(cate == "Quad"){
            screen.setText("A room assigned to four people. May have two or more beds");
        }
        if(cate == "Twin"){
            screen.setText("A room with two beds. May be occupied by one or more people.");
        }
        if(cate == "Double-double"){
            screen.setText("A room with two double (or perhaps queen) beds. May be occupied by one or more people.");
        } }

    @FXML
    void Back() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Manager.fxml"));
            back.getChildren().setAll(pane);
        } catch (Exception e) {
            System.out.println("Cant load the window");
        }
    }
    }












