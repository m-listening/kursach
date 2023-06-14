package app;

import data.world.World;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Play extends Application {
    public static Stage globalStage;
    public static World world;

    @Override
    public void start(Stage stage) {
        world = new World();
        world.initializeWithMicroObjects(100);
        stage.getIcons().add(new Image("icon.jpg"));
        stage.setTitle("Murder the King");
        stage.setScene(world.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}