package macro_objects;

import micro_objects.Kamikaze;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

public class RedBase extends Base {
    private final Set<Kamikaze> personnel;

    public RedBase(double x, double y) throws FileNotFoundException {
        super(1);
        personnel = new HashSet<>();

        getName().setText("Red Base");
        getWithin().setText("0");

        setX(x);
        setY(y);
        getGroup().setLayoutX(x);
        getGroup().setLayoutY(y);
    }

    public RedBase() throws FileNotFoundException {
        this(1130, 360);
    }

    @Override
    public String toString() {
        return "RedBase{ " +
                "x=" + getX() +
                ", y=" + getY() +
                ", name=" + getName().getText() +
                ", within=" + getWithin().getText() +
                '}';
    }

    public Set<Kamikaze> getPersonnel() {
        return personnel;
    }

}
