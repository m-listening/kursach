package app.kursova.windows;

import Methods.ForWarriors;
import app.kursova.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import micro_objects.Kamikaze;

public class ChangeParameters {

    @FXML
    private Button changeParameters_button;

    @FXML
    private TextField setHealth_field;

    @FXML
    private TextField setName_field;

    @FXML
    private TextField setX_field;

    @FXML
    private TextField setY_field;

    @FXML
    private VBox window_changeParams;

    @FXML
    void changeParameters() {
        String name = setName_field.getText();
        int health = Integer.parseInt(setHealth_field.getText());
        double x = Double.parseDouble(setX_field.getText());
        double y = Double.parseDouble(setY_field.getText());
        ForWarriors.changeParameters(name,health,x,y);
        Game.newStage.close();
    }

}
