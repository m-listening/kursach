package app.kursova.windows;

import app.kursova.Game;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import micro_objects.Kamikaze;
import micro_objects.SSO;
import micro_objects.SimpleSoldier;

import java.io.FileNotFoundException;

import static Methods.Utilities.updateWarrior;
import static app.kursova.World.warriors;

public class Parameters {

    @FXML
    private RadioButton RB_lvl1, RB_lvl2, RB_lvl3;

    @FXML
    private RadioButton RB_tmGreen, RB_tmRed;

    @FXML
    private TextField setHealth_field, setName_field;

    @FXML
    private TextField setY_field, setX_field;

    @FXML
    void createNewWarrior() throws FileNotFoundException {
        String name = setName_field.getText();
        double health = Double.parseDouble(setHealth_field.getText());
        double x = Double.parseDouble(setX_field.getText());
        double y = Double.parseDouble(setY_field.getText());
        Kamikaze warrior = null;
        if (selectedLvl == 1) {
            warrior = new Kamikaze(name, health);
        } else if (selectedLvl == 2) {
            warrior = new SimpleSoldier(name, health);
        } else if (selectedLvl == 3) {
            warrior = new SSO(name, health);
        }

        updateWarrior(warrior, x, y, selectedLvl, selectedTeam);
        warriors.add(warrior);

        Game.globalStage.close();
    }

    private static int selectedLvl = 1;
    private static Boolean selectedTeam;

    @FXML
    void selectLvl() {
        if (RB_lvl1.isSelected()) {
            selectedLvl = 1;
        } else if (RB_lvl2.isSelected()) {
            selectedLvl = 2;
        } else if (RB_lvl3.isSelected()) {
            selectedLvl = 3;
        }
    }

    @FXML
    void selectTeam() {
        if (RB_tmGreen.isSelected()) {
            selectedTeam = true;
        } else if (RB_tmRed.isSelected()) {
            selectedTeam = false;
        } else selectedTeam = null;
    }
}
