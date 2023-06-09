package data.windows;

import app.Play;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import data.objects.micro_objects.Kamikaze;


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

        for (Kamikaze item : Play.world.getElectedWarriors()) {
            item.setName(name);
            item.setHealth(health);
            item.setX(x);
            item.setY(y);
        }
        Play.globalStage.close();
    }
}
