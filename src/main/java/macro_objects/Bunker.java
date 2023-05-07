package macro_objects;

import javafx.scene.image.Image;
import micro_objects.Warrior;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Bunker extends Base {
    private final List<Warrior> redWarriors;
    private final List<Warrior> greenWarriors;
    private final List<Warrior> nobodyWarriors;

    public Bunker(double x, double y) throws FileNotFoundException {
        super(x, y);
        redWarriors = new ArrayList<>();
        greenWarriors = new ArrayList<>();
        nobodyWarriors = new ArrayList<>();

        name.setText("Bunker");
        name.setLayoutX(x - 25);
        name.setLayoutY(y - 100);

        within.setText("0");
        within.setLayoutX(x - 5);
        within.setLayoutY(y + 100);

        image = new Image(new FileInputStream("src/images/bunker.png"), 150, 150, false, false);
        imageView.setImage(image);
    }

    public Bunker() throws FileNotFoundException {
        this(640, 360);
    }

    @Override
    public String toString() {
        return "Bunker{" +
                "x=" + x +
                ", y=" + y +
                ", name=" + name.getText() +
                ", within=" + within.getText() +
                '}';
    }

    public void addWarrior(Warrior warrior) {
        if (warrior.isTeam()) {
            greenWarriors.add(warrior);
        } else if (!warrior.isTeam()) {
            redWarriors.add(warrior);
        } else nobodyWarriors.add(warrior);
    }

    public List<Warrior> getRedWarriors() {
        return redWarriors;
    }

    public List<Warrior> getGreenWarriors() {
        return greenWarriors;
    }

    public List<Warrior> getNobodyWarriors() {
        return nobodyWarriors;
    }
}