package app.kursova;

import Methods.Utilities;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import macro_objects.Base;
import micro_objects.Kamikaze;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game extends Application {
    public static Group mainGroup = new Group();
    public static Stage globalStage;

    private static Timeline timeline;

    public static Kamikaze warriorElect;
    public final static Set<Base> bases = new HashSet<>();
    public final static List<Kamikaze> warriors = new ArrayList<>();
    public final static List<Kamikaze> warriorsActive = new ArrayList<>();

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        Scene scene = new Scene(mainGroup, 1280, 720);

        Utilities.initializeStartGame();

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
        timeline = new Timeline(new KeyFrame(Duration.millis(10)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        stage.setTitle("Game!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}