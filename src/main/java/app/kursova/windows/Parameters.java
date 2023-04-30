package app.kursova.windows;

import app.kursova.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import micro_objects.Kamikaze;

import java.io.FileNotFoundException;

public class Parameters {

    @FXML
    private Button createWarrior_button;

    @FXML
    private TextField setHealth_field;

    @FXML
    private TextField setName_field;

    @FXML
    private TextField setX_field;

    @FXML
    private TextField setY_field;

    @FXML
    private VBox window_params;

    @FXML
    void createNewWarrior() throws FileNotFoundException {
        String name = setName_field.getText();
        int health = Integer.parseInt(setHealth_field.getText());
        double x = Double.parseDouble(setX_field.getText());
        double y = Double.parseDouble(setY_field.getText());
        Kamikaze kamikaze = new Kamikaze(name, health, x, y);
        Game.warrior.put(kamikaze, true);
        Game.mainGroup.getChildren().add(kamikaze.getGroup());
        Game.newStage.close();
    }

}
