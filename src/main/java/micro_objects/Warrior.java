package micro_objects;

import app.kursova.Coordinates;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public abstract class Warrior {
    int health, move = 1;
    double x, y;
    Coordinates coordinates;
    boolean elect, team, active;
    Image image;
    ImageView imageView;
    Label name;
    Rectangle rectangle;
    Line life;
    Group group;
}
