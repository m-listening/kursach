package macro_objects;

import javafx.scene.image.Image;
import micro_objects.Warrior;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

public class GreenBase extends Base {
    private final Set<Warrior> personnel;

    public GreenBase(double x, double y) throws FileNotFoundException {
        super(0, 0);

        this.x = x;
        this.y = y;

        personnel = new HashSet<>();

        name.setText("GreenBase");
        name.setLayoutX(-45);
        name.setLayoutY(-100);

        within.setText("0");
        within.setLayoutX(-5);
        within.setLayoutY(+100);

        image = new Image(new FileInputStream("src/images/GreenBase.png"), 150, 150, false, false);
        imageView.setImage(image);

        group.setLayoutX(x);
        group.setLayoutY(y);
    }

    public GreenBase() throws FileNotFoundException {
        this(150, 360);
    }

    @Override
    public String toString() {
        return "GreenBase{ " +
                "x=" + x +
                ", y=" + y +
                ", name=" + name.getText() +
                ", within=" + within.getText() +
                '}';
    }

    public Set<Warrior> getPersonnel() {
        return personnel;
    }

}
