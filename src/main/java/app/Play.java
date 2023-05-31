package app;

import data.Methods.Utilities;
import data.Methods.World;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Play extends Application {
    public static Stage globalStage;
    public static double sceneSizeMinX = 640, sceneSizeMaxX;
    public static double sceneSizeMinY = 480, sceneSizeMaxY;
    public static World world;
    public static Scene scene;

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        world = new World();
        sceneSizeMaxX = world.view.getImage().getWidth() / 3;
        sceneSizeMaxY = world.view.getImage().getHeight() / 3;
        world.initialize();

        scene = new Scene(world.getWorldGroup(), sceneSizeMinX, sceneSizeMinY);
        stage.getIcons().add(new Image("icon.jpg"));
        stage.setTitle("Game!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}