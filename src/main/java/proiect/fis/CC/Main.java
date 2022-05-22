package proiect.fis.CC;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    private static void copyFile(File src, File dest) throws IOException {
        FileUtils.copyFileToDirectory(src, dest);
    }
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
        primaryStage.setScene(new Scene(root, 600,520 ));
        primaryStage.show();
    }

    public static void main (String [] args){
        File from_data = new File("src\\main\\java\\data\\data.json").getAbsoluteFile();
        File from_city = new File("src\\main\\java\\data\\city.json").getAbsoluteFile();
        File from_login = new File("src\\main\\java\\data\\logininfo.json").getAbsoluteFile();
        File from_request = new File("src\\main\\java\\data\\request.json").getAbsoluteFile();
        File from_rooms = new File("src\\main\\java\\data\\rooms.json").getAbsoluteFile();

        File to = new File("target\\src\\main\\java\\data");

        try{
            copyFile(from_data, to);
            copyFile(from_city, to);
            copyFile(from_login, to);
            copyFile(from_request, to);
            copyFile(from_rooms, to);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        launch(args);
    }
}
