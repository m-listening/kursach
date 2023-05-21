package data.macro_objects;

import java.io.FileNotFoundException;

public class GreenBase extends Base {

    public GreenBase(double x, double y) throws FileNotFoundException {
        super(2);

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
}
