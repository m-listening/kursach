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
import java.util.HashMap;
import java.util.Map;

public class Game extends Application {
    public static Group mainGroup = new Group();
    //true - green team, false - red team
    public static Map<Kamikaze, Boolean> warrior = new HashMap<>();
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
                        item.setRectangle();
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
            if (event.getCode().equals(KeyCode.S)) {
                TextArea textArea = new TextArea();
                textArea.setEditable(false);
                String str = "";
                for (Kamikaze item : warrior.keySet()) {
                    str += item + "\n";
                }
                textArea.setText(str);
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