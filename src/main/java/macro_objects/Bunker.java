package macro_objects;

import micro_objects.Kamikaze;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

public class Bunker extends Base {
    private final Set<Kamikaze> redWarriors;
    private final Set<Kamikaze> greenWarriors;
    private final Set<Kamikaze> nobodyWarriors;

    public Bunker(double x, double y) throws FileNotFoundException {
        super(3);

        redWarriors = new HashSet<>();
        greenWarriors = new HashSet<>();
        nobodyWarriors = new HashSet<>();

        getName().setText("Bunker");
        getWithin().setText("0");

        setX(x);
        setY(y);
        getGroup().setLayoutX(x);
        getGroup().setLayoutY(y);
    }

    public Bunker() throws FileNotFoundException {
        this(0, 0);
    }

    @Override
    public String toString() {
        return "Bunker{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", name=" + getName().getText() +
                ", within=" + getWithin().getText() +
                '}';
    }

    public void addWarrior(Kamikaze warrior) {
        if (warrior.isTeam() == null) {
            nobodyWarriors.add(warrior);
        } else if (warrior.isTeam()) {
            greenWarriors.add(warrior);
        } else redWarriors.add(warrior);
    }

    public Set<Kamikaze> getRedWarriors() {
        return redWarriors;
    }

    public Set<Kamikaze> getGreenWarriors() {
        return greenWarriors;
    }

    public Set<Kamikaze> getNobodyWarriors() {
        return nobodyWarriors;
    }
}