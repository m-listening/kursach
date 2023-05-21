package macro_objects;

import micro_objects.Kamikaze;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

public class GreenBase extends Base {
    private final Set<Kamikaze> personnel;

    public GreenBase(double x, double y) throws FileNotFoundException {
        super(2);
        personnel = new HashSet<>();

        getName().setText("GreenBase");
        getWithin().setText("0");

        setX(x);
        setY(y);
        getGroup().setLayoutX(x);
        getGroup().setLayoutY(y);
    }

    public GreenBase() throws FileNotFoundException {
        this(150, 360);
    }

    @Override
    public String toString() {
        return "GreenBase{ " +
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
