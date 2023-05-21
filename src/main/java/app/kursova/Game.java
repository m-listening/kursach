package app.kursova;

import Methods.Utilities;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Game extends Application {
    public static Stage globalStage;

    public static World world;

    @Override
    public void start(Stage stage) throws FileNotFoundException, CloneNotSupportedException {
        world = new World();
        world.initialize();

        Scene scene = new Scene(world.getWorldGroup(), 1280, 720);
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

        stage.setTitle("Game!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}