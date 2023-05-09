package app.kursova.windows;

import app.kursova.Game;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import micro_objects.Kamikaze;

import java.io.FileNotFoundException;

import static Methods.Utilities.lvlImage;
import static app.kursova.Game.warriors;

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
        int health = Integer.parseInt(setHealth_field.getText());
        double x = Double.parseDouble(setX_field.getText());
        double y = Double.parseDouble(setY_field.getText());
        Kamikaze kamikaze = new Kamikaze(name, health);

        kamikaze.setX(x);
        kamikaze.setY(y);
        kamikaze.getGroup().setLayoutX(x);
        kamikaze.getGroup().setLayoutY(y);

        kamikaze.setTeam(selectedTeam);
        Image image = lvlImage(selectedLvl);
        if (image != null) {
            kamikaze.setImage(image);
            kamikaze.getImageView().setImage(kamikaze.getImage());
        }

        warriors.add(kamikaze);
        Game.mainGroup.getChildren().add(kamikaze.getGroup());
        Game.globalStage.close();
    }

    private static int selectedLvl = 0;
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
