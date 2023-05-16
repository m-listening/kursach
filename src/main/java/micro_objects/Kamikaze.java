package micro_objects;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Kamikaze extends Warrior implements Cloneable, Comparable<Kamikaze> {
    protected Murder murders;
    private double armor, damage;

    public Kamikaze(String name, double health) {
        group = new Group();
        move = 3;
        armor = 50;
        damage = 500;
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

        life = new Line(0, +15, +50, +15);
        life.setStrokeWidth(3);
        life.setStroke(Color.BLACK);

        imageView = new ImageView();
        imageView.setLayoutX(0);
        imageView.setLayoutY(20);

        rectangle = new Rectangle(-5, -5, 65, 85);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStrokeWidth(3);
        rectangle.setStroke(Color.TRANSPARENT);


        circle = new Circle();
        circle.setLayoutX(20);
        circle.setLayoutY(40);
        circle.setRadius(50);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.GREY);

        group.getChildren().addAll(imageView, life, this.name, rectangle, circle);

        System.out.println("Конструктор викликаний.\n" + this);
    }

    public boolean inflictDamage(Kamikaze warrior) {
        if (warrior.getArmor() > 0) warrior.setArmor(warrior.getArmor() - this.getDamage());
        if (warrior.getArmor() == 0) {
            warrior.setHealth(warrior.getHealth() - this.getDamage());
            return warrior.getHealth() <= 0;
        } else {
            warrior.setHealth(warrior.getHealth() - warrior.getArmor());
            warrior.setArmor(0);
        }
        return false;
    }

    public Kamikaze() {
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

    public static class Murder {
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

    static {
        System.out.println("Статичний блок було викликано.");
    }

    {
        System.out.println("Динамічний блок було викликано.");
    }

    @Override
    public Kamikaze clone() throws CloneNotSupportedException {
        Kamikaze kamikaze = (Kamikaze) super.clone();

        kamikaze.murders = new Murder();

        kamikaze.name = new Label(kamikaze.getName().getText() + ".cl");
        kamikaze.getName().setLayoutX(5);
        kamikaze.getName().setLayoutY(-2);
        kamikaze.getName().setFont(Font.font("Impact", 14));

        kamikaze.active = false;
        kamikaze.elect = false;
        kamikaze.team = null;


        kamikaze.life = new Line(5, +15, +50, +15);
        kamikaze.getLife().setStrokeWidth(3);
        kamikaze.getLife().setStroke(Color.BLACK);

        kamikaze.image = kamikaze.getImage();
        kamikaze.imageView = new ImageView(kamikaze.getImage());
        kamikaze.getImageView().setLayoutX(0);
        kamikaze.getImageView().setLayoutY(20);

        kamikaze.setRectangle(new Rectangle(-5, -5, 65, 85));
        kamikaze.getRectangle().setFill(Color.TRANSPARENT);
        kamikaze.getRectangle().setStrokeWidth(3);
        kamikaze.getRectangle().setStroke(Color.TRANSPARENT);

        kamikaze.setCircle(new Circle());
        kamikaze.getCircle().setLayoutX(20);
        kamikaze.getCircle().setLayoutY(40);
        kamikaze.getCircle().setRadius(50);
        kamikaze.getCircle().setFill(Color.TRANSPARENT);
        kamikaze.getCircle().setStroke(Color.GREY);

        kamikaze.group = new Group();
        kamikaze.getGroup().getChildren().addAll(kamikaze.getCircle(), kamikaze.getImageView(), kamikaze.getLife(), kamikaze.getName(), kamikaze.getRectangle());

        kamikaze.setX(kamikaze.getX() + 100);
        kamikaze.setY(kamikaze.getY() + 100);

        return kamikaze;
    }

    @Override
    public int compareTo(Kamikaze o) {
        int result = 0;
        result += Double.compare(this.getHealth(), o.getHealth());
        result += Integer.compare(this.getMurders().getCount(), o.getMurders().getCount());
        result += this.getName().getText().compareTo(o.getName().getText());
        return result;
    }

    public void setMurders(Murder murders) {
        this.murders = murders;
    }

    public Murder getMurders() {
        return murders;
    }

    public double getArmor() {
        return armor;
    }

    public void setArmor(double armor) {
        this.armor = armor;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}