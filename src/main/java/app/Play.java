package app;

import data.world.World;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static data.functional.Utilities.*;

public class Play extends Application {
    public static Stage globalStage;
    public static World world;

    @Override
    public void start(Stage stage) {
        world = new World();
        stage.getIcons().add(new Image("icon.jpg"));
        stage.setTitle("Murder the King");
        stage.setScene(world.getScene());
        stage.show();
        showWindow("GenerateConfig", "Create config!");
    }

    public static void main(String[] args) {
        launch();
    }
}