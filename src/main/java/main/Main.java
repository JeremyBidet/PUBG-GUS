package fr.whyt.pubg.main;

import fr.whyt.pubg.inout.ParserCIS;
import fr.whyt.pubg.inout.ParsingException;
import fr.whyt.pubg.inout.WriterCIS;
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
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        try {
            CustomInputSettings cis = ParserCIS.serialize("");
            System.out.println(WriterCIS.deserialize(cis));
        } catch (ParsingException e) {
            e.printStackTrace();
            Parent exception = FXMLLoader.load(ClassLoader.getSystemResource("exception.fxml"));
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}
