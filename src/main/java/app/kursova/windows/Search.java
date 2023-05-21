package app.kursova.windows;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import macro_objects.Base;
import micro_objects.Kamikaze;

import static app.kursova.Game.world;

public class Search {
    private static int select;

    @FXML
    private ListView<String> listView;

    @FXML
    private TextField tF_name;

    @FXML
    private MenuButton saw_Activity;

    @FXML
    private MenuButton name_BMO;

    @FXML
    void searchByBase(ActionEvent event) {
        ObservableList<String> items = FXCollections.observableArrayList();
        if (select == 0) {
            for (Kamikaze item : world.getWarriors())
                if (!item.isInMacro())
                    items.add(item.toString());
        }
        for (Base base : world.getBases()) {
            if (select == 1)
                for (Kamikaze item : base.getState())
                    items.add(item.toString());
            if (select == 2)
                for (Kamikaze item : base.getState())
                    items.add(item.toString());
            if (select == 3)
                for (Kamikaze item : base.getState())
                    items.add(item.toString());
        }
        listView.setItems(items);
    }

    @FXML
    void searchByName() {
        String name = tF_name.getText();
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Kamikaze item : world.getWarriors())
            if (item.getName().getText().contains(name))
                items.add(item.toString());
        listView.setItems(items);
    }

    @FXML
    void searchByActivity() {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Kamikaze item : world.getWarriors()) {
            if (select == 1 && item.isActive())
                items.add(item.toString());
            if (select == 0 && !item.isActive())
                items.add(item.toString());
        }
        listView.setItems(items);
    }

    @FXML
    void selectTrue() {
        saw_Activity.setText("True");
        select = 1;
    }

    @FXML
    void selectFalse() {
        saw_Activity.setText("False");
        select = 0;
    }

    @FXML
    void selectBunker() {
        name_BMO.setText("Bunker");
        select = 1;
    }

    @FXML
    void selectGreenBase() {
        name_BMO.setText("Green Base");
        select = 2;
    }

    @FXML
    void selectRedBase() {
        name_BMO.setText("Red Base");
        select = 3;
    }

    @FXML
    void selectNowhere() {
        name_BMO.setText("Nowhere");
        select = 0;
    }
}
