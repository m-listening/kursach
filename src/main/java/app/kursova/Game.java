package app.kursova;

import Methods.Utilities;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import macro_objects.Base;
import micro_objects.Kamikaze;

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
    public final static Set<Kamikaze> warriors = new HashSet<>();
    public final static List<Kamikaze> warriorsActive = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(mainGroup, 1280, 720);

        Utilities.initializeStartGame();
        scene.setOnMouseClicked(Utilities::mousePressedHandler);
        scene.setOnKeyPressed(Utilities::keyPressedHandler);

        stage.setTitle("Game!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}