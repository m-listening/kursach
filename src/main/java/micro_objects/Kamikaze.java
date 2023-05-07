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

    public Kamikaze(String name, int health) throws FileNotFoundException {
        group = new Group();
        move = 3;

        this.health = health;

        this.murders = new Murder();

        this.name = new Label(name);
        this.name.setLayoutX(+5);
        this.name.setLayoutY(-2);
        this.name.setFont(Font.font("Impact", 14));

        active = false;
        elect = false;
        team = null;
        inMacro = false;

        image = new Image(new FileInputStream("src/images/zombie.png"), 50, 50, false, false);

        life = new Line(+5, +15, +50, +15);
        life.setStrokeWidth(3);
        life.setStroke(Color.BLACK);

        imageView = new ImageView(image);
        imageView.setLayoutX(0);
        imageView.setLayoutY(20);

        rectangle = new Rectangle(-5, -5, 65, 85);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStrokeWidth(3);
        rectangle.setStroke(Color.TRANSPARENT);

        group.getChildren().addAll(imageView, life, this.name, rectangle);
        System.out.println("Конструктор викликаний.\n" + this);
    }

    public Kamikaze() throws FileNotFoundException {
        this("", 0);
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

        kamikaze.setX(kamikaze.getX() + 100);
        kamikaze.setY(kamikaze.getY() + 100);

        kamikaze.setMurders(new Murder());

        kamikaze.setName(new Label(kamikaze.getName().getText() + " cloned"));
        kamikaze.getName().setLayoutX(5);
        kamikaze.getName().setLayoutY(-2);
        kamikaze.getName().setFont(Font.font("Impact", 14));

        kamikaze.active = false;
        kamikaze.elect = false;
        kamikaze.team = null;

        try {
            kamikaze.setImage(new Image(new FileInputStream("src/images/kamikaze.png"), 50, 50, false, false));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        kamikaze.setLife(new Line(5, +15, +50, +15));
        kamikaze.getLife().setStrokeWidth(3);
        kamikaze.getLife().setStroke(Color.BLACK);

        kamikaze.setImageView(new ImageView(kamikaze.getImage()));
        kamikaze.getImageView().setLayoutX(0);
        kamikaze.getImageView().setLayoutY(20);

        kamikaze.setRectangle(new Rectangle(-5, -5, 65, 85));
        kamikaze.getRectangle().setFill(Color.TRANSPARENT);
        kamikaze.getRectangle().setStrokeWidth(3);
        kamikaze.getRectangle().setStroke(Color.TRANSPARENT);

        kamikaze.setGroup(new Group());
        kamikaze.getGroup().getChildren().addAll(kamikaze.getImageView(), kamikaze.getLife(), kamikaze.getName(), kamikaze.getRectangle());
        kamikaze.getGroup().setLayoutX(kamikaze.getX());
        kamikaze.getGroup().setLayoutY(kamikaze.getY());

        return kamikaze;
    }
}