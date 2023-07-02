package data.functional;

import app.Play;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static app.Play.globalStage;
import static data.functional.forObjects.CONSTANTS.*;

public class Utilities {

    /**
     * @Url -> Name of the .fxml file. Transfer without .fxml.
     * @title -> the name of the window
     */
    public static void showWindow(String url, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(Play.class.getResource(url + ".fxml"));
            Scene secondScene = new Scene(loader.load());

            globalStage = new Stage();
            globalStage.setTitle(title);
            globalStage.getIcons().add(new Image("icon.jpg"));
            globalStage.setScene(secondScene);
            globalStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image getImage(String name) {
        switch (name) {
            case "GreenBase" -> {
                return new Image("GreenBase.png", MACRO_IMAGE_WIDTH, MACRO_IMAGE_HEIGHT, false, false);
            }
            case "RedBase" -> {
                return new Image("RedBase.png", MACRO_IMAGE_WIDTH, MACRO_IMAGE_HEIGHT, false, false);
            }
            case "Bunker" -> {
                return new Image("Bunker.png", MACRO_IMAGE_WIDTH, MACRO_IMAGE_HEIGHT, false, false);
            }
            case "Kamikaze" -> {
                return new Image("Kamikaze.png", MICRO_OBJECT_IMAGE_WIDTH, MICRO_OBJECT_IMAGE_HEIGHT, false, false);
            }
            case "SimpleSoldier" -> {
                return new Image("SimpleSoldier.png", MICRO_OBJECT_IMAGE_WIDTH, MICRO_OBJECT_IMAGE_HEIGHT, false, false);
            }
            case "SSO" -> {
                return new Image("SSO.png", MICRO_OBJECT_IMAGE_WIDTH, MICRO_OBJECT_IMAGE_HEIGHT, false, false);
            }
            case "Icon" -> {
                return new Image("icon.png");
            }
            case "Map" -> {
                return new Image("map.png", WORLD_WIDTH, WORLD_HEIGHT, false, false);
            }
            case "Fight" -> {
                return new Image("Fight.png", MICRO_FIGHT_IMAGE_WIDTH, MICRO_FIGHT_IMAGE_HEIGHT, false, false);
            }
            case "Monster" -> {
                return new Image("monster.png", MICRO_FIGHT_IMAGE_WIDTH * 5, MICRO_FIGHT_IMAGE_HEIGHT * 5, false, false);
            }
        }
        return null;
    }
}