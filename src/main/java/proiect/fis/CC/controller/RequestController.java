package proiect.fis.CC.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.controlsfx.control.Notifications;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class RequestController {

    @FXML
    private ListView<String> List;

    @FXML
    private ListView<String> List1;

    @FXML
    private AnchorPane go;

    @FXML
    private Text message;

    @FXML
    private TextField check;

    @FXML
    private Text mess;


    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> list1 = new ArrayList<String>();
    JSONParser parser1 = new JSONParser();
    private String username = "";
    private String user = "";
    private String hotel = "";
    private String data = "";
    private String hotel1 = "";
    private JSONArray requests = new JSONArray();

    public void initialize() {
        JSONObject list11 = new JSONObject();
        try (Reader reader = new FileReader("src/main/java/data/logininfo.json")) {
            JSONObject json = (JSONObject) parser1.parse(reader);
            list11 = json;
            username = list11.get("username").toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try (Reader reader = new FileReader("src/main/java/data/data.json")) {
            JSONArray jsonArray = (JSONArray) parser1.parse(reader);
            Iterator<JSONObject> it = jsonArray.iterator();
            while (it.hasNext()) {
                JSONObject obj = it.next();
                user = obj.get("username").toString();
                if (user.equals(username))
                    hotel = obj.get("hotelName").toString();
            }
        } catch (IOException e) {
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
                if (b.equals(username))
                    list.add(a);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List.getItems().addAll(list);

        try (Reader reader = new FileReader("src/main/java/data/request.json")) {
            JSONArray jsonArray = (JSONArray) parser1.parse(reader);
            requests = jsonArray;
            Iterator<JSONObject> it = jsonArray.iterator();
            while (it.hasNext()) {
                JSONObject obj = it.next();
                String x = obj.get("request").toString();
                String y = obj.get("hotel").toString();
                String z = obj.get("status").toString();
                if (z.equals("Pending"))
                    if (y.equals(hotel))
                        list1.add(x);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List1.getItems().addAll(list1);
    }


    @FXML
    void Check() {

        String x = check.getText();
        try (Reader reader = new FileReader("src/main/java/data/request.json")) {
            JSONArray jsonArray = (JSONArray) parser1.parse(reader);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject o = (JSONObject) jsonArray.get(i);
                if (o.get("request").equals(x)) {
                    message.setText("The room is available");
                    break;
                } else {
                    message.setText("The room is not available");
                    continue;
                }

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void Accept() {
        String x = List1.getSelectionModel().getSelectedItem();
        Iterator<JSONObject> it = requests.iterator();
        while (it.hasNext()) {
            JSONObject obj = it.next();
            if (obj.get("request").equals(x)) {
                obj.put("status", "Accepted");
            }
        }
        try (FileWriter file = new FileWriter("src/main/java/data/request.json")) {
            file.write(requests.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int index = List1.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            List1.getItems().remove(index);
        }
    }


    @FXML
    void Reject() {
        String x = List1.getSelectionModel().getSelectedItem();
        Iterator<JSONObject> it = requests.iterator();
        while (it.hasNext()) {
            JSONObject obj = it.next();
            if (obj.get("request").equals(x)) {
                obj.put("status", "Rejected");
            }
        }
        try (FileWriter file = new FileWriter("src/main/java/data/request.json")) {
            file.write(requests.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int index = List1.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            List1.getItems().remove(index);
        }
    }


    @FXML
    void Notification() throws java.text.ParseException {

        SimpleDateFormat sdf;
        Date strDate = null;

        try (Reader reader = new FileReader("src/main/java/data/request.json")) {
            JSONArray json = (JSONArray) parser1.parse(reader);
            Iterator<JSONObject> it = json.iterator();

            while (it.hasNext()) {
                JSONObject obj = it.next();
                data = obj.get("checkout").toString();
                hotel1 = obj.get("hotel").toString();

                if (hotel1.equals(hotel)) {
                    sdf = new SimpleDateFormat("dd.MM.yyyy");
                    strDate = sdf.parse(data);

                    if (new Date().after(strDate)) {
                        obj.replace("checkout","33.33.3333");
                        mess.setText("You have notifications!");
                        Notifications.create()
                                .title("This room can be used again")
                                .text(obj.get("request").toString())
                                .position(Pos.TOP_CENTER)
                                .showInformation();
                        break;
                    } else {
                        continue;
                    }
                }
            }

                try (FileWriter file = new FileWriter("src/main/java/data/request.json")) {
                    file.write(json.toString());
                    file.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    void Back() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Manager.fxml"));
            go.getChildren().setAll(pane);
        } catch (Exception e) {
            System.out.println("Cant load the window");
        }
    }
}
