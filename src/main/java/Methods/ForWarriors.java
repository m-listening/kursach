package Methods;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import micro_objects.Kamikaze;
import micro_objects.Warrior;

import java.util.HashMap;

import static Methods.Collections.*;
import static app.kursova.Game.mainGroup;

public class ForWarriors {
    private static final HashMap<Kamikaze, Boolean> newListWarrior = new HashMap<>();

    public static void deleteWarrior() {
        for (Kamikaze item : warriorsActive) {
            newListWarrior.put(item, item.isTeam());
        }
        for (Kamikaze item : newListWarrior.keySet()) {
            warrior.remove(item);
            mainGroup.getChildren().remove(item.getGroup());
        }
        warriorsActive.clear();
    }

    public static void turnOf() {
        for (Kamikaze item : warriorsActive) {
            item.setActive(false);
        }
        warriorsActive.clear();
    }

    public static void moveIfElect(double x, double y) {
        if (warriorElect != null && warriorElect.isActive()) {
            warriorElect.setX(warriorElect.getX() + x);
            warriorElect.setY(warriorElect.getY() + y);
            warriorElect.getGroup().setLayoutX(warriorElect.getGroup().getLayoutX() + x);
            warriorElect.getGroup().setLayoutY(warriorElect.getGroup().getLayoutY() + y);
        }
    }

    public static void changeParameters(String name, int health, double x, double y) {
        warriorElect.setName(new Label(name));
        warriorElect.setHealth(health);

        double _x = warriorElect.getX();
        double _y = warriorElect.getY();

        warriorElect.setX(x);
        warriorElect.setY(y);

        warriorElect.getGroup().setLayoutX(warriorElect.getX() - _x);
        warriorElect.getGroup().setLayoutY(warriorElect.getY() - _y);
    }

    public static ListView<Warrior> showWarriors() {
        ObservableList<Warrior> listWarriors = FXCollections.observableArrayList(warrior.keySet());
        return new ListView<>(listWarriors);
    }
}