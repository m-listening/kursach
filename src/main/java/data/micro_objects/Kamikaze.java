package data.micro_objects;

import data.Methods.Utilities;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.Objects;

import static app.Play.*;

public class Kamikaze implements Cloneable, Comparable<Kamikaze> {
    private int move;
    private double x, y, health;
    private double aimX, aimY;
    private boolean elect, active, inMacro;
    private Boolean team;
    private Image image;
    private ImageView imageView;
    private Label name;
    private Circle circle;
    private Rectangle rectangle;
    private Line life;
    private Murder murders;
    private double armor, damage;

    public Kamikaze(String name, double health) {
        move = 3;
        armor = 200;
        damage = 3;
        this.health = health;
        aimY = -1000;
        aimX = -1000;

        murders = new Murder();

        this.name = new Label(name);
        this.name.setFont(Font.font("Impact", 14));

        active = false;
        elect = false;
        team = null;
        inMacro = false;

        life = new Line(0, +15, +50, +15);
        life.setStrokeWidth(3);
        life.setStroke(Color.BLACK);

        imageView = new ImageView();

        rectangle = new Rectangle(0, 0, 65, 85);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStrokeWidth(2);
        rectangle.setStroke(Color.TRANSPARENT);

        circle = new Circle(40);
        circle.setFill(Color.TRANSPARENT);

        world.getWorldGroup().getChildren().addAll(imageView, life, this.name, rectangle, circle);

        System.out.println("Конструктор викликаний.\n" + this);
    }

    public Kamikaze() {
        this("", 0);
    }

    public void lifeCycle() {
        world.getAllWarriors().forEach(e -> {
            if (!e.equals(this) && e.getTeam() != this.getTeam() && !this.isInMacro() && !e.isInMacro()) {
                if (e.getImageView().getBoundsInParent().intersects(this.getCircle().getBoundsInParent())) {
                    this.inflictDamage(e);
                    if (e.getHealth() <= 0) this.getMurders().implementCount();
                }
            }
        });
        if (!isElect() && !isInMacro() && isActive()) {
            if (isEmptyAim()) {
                Utilities.whatToDo(this);
            } else {
                if ((Math.abs(aimX - x) + Math.abs(aimY - y)) < 5.0) {
                    clearAim();
                } else {
                    double signDeltaX = Math.signum(aimX - x);
                    double dx = (Math.min(Math.abs(aimX - x), move)) * signDeltaX;

                    double signDeltaY = Math.signum(aimY - y);
                    double dy = (Math.min(Math.abs(aimY - y), move)) * signDeltaY;
                    moveActive(dx, dy);
                }
            }
        }
        if (this.getAimX() > sceneSizeMaxX * 1.5 || this.getAimY() > sceneSizeMaxY * 2)
            clearAim();
    }

    public void clearAim() {
        this.aimX = -1000;
        this.aimY = -1000;
    }

    public boolean isEmptyAim() {
        return (this.aimX < 0) && (this.aimY < 0);
    }

    public void moveActive(double stepByX, double stepByY) {
        if (this.getX() + stepByX < sceneSizeMaxX * 1.5 && this.getX() + stepByX > 0)
            this.setX(this.getX() + stepByX);
        if (this.getY() + stepByY < sceneSizeMaxY * 2 && this.getY() + stepByY > 0)
            this.setY(this.getY() + stepByY);
    }

    public void inflictDamage(Kamikaze warrior) {
        warrior.setHealth(warrior.getHealth() - this.getDamage());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" +
                "name=" + name.getText() +
                ", x=" + x +
                ", y=" + y +
                ", health=" + health +
                ", armor=" + armor +
                ", murders=" + murders.getCount() +
                ", elect=" + elect +
                ", active=" + active +
                ", inMacro=" + inMacro +
                ", team=" + (team != null ? team ? "Green" : "Red" : null) +
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

        public void implementCount() {
            this.count++;
        }

        public void setCount(int v) {
            this.count = v;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kamikaze kamikaze = (Kamikaze) o;
        return Objects.equals(imageView, kamikaze.imageView) && Objects.equals(name, kamikaze.name) && Objects.equals(circle, kamikaze.circle) && Objects.equals(rectangle, kamikaze.rectangle) && Objects.equals(life, kamikaze.life) && Objects.equals(murders, kamikaze.murders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageView, name, circle, rectangle, life, murders);
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

        kamikaze.setMurders(new Murder());

        kamikaze.setAimX(-1000);
        kamikaze.setAimY(-1000);

        kamikaze.setName(new Label(kamikaze.getName().getText() + ".cl"));
        kamikaze.getName().setFont(Font.font("Impact", 14));

        kamikaze.setLife(new Line(0, +15, +50, +15));
        kamikaze.getLife().setStrokeWidth(3);
        kamikaze.getLife().setStroke(Color.BLACK);

        kamikaze.elect = false;
        kamikaze.setActive(kamikaze.isActive());
        kamikaze.setTeam(kamikaze.getTeam());

        kamikaze.setImageView(new ImageView(kamikaze.getImage()));

        kamikaze.setRectangle(new Rectangle(0, 0, 65, 85));
        kamikaze.getRectangle().setFill(Color.TRANSPARENT);
        kamikaze.getRectangle().setStrokeWidth(2);
        kamikaze.getRectangle().setStroke(Color.TRANSPARENT);

        kamikaze.setCircle(new Circle(kamikaze.getCircle().getRadius()));
        kamikaze.getCircle().setFill(Color.TRANSPARENT);

        world.getWorldGroup().getChildren().addAll(kamikaze.getCircle(), kamikaze.getImageView(), kamikaze.getLife(), kamikaze.getName(), kamikaze.getRectangle());

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

    public double getAimX() {
        return aimX;
    }

    public void setAimX(double aimX) {
        this.aimX = aimX;
    }

    public double getAimY() {
        return aimY;
    }

    public void setAimY(double aimY) {
        this.aimY = aimY;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setRectangleColor() {
        if (isElect()) {
            rectangle.setStroke(Color.RED);
        } else rectangle.setStroke(Color.TRANSPARENT);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Boolean getTeam() {
        return team;
    }

    public void setTeam(Boolean team) {
        this.team = team;
    }

    public boolean isInMacro() {
        return inMacro;
    }

    public void flipInMacro() {
        inMacro = !inMacro;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        this.imageView.setLayoutX(x - 5);
        this.rectangle.setLayoutX(x - 5);
        this.circle.setLayoutX(x + 20);
        this.name.setLayoutX(x + 5);
        this.life.setLayoutX(x);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        this.imageView.setLayoutY(y + 20);
        this.rectangle.setLayoutY(y - 5);
        this.circle.setLayoutY(y + 40);
        this.name.setLayoutY(y - 2);
        this.life.setLayoutY(y);
    }

    public boolean isElect() {
        return elect;
    }

    public void flipElect() {
        this.elect = !this.elect;
        setRectangleColor();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        if (isActive()) this.life.setStroke(Color.LIGHTGREEN);
        else this.life.setStroke(Color.BLACK);
    }

    public void flipActive() {
        this.active = !this.active;
        if (isActive()) this.life.setStroke(Color.LIGHTGREEN);
        else this.life.setStroke(Color.BLACK);
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

    public Label getName() {
        return name;
    }

    public Line getLife() {
        return life;
    }

    public void setLife(Line life) {
        this.life = life;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setName(Label name) {
        this.name = name;
    }
}