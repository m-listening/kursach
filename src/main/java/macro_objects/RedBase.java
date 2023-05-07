package macro_objects;

import javafx.scene.image.Image;
import micro_objects.Warrior;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class RedBase extends Base {
    private final List<Warrior> personnel;

    public RedBase(double x, double y) throws FileNotFoundException {
        super(x, y);
        personnel = new ArrayList<>();

        name.setText("Red Base");
        name.setLayoutX(x - 45);
        name.setLayoutY(y - 100);

        within.setText("0");
        within.setLayoutX(x - 5);
        within.setLayoutY(y + 100);

        image = new Image(new FileInputStream("src/images/RedBase.png"), 150, 150, false, false);
        imageView.setImage(image);
    }

    public RedBase() throws FileNotFoundException {
        this(1130, 360);
    }

    @Override
    public String toString() {
        return "RedBase{ " +
                "x=" + x +
                ", y=" + y +
                ", name=" + name.getText() +
                ", within=" + within.getText() +
                '}';
    }

    public List<Warrior> getPersonnel() {
        return personnel;
    }

}
