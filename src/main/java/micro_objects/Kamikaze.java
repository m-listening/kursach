package micro_objects;

import Methods.Murder;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class Kamikaze extends Warrior implements Cloneable {

    public Kamikaze(String name, int health, double x, double y) throws FileNotFoundException {
        group = new Group();
        move = 3;

        this.health = health;

        this.murders = new Murder();

        this.x = x;
        this.y = y;

        this.name = new Label(name);
        this.name.setLayoutX(x + 5);
        this.name.setLayoutY(y - 2);
        this.name.setFont(Font.font("Impact", 14));

        active = false;
        elect = false;
        team = null;

        image = new Image(new FileInputStream("src/images/zombie.png"), 50, 50, false, false);

        life = new Line(this.x + 5, this.y + 15, this.x + 50, y + 15);
        life.setStrokeWidth(3);
        life.setStroke(Color.BLACK);

        imageView = new ImageView(image);
        imageView.setLayoutX(this.x);
        imageView.setLayoutY(this.y + 15 + 5);

        rectangle = new Rectangle(x - 5, y - 5, 65, 85);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStrokeWidth(3);
        rectangle.setStroke(Color.TRANSPARENT);

        group.getChildren().addAll(imageView, life, this.name, rectangle);
        System.out.println("Конструктор викликаний.\n" + this);
    }

    public Kamikaze() throws FileNotFoundException {
        this("", 0, 1, 1);
    }

    @Override
    public String toString() {
        return "Kamikaze{" +
                "name=" + name.getText() +
                ", health=" + health +
                ", x=" + x +
                ", y=" + y +
                ", team=" + (isTeam() != null ? isTeam() ? "Green" : "Red" : "None") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kamikaze kamikaze = (Kamikaze) o;
        return team == kamikaze.team && Objects.equals(group, kamikaze.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, group);
    }

    static {
        System.out.println("Статичний блок було викликано.");
    }

    {
        System.out.println("Динамічний блок було викликано.");
    }

    @Override
    public Kamikaze clone() throws CloneNotSupportedException {
        Kamikaze kamikaze = (Kamikaze) super.clone();

        double _x = kamikaze.getX() + 100;
        double _y = kamikaze.getY() + 100;

        kamikaze.setGroup(new Group());

        kamikaze.setMurders(new Murder());

        kamikaze.setX(_x);
        kamikaze.setY(_y);

        kamikaze.setName(new Label(kamikaze.getName().getText() + " cloned"));
        kamikaze.getName().setLayoutX(_x + 5);
        kamikaze.getName().setLayoutY(_y - 2);
        kamikaze.getName().setFont(Font.font("Impact", 14));

        kamikaze.active = false;
        kamikaze.elect = false;
        kamikaze.team = null;

        try {
            kamikaze.setImage(new Image(new FileInputStream("src/images/kamikaze.png"), 50, 50, false, false));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        kamikaze.setLife(new Line(_x + 5, _y + 15, _x + 50, _y + 15));
        kamikaze.getLife().setStrokeWidth(3);
        kamikaze.getLife().setStroke(Color.BLACK);

        kamikaze.setImageView(new ImageView(kamikaze.getImage()));
        kamikaze.getImageView().setLayoutX(_x);
        kamikaze.getImageView().setLayoutY(_y + 15 + 5);

        kamikaze.setRectangle(new Rectangle(_x - 5, _y - 5, 65, 85));
        kamikaze.getRectangle().setFill(Color.TRANSPARENT);
        kamikaze.getRectangle().setStrokeWidth(3);
        kamikaze.getRectangle().setStroke(Color.TRANSPARENT);

        kamikaze.getGroup().getChildren().addAll(kamikaze.getImageView(), kamikaze.getLife(), kamikaze.getName(), kamikaze.getRectangle());
        return kamikaze;
    }
}