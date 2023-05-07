package Methods;

import app.kursova.Game;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import macro_objects.Bunker;
import macro_objects.GreenBase;
import macro_objects.RedBase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static app.kursova.Game.mainGroup;
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

    /**
     * @param lvl -> 1 -> kamikaze, 2 -> simple soldier, 3-> SSO
     * @return -> new Image, or null if lvl != 1,2,3
     */
    public static Image lvlImage(int lvl) throws FileNotFoundException {
        if (lvl == 1) {
            return new Image(new FileInputStream("src/images/kamikaze.png"), 50, 50, false, false);
        }
        if (lvl == 2) {
            return new Image(new FileInputStream("src/images/simpleSoldier.png"), 50, 50, false, false);
        }
        if (lvl == 3) {
            return new Image(new FileInputStream("src/images/SSO.png"), 50, 50, false, false);
        }
        return null;
    }

    public static void initializeStartGame() throws FileNotFoundException {
        Bunker bunker = new Bunker();
        GreenBase greenBase = new GreenBase();
        RedBase redBase = new RedBase();
        mainGroup.getChildren().addAll(
                bunker.getGroup(),
                greenBase.getGroup(),
                redBase.getGroup());

    }
}
