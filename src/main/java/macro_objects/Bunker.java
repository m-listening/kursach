package macro_objects;

import javafx.scene.image.Image;
import micro_objects.Warrior;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

public class Bunker extends Base {
    private final Set<Warrior> redWarriors;
    private final Set<Warrior> greenWarriors;
    private final Set<Warrior> nobodyWarriors;

    public Bunker(double x, double y) throws FileNotFoundException {
        super(0, 0);

        this.x = x;
        this.y = y;

        redWarriors = new HashSet<>();
        greenWarriors = new HashSet<>();
        nobodyWarriors = new HashSet<>();

        name.setText("Bunker");
        name.setLayoutX(-25);
        name.setLayoutY(-100);

        within.setText("0");
        within.setLayoutX(-5);
        within.setLayoutY(+100);

        image = new Image(new FileInputStream("src/images/bunker.png"), 150, 150, false, false);
        imageView.setImage(image);

        group.setLayoutX(x);
        group.setLayoutY(y);
    }

    public Bunker() throws FileNotFoundException {
        this(0, 0);
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
        if (warrior.isTeam() == null) {
            nobodyWarriors.add(warrior);
        } else if (warrior.isTeam()) {
            greenWarriors.add(warrior);
        } else redWarriors.add(warrior);
    }

    public Set<Warrior> getRedWarriors() {
        return redWarriors;
    }

    public Set<Warrior> getGreenWarriors() {
        return greenWarriors;
    }

    public Set<Warrior> getNobodyWarriors() {
        return nobodyWarriors;
    }
}