package app.kursova.windows;

import app.kursova.Game;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static app.kursova.Game.warriorElect;

public class ChangeParameters {

    @FXML
    private TextField setHealth_field, setName_field;

    @FXML
    private TextField setX_field, setY_field;

    @FXML
    void changeParameters() {
        String name = setName_field.getText();
        int health = Integer.parseInt(setHealth_field.getText());
        double x = Double.parseDouble(setX_field.getText());
        double y = Double.parseDouble(setY_field.getText());

        warriorElect.setName(name);
        warriorElect.setHealth(health);

        warriorElect.setX(x);
        warriorElect.setY(y);

        warriorElect.getGroup().setLayoutX(x);
        warriorElect.getGroup().setLayoutY(y);
        Game.globalStage.close();
    }
}
