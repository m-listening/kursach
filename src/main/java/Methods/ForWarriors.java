package Methods;

import micro_objects.Kamikaze;

import java.util.HashMap;

import static app.kursova.Game.mainGroup;
import static app.kursova.Game.warrior;

public class ForWarriors {
    private static HashMap<Kamikaze, Boolean> newListWarrior = new HashMap<>();

    public static void deleteWarrior() {
        for (Kamikaze item : warrior.keySet()) {
            if (item.isElect()) {
                newListWarrior.put(item, item.isTeam());
            }
        }
        for (Kamikaze item : newListWarrior.keySet()) {
            warrior.remove(item);
            mainGroup.getChildren().remove(item.getGroup());
        }
    }

    public static void moveIfElect(double x, double y) {
        for (Kamikaze item : warrior.keySet()) {
            if (item.isElect()) {
                item.setX(x);
                item.setY(y);
                item.getGroup().setLayoutX(x);
                item.getGroup().setLayoutY(y);

            }
        }
    }

    public static void changeParameters(String name, int health, double x, double y) {
        for (Kamikaze item : warrior.keySet()) {
            if (item.isElect()) {
                item.setName(name);
                item.setHealth(health);
                item.setX(x);
                item.setY(y);

                double _x = item.getGroup().getLayoutX();
                double _y = item.getGroup().getLayoutY();
                if (_x == 0) {
                    item.getGroup().setLayoutX(x);
                }
                else if (_x > x) {
                    item.getGroup().setLayoutX(x - _x);
                }
                else if (_y < y)
                    item.getGroup().setLayoutX(y + _y);
                if (_y == 0) {
                    item.getGroup().setLayoutY(y);
                }
                else if (_y > y) {
                    item.getGroup().setLayoutX(y - _y);
                }
                else if (_x < x) {
                    item.getGroup().setLayoutX(x + _x);
                }
            }
        }
    }
}
