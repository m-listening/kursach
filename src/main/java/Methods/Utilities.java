package Methods;

import app.kursova.Game;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static app.kursova.Game.newStage;

public class Utilities {
    /**
     * @Url -> Name of the .fxml file. Transfer without .fxml.
     * @title -> the name of the window
     */
    public static void showWindow(String url, String title) {
        Parent root;
        FXMLLoader loader = new FXMLLoader(Game.class.getResource(url + ".fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene secondScene = new Scene(root);

        newStage = new Stage();
        newStage.setTitle("Change warrior");
        newStage.setScene(secondScene);
        newStage.showAndWait();
    }
}
