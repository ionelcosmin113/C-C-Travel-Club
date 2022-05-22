package proiect.fis.CC.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;


public class LoginController {

    @FXML
    private TextField usernameF;

    @FXML
    private PasswordField passwordF;

    @FXML
    private Text loginMessage;


    @FXML
    private AnchorPane go;

    @FXML
    void LoginButton() {


        String user = usernameF.getText();
        String pass = passwordF.getText();
        String manager = "manager";
        String client = "client";
        JSONParser parser = new JSONParser();
        JSONObject compare = new JSONObject();

        try (FileWriter file = new FileWriter("src/main/java/data/logininfo.json")){
            JSONObject loginInfo = new JSONObject();
            loginInfo.put("username",user);
            loginInfo.put("password",HashPassword(pass));
            file.write(loginInfo.toJSONString());
            file.flush();
        }  catch (IOException e) {
        e.printStackTrace();
        }

        if (user == null || user.isEmpty()) {
            loginMessage.setText("Please type in a username!");
            return;
        }

        if (pass == null || pass.isEmpty()) {
            loginMessage.setText("Password cannot be empty!");
            return;
        }

        try (Reader reader = new FileReader("src/main/java/data/data.json")) {
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            compare.put("usernameF", user);
            compare.put("passwordF", HashPassword(pass));
            compare.put("RoleM", manager);
            compare.put("RoleC", client);
            Iterator<JSONObject> it = jsonArray.iterator();
            while (it.hasNext()) {
                JSONObject obj = it.next();
                if (obj.get("username").equals(compare.get("usernameF")) && obj.get("role").equals(compare.get("RoleM")) && obj.get("password").equals(compare.get("passwordF"))) {
                    loginMessage.setText("Login as a manager!");
                    try {

                        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Manager.fxml"));
                        go.getChildren().setAll(pane);

                    } catch (Exception e) {
                        System.out.println("Cant load the window");
                    }
                }
                if (obj.get("username").equals(compare.get("usernameF")) && obj.get("role").equals(compare.get("RoleC")) && obj.get("password").equals(compare.get("passwordF"))) {
                    loginMessage.setText("Login as a client!");
                    try {
                        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Client.fxml"));
                        go.getChildren().setAll(pane);
                    } catch (Exception e) {
                        System.out.println("Cant load the window");}
                }
                if (!obj.get("username").equals(compare.get("usernameF")) && obj.get("password").equals(compare.get("passwordF"))) {
                    loginMessage.setText("Incorrect login, reenter the credentials!");
                    break;
                }
                if (obj.get("username").equals(compare.get("usernameF")) && !obj.get("password").equals(compare.get("passwordF"))) {
                    loginMessage.setText("Incorrect login, reenter the credentials!");
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static String HashPassword(String pass) {
        String passwordToHash = pass;
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public void Register(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Registration.fxml"));
            go.getChildren().setAll(pane);
        } catch (Exception e) {
            System.out.println("Cant load the window");
        }
    }
}
