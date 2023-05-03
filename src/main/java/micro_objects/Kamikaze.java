package micro_objects;

import app.kursova.Coordinates;
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

        this.coordinates = new Coordinates(x,y);

        this.x = x;
        this.y = y;

        this.name = new Label(name);
        this.name.setLayoutX(x + 5);
        this.name.setLayoutY(y - 2);
        this.name.setFont(Font.font("Impact", 14));

        active = false;
        elect = false;

        image = new Image(new FileInputStream("src/images/kamikaze.png"), 50, 50, false, false);

        life = new Line(this.x + 5, this.y + 15, this.x + 50, y + 15);
        life.setStrokeWidth(3);
        life.setStroke(Color.GREEN);

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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kamikaze kamikaze = (Kamikaze) o;
        return team == kamikaze.team && Objects.equals(name, kamikaze.name) && Objects.equals(group, kamikaze.group) && Objects.equals(imageView, kamikaze.imageView);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, team, group, imageView);
    }

    static {
        System.out.println("Статичний блок було викликано.");
    }

    public Group getGroup() {
        return this.group;
    }

    public String getName() {
        return name.getText();
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isElect() {
        return elect;
    }

    public void setElect() {
        this.elect = !this.elect;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle() {
        if (isElect()) {
            rectangle.setStroke(Color.RED);
        } else rectangle.setStroke(Color.TRANSPARENT);
    }

    public Line getLife() {
        return life;
    }

    public void setLife(Line life) {
        this.life = life;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setElect(boolean elect) {
        this.elect = elect;
    }

    public boolean isTeam() {
        return team;
    }

    public void setTeam(boolean team) {
        this.team = team;
    }

    {
        System.out.println("Динамічний блок було викликано.");
    }

    @Override
    public Kamikaze clone() {
        try {
            return (Kamikaze) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}