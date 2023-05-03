package Methods;

import micro_objects.Kamikaze;

import java.util.HashMap;

import static Methods.Collections.warrior;
import static Methods.Collections.warriorsElect;
import static app.kursova.Game.mainGroup;

public class ForWarriors {
    private static final HashMap<Kamikaze, Boolean> newListWarrior = new HashMap<>();

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
        for (Kamikaze item : warriorsElect) {
            item.setX(item.getX() + x);
            item.setY(item.getY() + y);
            item.getGroup().setLayoutX(item.getGroup().getLayoutX() + x);
            item.getGroup().setLayoutY(item.getGroup().getLayoutY() + y);
        }
    }

    public static void changeParameters(String name, int health, double x, double y) {
        for (Kamikaze item : warriorsElect) {
            item.setName(name);
            item.setHealth(health);

            double _x = item.getX();
            double _y = item.getY();

            item.setX(x);
            item.setY(y);

            item.getGroup().setLayoutX(item.getX() - _x);
            item.getGroup().setLayoutY(item.getY() - _y);
        }
    }
}
