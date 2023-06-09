package data.objects.macro_objects;

import app.Play;
import data.functional.forObjects.micro.enums.Team;
import data.functional.Utilities;
import data.interfaces.LifeCycle;
import data.objects.micro_objects.Kamikaze;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.HashSet;
import java.util.Set;

import static data.functional.forObjects.CONSTANTS.*;

public abstract class Base implements LifeCycle {
    private Team team;
    private double x, y;
    private final Image image;
    private final ImageView imageView;
    private final Circle circle;
    private final Label name, within;
    private final Group group;
    private Set<Kamikaze> state;

    public Base() {
        state = new HashSet<>();

        name = new Label();
        name.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 16.0));
        name.setLayoutX(MACRO_NAME_LAYOUT_X);
        name.setLayoutY(MACRO_NAME_LAYOUT_Y);

        within = new Label();
        within.setFont(Font.font("Arial", FontWeight.BOLD, 16.0));
        within.setLayoutX(MACRO_WITHIN_LAYOUT_X);
        within.setLayoutY(MACRO_WITHIN_LAYOUT_Y);

        circle = new Circle(MACRO_CIRCLE_RADIUS);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.GREY);

        image = Utilities.getImage(getClass().getSimpleName());
        imageView = new ImageView(image);
        imageView.setLayoutX(MACRO_IMAGE_VIEW_LAYOUT_X);
        imageView.setLayoutY(MACRO_IMAGE_VIEW_LAYOUT_Y);

        group = new Group(imageView, circle, name, within);
        Play.world.getWorldPane().getChildren().add(group);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", name=" + getName().getText() +
                ", within=" + getWithin().getText() +
                '}';
    }

    public void setState(Set<Kamikaze> state) {
        this.state = state;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
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

    public Image getImage() {
        return image;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Circle getCircle() {
        return circle;
    }

    public Label getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public Label getWithin() {
        return within;
    }

    public void setWithin(String within) {
        this.within.setText(within);
    }

    public void setWithin(Integer within) {
        this.within.setText(within.toString());
    }

    public Group getGroup() {
        return group;
    }

    public Set<Kamikaze> getState() {
        return state;
    }
}
