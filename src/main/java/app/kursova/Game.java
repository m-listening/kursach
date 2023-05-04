package app.kursova;

import Methods.ForWarriors;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import micro_objects.Kamikaze;
import java.io.IOException;

import static Methods.Collections.warrior;
import static Methods.Collections.warriorsElect;

public class Game extends Application {
    public static Group mainGroup = new Group();
    //true - green team, false - red team

    public static Stage newStage;
    private static Timeline timeline;

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(mainGroup, 1280, 720);
        stage.setTitle("Game!");
        scene.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                Parent root;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Parameters.fxml"));
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene secondScene = new Scene(root);

                newStage = new Stage();
                newStage.setTitle("Creating warrior");
                newStage.setScene(secondScene);
                newStage.show();
            }

            if (event.getButton().equals(MouseButton.SECONDARY)) {
                for (Kamikaze item : warrior.keySet()) {
                    if (item.getGroup()
                            .boundsInParentProperty()
                            .get()
                            .contains(event.getX(), event.getY())) {
                        item.setElect();
                        if (item.isElect() && !warriorsElect.contains(item))
                            warriorsElect.add(item);
                        else warriorsElect.remove(item);
                        item.setRectangleColor();
                        break;
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
                Parent root;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangeParameters.fxml"));
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene secondScene = new Scene(root);

                newStage = new Stage();
                newStage.setTitle("Change warrior");
                newStage.setScene(secondScene);
                newStage.show();
            }
            if (event.getCode().equals(KeyCode.Q)) {
                for(Kamikaze item : warriorsElect){
                    try {
                        Kamikaze kamikaze = item.clone();
                        warrior.put(kamikaze,kamikaze.isTeam());
                        mainGroup.getChildren().add(kamikaze.getGroup());
                    } catch (CloneNotSupportedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (event.getCode().equals(KeyCode.S)) {
                TextArea textArea = new TextArea();
                textArea.setEditable(false);
                StringBuilder str = new StringBuilder();
                for (Kamikaze item : warrior.keySet()) {
                    str.append(item).append("\n");
                }
                textArea.setText(str.toString());
                StackPane root = new StackPane();
                root.getChildren().add(textArea);
                newStage = new Stage();
                Scene secondScene = new Scene(root, 400, 300);
                newStage.setScene(secondScene);
                newStage.setScene(secondScene);
                newStage.show();
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}