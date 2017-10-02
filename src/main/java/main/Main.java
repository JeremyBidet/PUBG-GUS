package fr.whyt.pubg.main;

import fr.whyt.pubg.inout.Parser;
import fr.whyt.pubg.inout.cis.CustomInputSettings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    public static Map<String, String> properties;

    static {
        properties = new HashMap<>();
        URL url = ClassLoader.getSystemResource("properties.config");
        //Object o = JSONFunctions.parse(url.getPath(), null);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        CustomInputSettings cis = Parser.processCustomInputSettings("");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
