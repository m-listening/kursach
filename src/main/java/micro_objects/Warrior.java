package micro_objects;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public abstract class Warrior {
    int move = 1;
    double x, y, health;
    boolean elect, active, inMacro;
    Boolean team;
    Image image;
    ImageView imageView;
    Label name;
    Circle circle;
    Rectangle rectangle;
    Line life;
    Group group;

    @Override
    public String toString() {
        return "Warrior{" +
                "name=" + name.getText() +
                ", x=" + x +
                ", y=" + y +
                ", active=" + active +
                ", health=" + health +
                ", inMacro=" + inMacro +
                ", team=" + (isTeam() != null ? isTeam() ? "Green" : "Red" : "None") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warrior warrior = (Warrior) o;
        return health == warrior.health && Objects.equals(name, warrior.name) && Objects.equals(group, warrior.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(health, name, group);
    }

    public Boolean getTeam() {
        return team;
    }

    public void setRectangleColor() {
        if (isElect()) {
            rectangle.setStroke(Color.RED);
        } else rectangle.setStroke(Color.TRANSPARENT);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Boolean isTeam() {
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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
        this.getGroup().setLayoutX(x);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        this.getGroup().setLayoutY(y);
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