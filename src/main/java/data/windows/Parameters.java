package data.windows;

import app.Play;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import data.micro_objects.Kamikaze;
import data.micro_objects.SSO;
import data.micro_objects.SimpleSoldier;

import java.io.FileNotFoundException;

import static data.Methods.Utilities.updateWarrior;

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

        Kamikaze warrior;
        int selectedLvl = 1;
        if (RB_lvl2.isSelected()) {
            warrior = new SimpleSoldier(name, health);
            selectedLvl = 2;
        } else if (RB_lvl3.isSelected()) {
            warrior = new SSO(name, health);
            selectedLvl = 3;
        } else warrior = new Kamikaze(name, health);

        Boolean selectedTeam;
        if (RB_tmGreen.isSelected()) {
            selectedTeam = true;
        } else if (RB_tmRed.isSelected()) {
            selectedTeam = false;
        } else selectedTeam = null;
        updateWarrior(warrior, x, y, selectedLvl, selectedTeam);
        Play.world.getAllWarriors().add(warrior);

        Play.globalStage.close();
    }
}