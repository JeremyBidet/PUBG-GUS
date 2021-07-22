package fr.whyt.pubg.main;

import fr.whyt.pubg.deprecated.parser.IniFile;
import fr.whyt.pubg.deprecated.parser.data.CustomInputSettings;
import fr.whyt.pubg.deprecated.parser.exceptions.ParsingException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    public static final Map<String, String> properties;

    static {
        properties = new HashMap<>();
        final URL url = ClassLoader.getSystemResource("properties.config");
        //Object o = JSONFunctions.parse(url.getPath(), null);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        try {
            final CustomInputSettings cis = IniFile.deserialize(CustomInputSettings.class, "test");
            System.out.println(cis.serialize());
        } catch (ParsingException e) {
            e.printStackTrace();
            final Parent exception = FXMLLoader.load(ClassLoader.getSystemResource("exception.fxml"));
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}
