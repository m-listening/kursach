package data.windows;

import app.Play;
import data.objects.micro_objects.Kamikaze;
import data.objects.micro_objects.SSO;
import data.objects.micro_objects.SimpleSoldier;
import data.functional.forObjects.micro.enums.Team;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class Parameters {
    @FXML
    public RadioButton RB_lvl1;
    @FXML
    public ToggleGroup lvl;
    @FXML
    public ToggleGroup team;
    @FXML
    public Button createWarrior_button;
    @FXML
    private RadioButton RB_lvl2, RB_lvl3;

    @FXML
    private RadioButton RB_tmGreen, RB_tmRed;

    @FXML
    private TextField setHealth_field, setName_field;

    @FXML
    private TextField setY_field, setX_field;

    @FXML
    private CheckBox active, elect;
    @FXML
    private VBox window_params;

    @FXML
    void createNewWarrior() {
        String name = setName_field.getText();
        double health = Double.parseDouble(setHealth_field.getText());
        double x = Double.parseDouble(setX_field.getText());
        double y = Double.parseDouble(setY_field.getText());

        Kamikaze warrior;
        if (RB_lvl2.isSelected())
            warrior = new SimpleSoldier(name, health, x, y);
        else if (RB_lvl3.isSelected())
            warrior = new SSO(name, health, x, y);
        else warrior = new Kamikaze(name, health, x, y);

        Team selectedTeam = null;
        if (RB_tmGreen.isSelected())
            selectedTeam = Team.GREEN;
        else if (RB_tmRed.isSelected())
            selectedTeam = Team.RED;
        warrior.setActive(active.isSelected());
        warrior.setElect(elect.isSelected());
        warrior.setTeam(selectedTeam);
        warrior.setX(x);
        warrior.setY(y);

        Play.globalStage.close();
    }
}