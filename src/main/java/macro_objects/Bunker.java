package macro_objects;

import java.io.FileNotFoundException;

public class Bunker extends Base {
    public Bunker(double x, double y) throws FileNotFoundException {
        super(3);

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
}