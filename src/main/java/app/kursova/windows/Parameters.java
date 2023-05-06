package app.kursova.windows;

import app.kursova.Game;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import micro_objects.Kamikaze;

import java.io.FileNotFoundException;

import static Methods.Collections.warrior;

public class Parameters {

    @FXML
    private RadioButton RB_lvl1;

    @FXML
    private RadioButton RB_lvl2;

    @FXML
    private RadioButton RB_lvl3;

    @FXML
    private RadioButton RB_tmGreen;

    @FXML
    private RadioButton RB_tmRed;

    @FXML
    private TextField setHealth_field;

    @FXML
    private TextField setName_field;

    @FXML
    private TextField setX_field;

    @FXML
    private TextField setY_field;

    @FXML
    void createNewWarrior() throws FileNotFoundException {
        String name = setName_field.getText();
        int health = Integer.parseInt(setHealth_field.getText());
        double x = Double.parseDouble(setX_field.getText());
        double y = Double.parseDouble(setY_field.getText());
        Kamikaze kamikaze = new Kamikaze(name, health, x, y);

        warrior.put(kamikaze, kamikaze.isTeam());
        Game.mainGroup.getChildren().add(kamikaze.getGroup());
        Game.newStage.close();
    }

}
