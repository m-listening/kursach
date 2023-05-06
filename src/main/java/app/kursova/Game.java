package app.kursova;

import Methods.ForWarriors;
import Methods.Utilities;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import micro_objects.Kamikaze;

import java.io.IOException;

import static Methods.Collections.*;

public class Game extends Application {
    public static Group mainGroup = new Group();
    public static Stage newStage;
    private static Timeline timeline;

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(mainGroup, 1280, 720);
        stage.setTitle("Game!");
        scene.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                for (Kamikaze item : warrior.keySet()) {
                    if (item.getGroup().boundsInParentProperty().get().contains(event.getX(), event.getY())) {
                        item.setActive();
                        if (item.isActive()) warriorsActive.add(item);
                        else warriorsActive.remove(item);
                        return;
                    }
                }
                Utilities.showWindow("Parameters", "Parameters");
            }

            if (event.getButton().equals(MouseButton.SECONDARY)) {
                for (Kamikaze kamikaze : warrior.keySet()) {
                    if (kamikaze.getGroup().boundsInParentProperty().get().contains(event.getX(), event.getY())) {
                        if (warriorElect != null) {
                            warriorElect.setElect();
                            if (warriorElect == kamikaze) {
                                warriorElect = null;
                                return;
                            }
                        }
                        warriorElect = kamikaze;
                        warriorElect.setElect();
                        return;
                    }
                }
            }
        });
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.DELETE)) {
                ForWarriors.deleteWarrior();
            }
            if (event.getCode().equals(KeyCode.NUMPAD8)) {
                ForWarriors.moveIfElect(0, -10);
            }
            if (event.getCode().equals(KeyCode.NUMPAD4)) {
                ForWarriors.moveIfElect(-10, 0);
            }
            if (event.getCode().equals(KeyCode.NUMPAD6)) {
                ForWarriors.moveIfElect(10, 0);
            }
            if (event.getCode().equals(KeyCode.NUMPAD2)) {
                ForWarriors.moveIfElect(0, 10);
            }
            if (event.getCode().equals(KeyCode.C)) {
                Utilities.showWindow("ChangeParameters", "New parameters");
            }
            if (event.getCode().equals(KeyCode.Q)) {
                try {
                    if(warriorElect != null) {
                        Kamikaze kamikaze = warriorElect.clone();
                        warrior.put(kamikaze, kamikaze.isTeam());
                        mainGroup.getChildren().add(kamikaze.getGroup());
                    }
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (event.getCode().equals(KeyCode.S)) {
                newStage = new Stage();
                Scene secondScene = new Scene(ForWarriors.showWarriors(), 400, 300);
                newStage.setScene(secondScene);
                newStage.show();
            }
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                ForWarriors.turnOf();
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}