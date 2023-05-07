package app.kursova.windows;

import app.kursova.Game;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import micro_objects.Kamikaze;

import java.io.FileNotFoundException;

import static Methods.Collections.warrior;
import static Methods.Utilities.lvlImage;

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

        kamikaze.setTeam(selectedTeam());
        Image image = lvlImage(selectedLvl());
        if (image != null) {
            kamikaze.setImage(image);
            kamikaze.getImageView().setImage(kamikaze.getImage());
        }

        warrior.put(kamikaze, kamikaze.isTeam());
        Game.mainGroup.getChildren().add(kamikaze.getGroup());
        Game.newStage.close();
    }

    private int selectedLvl() {
        return RB_lvl1.isSelected() ? 1 : RB_lvl2.isSelected() ? 2 : RB_lvl3.isSelected() ? 3 : 0;
    }

    private Boolean selectedTeam() {
        return RB_tmGreen.isSelected() ? Boolean.TRUE : RB_tmRed.isSelected() ? Boolean.FALSE : null;
    }

    @FXML
    void RB_tmRed() {
        RB_tmGreen.setSelected(false);
    }

    @FXML
    void RB_tmGreen() {
        RB_tmRed.setSelected(false);
    }

    @FXML
    void RB_kamikaze() {
        RB_lvl2.setSelected(false);
        RB_lvl3.setSelected(false);
    }

    @FXML
    void RB_simpleSoldier() {
        RB_lvl1.setSelected(false);
        RB_lvl3.setSelected(false);
    }

    @FXML
    void RB_SSO() {
        RB_lvl1.setSelected(false);
        RB_lvl2.setSelected(false);
    }
}
