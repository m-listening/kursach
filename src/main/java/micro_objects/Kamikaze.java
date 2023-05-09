package micro_objects;

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

public class Kamikaze extends Warrior implements Cloneable, Comparable<Kamikaze> {
    private Murder murders;

    public Kamikaze(String name, int health) throws FileNotFoundException {
        group = new Group();
        move = 3;

        this.health = health;

        murders = new Murder() {
        };

        this.name = new Label(name);
        this.name.setLayoutX(+5);
        this.name.setLayoutY(-2);
        this.name.setFont(Font.font("Impact", 14));

        active = false;
        elect = false;
        team = null;
        inMacro = false;

        image = new Image(new FileInputStream("src/images/zombie.png"), 50, 50, false, false);

        life = new Line(0, +15, +50, +15);
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
                ", x=" + x +
                ", y=" + y +
                ", active=" + active +
                ", health=" + health +
                ", murders=" + murders.getCount() +
                ", inMacro=" + inMacro +
                ", team=" + (isTeam() != null ? isTeam() ? "Green" : "Red" : "None") +
                '}';
    }

    private static class Murder {
        private int count;

        public Murder() {
            this.count = 0;
        }

        public int getCount() {
            return count;
        }

        public void setCount() {
            this.count++;
        }

        public void setCount(int v) {
            this.count = v;
        }
    }

    public Murder getMurders() {
        return murders;
    }

    public void setMurders(Murder murders) {
        this.murders = murders;
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

        kamikaze.x = kamikaze.getX() + 100;
        kamikaze.y = kamikaze.getY() + 100;

        kamikaze.murders = new Murder();

        kamikaze.name = new Label(kamikaze.getName().getText() + " cloned");
        kamikaze.getName().setLayoutX(5);
        kamikaze.getName().setLayoutY(-2);
        kamikaze.getName().setFont(Font.font("Impact", 14));

        kamikaze.active = false;
        kamikaze.elect = false;
        kamikaze.team = null;

        try {
            kamikaze.image = new Image(new FileInputStream("src/images/kamikaze.png"), 50, 50, false, false);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        kamikaze.life = new Line(5, +15, +50, +15);
        kamikaze.getLife().setStrokeWidth(3);
        kamikaze.getLife().setStroke(Color.BLACK);

        kamikaze.imageView = new ImageView(kamikaze.getImage());
        kamikaze.getImageView().setLayoutX(0);
        kamikaze.getImageView().setLayoutY(20);

        kamikaze.rectangle = new Rectangle(-5, -5, 65, 85);
        kamikaze.getRectangle().setFill(Color.TRANSPARENT);
        kamikaze.getRectangle().setStrokeWidth(3);
        kamikaze.getRectangle().setStroke(Color.TRANSPARENT);

        kamikaze.group = new Group();
        kamikaze.getGroup().getChildren().addAll(kamikaze.getImageView(), kamikaze.getLife(), kamikaze.getName(), kamikaze.getRectangle());
        kamikaze.getGroup().setLayoutX(kamikaze.getX());
        kamikaze.getGroup().setLayoutY(kamikaze.getY());

        return kamikaze;
    }

    @Override
    public int compareTo(Kamikaze o) {
        int result = 0;
        result += Integer.compare(this.getHealth(), o.getHealth());
        result += Integer.compare(this.getMurders().getCount(), o.getMurders().getCount());
        result += this.getName().getText().compareTo(o.getName().getText());
        return result;
    }
}