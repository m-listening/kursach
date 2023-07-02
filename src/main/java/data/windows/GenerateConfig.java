package data.windows;

import app.Play;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class GenerateConfig {

    @FXML
    private RadioButton generate100;

    @FXML
    private RadioButton generate20;

    @FXML
    private RadioButton generate50;

    @FXML
    private ToggleGroup generated;

    @FXML
    private ToggleGroup initial;

    @FXML
    private RadioButton initial100;

    @FXML
    private RadioButton initial150;

    @FXML
    private RadioButton initial200;

    @FXML
    private RadioButton initial300;

    @FXML
    private RadioButton initial50;

    @FXML
    private ToggleGroup less;

    @FXML
    private RadioButton less10;

    @FXML
    private RadioButton less25;

    @FXML
    private RadioButton less50;

    @FXML
    private RadioButton less75;

    @FXML
    void playGame(ActionEvent event) {
        int initial = initial50.isSelected() ? 50
                : initial100.isSelected() ? 100
                : initial150.isSelected() ? 150
                : initial200.isSelected() ? 200
                : initial300.isSelected() ? 300 : 0;
        int less = less10.isSelected() ? 10
                : less25.isSelected() ? 25
                : less50.isSelected() ? 50
                : less75.isSelected() ? 75 : 0;
        int generate = generate20.isSelected() ? 20
                : generate50.isSelected() ? 50
                : generate100.isSelected() ? 100 : 0;
        Play.world.initializeConfig(initial * 2, generate, less);
        Play.globalStage.close();
    }
}
