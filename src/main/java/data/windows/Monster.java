package data.windows;

import app.Play;
import data.functional.Utilities;
import data.objects.micro_objects.Kamikaze;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class Monster {
    @FXML
    private ListView<Kamikaze> monster1;

    @FXML
    private ListView<Kamikaze> monster2;

    @FXML
    void Cancel(ActionEvent event) {
        Play.globalStage.close();
    }

    @FXML
    void Ok(ActionEvent event) {
        Kamikaze kamikaze = monster1.getSelectionModel().getSelectedItem();
        Kamikaze kamikaze1 = monster2.getSelectionModel().getSelectedItem();
        if (kamikaze.equals(kamikaze1))
            kamikaze.setMonster(true);
        else {
            kamikaze.setMonster(true);
            kamikaze.setName("Monster 1");
            kamikaze1.setName("Monster 2");
            kamikaze1.setMonster(true);
        }
        for (Kamikaze item : Play.world.getAllWarriors()) {
            if (item.isMonster())
                item.getImageView().setImage(Utilities.getImage("Monster"));
        }
        Play.globalStage.close();
    }

    @FXML
    void load(ActionEvent event) {
        ObservableList<Kamikaze> items = FXCollections.observableArrayList();
        items.addAll(Play.world.getAllWarriors());
        monster1.setItems(items);
        monster2.setItems(items);
    }
}
