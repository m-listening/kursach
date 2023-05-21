package micro_objects;

import app.kursova.Game;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.Objects;

public class Kamikaze implements Cloneable, Comparable<Kamikaze> {
    private int move;
    private double x, y, health;
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
        move = 20;
        armor = 50;
        damage = 500;
        this.health = health;

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
        circle.setRadius(40);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.GREY);

        Game.world.getWorldGroup().getChildren().addAll(imageView, life, this.name, rectangle, circle);

        System.out.println("Конструктор викликаний.\n" + this);
    }

    public Kamikaze() {
        this("", 0);
    }

    /*** @return true -> warrior died, false -> alive*/
    public boolean inflictDamage(Kamikaze warrior) {
        warrior.setHealth(warrior.getHealth() - this.getDamage());
        return warrior.getHealth() <= 0;
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
        kamikaze.getCircle().setStroke(Color.GREY);

        Game.world.getWorldGroup().getChildren().addAll(kamikaze.getCircle(), kamikaze.getImageView(),
                kamikaze.getLife(), kamikaze.getName(), kamikaze.getRectangle());

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

    public void setInMacro(boolean inMacro) {
        this.inMacro = inMacro;
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

    public void setElect(boolean elect) {
        this.elect = elect;
        setRectangleColor();
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