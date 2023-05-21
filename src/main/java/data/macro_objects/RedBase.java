package data.macro_objects;

import java.io.FileNotFoundException;

public class RedBase extends Base {

    public RedBase(double x, double y) throws FileNotFoundException {
        super(1);

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
}
