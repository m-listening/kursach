package app;

import data.Methods.Utilities;
import data.World.World;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Play extends Application {
    public static Stage globalStage;
    public static double sceneSizeMinX = 720;
    public static double sceneSizeMinY = 480 ;
    public static World world;
    public static Scene scene;

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        world = new World();
        world.initialize();
        scene = new Scene(world.getGame(), sceneSizeMinX, sceneSizeMinY);
        scene.setOnMouseClicked(event -> {
            try {
                Utilities.mousePressedHandler(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        scene.setOnKeyPressed(event -> {
            try {
                Utilities.keyPressedHandler(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        stage.getIcons().add(new Image("icon.jpg"));
        stage.setTitle("Game!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}