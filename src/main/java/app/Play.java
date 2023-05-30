package app;

import data.Methods.Utilities;
import data.Methods.World;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Play extends Application {
    public static Stage globalStage;
    public static double sceneSizeMinX = 640, sceneSizeMaxX = 2160;
    public static double sceneSizeMinY = 480, sceneSizeMaxY = 1680;
    public static World world;

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        world = new World();
        world.initialize();

        Scene scene = new Scene(world.getWorldGroup(), sceneSizeMinX, sceneSizeMinY);
        scene.setOnMouseClicked(event -> {
            try {
                Utilities.mousePressedHandler(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        scene.setOnMouseClicked(event -> {
            try {
                Utilities.mousePressedHandler(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        scene.setOnKeyPressed(Utilities::keyPressedHandler);
        stage.getIcons().add(new Image("icon.jpg"));
        stage.setTitle("Game!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}