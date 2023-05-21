package data.macro_objects;

import data.Methods.Utilities;
import app.Play;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import data.micro_objects.Kamikaze;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

public abstract class Base {
    private double x, y;
    private final Image image;
    private final ImageView imageView;
    private final Circle circle;
    private final Label name, within;
    private final Group group;
    private final Set<Kamikaze> state;

    public Base(int typeLvl) throws FileNotFoundException {
        state = new HashSet<>();

        name = new Label();
        name.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 16.0));
        name.setLayoutX(-45);
        name.setLayoutY(-100);

        within = new Label();
        within.setFont(Font.font("Arial", FontWeight.BOLD, 16.0));
        within.setLayoutX(-5);
        within.setLayoutY(100);

        circle = new Circle(150);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.GREY);

        image = Utilities.lvlImage(typeLvl, true);
        imageView = new ImageView(image);
        imageView.setLayoutX(-75);
        imageView.setLayoutY(-75);

        group = new Group(imageView, circle, name, within);
        Play.world.getWorldGroup().getChildren().add(group);
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
