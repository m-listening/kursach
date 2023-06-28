package data.windows;

import data.functional.forObjects.micro.comparators.HealthComparator;
import data.functional.forObjects.micro.comparators.MurdersComparator;
import data.functional.forObjects.micro.comparators.NameComparator;
import data.objects.macro_objects.Base;
import data.objects.macro_objects.Bunker;
import data.objects.macro_objects.GreenBase;
import data.objects.macro_objects.RedBase;
import data.objects.micro_objects.Kamikaze;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import static app.Play.world;

public class Search {
    private static int select;

    @FXML
    private ListView<String> listView;

    @FXML
    private TextField tF_name;
    @FXML
    private RadioButton rb_hpMoreThan;

    @FXML
    private RadioButton rb_leftPart;

    @FXML
    private RadioButton rb_rightPart;
    @FXML
    private MenuButton name_BMO;
    @FXML
    private RadioButton rb_HP;

    @FXML
    private RadioButton rb_topByMurders;

    @FXML
    private RadioButton rb_topByName;

    @FXML
    void showChoice1(ActionEvent event) {
        ObservableList<String> items = FXCollections.observableArrayList();
        int count = 0;
        if (rb_hpMoreThan.isSelected()) {
            for (Kamikaze obj : world.getAllWarriors())
                if (obj.getHealth() > obj.getMaxHealth() / 2)
                    count++;
            items.add("Count warriors with HP more than 50% = " + count);
        } else if (rb_leftPart.isSelected()) {
            for (Kamikaze obj : world.getAllWarriors())
                if (obj.getX() < 2000)
                    count++;
            items.add("Count warriors on the left side  = " + count);
        } else if (rb_rightPart.isSelected()) {
            for (Kamikaze obj : world.getAllWarriors())
                if (obj.getX() > 2000)
                    count++;
            items.add("Count warriors on the right side  = " + count);
        }
        listView.setItems(items);
    }

    @FXML
    void showChoice(ActionEvent event) {
        ObservableList<String> items = FXCollections.observableArrayList();
        if (rb_topByMurders.isSelected()) {
            world.getAllWarriors().sort(new MurdersComparator());
            world.getAllWarriors().forEach(e -> items.add(e.toString()));
        } else if (rb_HP.isSelected()) {
            world.getAllWarriors().sort(new HealthComparator());
            world.getAllWarriors().forEach(e -> items.add(e.toString()));
        } else if (rb_topByName.isSelected()) {
            world.getAllWarriors().sort(new NameComparator());
            world.getAllWarriors().forEach(e -> items.add(e.toString()));
        }
        listView.setItems(items);
    }

    @FXML
    void searchByBase(ActionEvent event) {
        ObservableList<String> items = FXCollections.observableArrayList();
        world.getAllWarriors().sort(Kamikaze::compareTo);
        if (select == 0)
            for (Kamikaze item : world.getAllWarriors())
                if (!item.isInMacro())
                    items.add(item.toString());
        for (Base base : world.getBaseSet()) {
            if (select == 1 && base instanceof Bunker)
                for (Kamikaze item : base.getState())
                    items.add(item.toString());
            if (select == 2 && base instanceof GreenBase)
                for (Kamikaze item : base.getState())
                    items.add(item.toString());
            if (select == 3 && base instanceof RedBase)
                for (Kamikaze item : base.getState())
                    items.add(item.toString());
        }
        listView.setItems(items);
    }

    @FXML
    void searchByName() {
        String name = tF_name.getText();
        ObservableList<String> items = FXCollections.observableArrayList();
        world.getAllWarriors().sort(Kamikaze::compareTo);
        for (Kamikaze item : world.getAllWarriors())
            if (item.getName().getText().contains(name))
                items.add(item.toString());
        listView.setItems(items);
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
