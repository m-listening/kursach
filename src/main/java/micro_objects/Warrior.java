package micro_objects;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public interface Warrior {
    int health = 0;
    double x = 0, y = 0;
    boolean elect = false, team = false, active = false;
    Image image = null;
    ImageView imageView = null;
    Label name = null;
    Rectangle rectangle = null;
    Line life = null;
    Group group = null;
}
