package macro_objects;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public abstract class Base {
    double x, y;
    Image image;
    ImageView imageView;
    Circle circle;
    Label name, within;
    Group group;
    public Base(double x, double y) {
        this.x = x;
        this.y = y;

        name = new Label(null);
        name.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 16.0));
        name.setLayoutX(x);
        name.setLayoutY(y);

        within = new Label(null);
        within.setFont(Font.font("Arial", FontWeight.BOLD, 16.0));
        within.setLayoutX(x);
        within.setLayoutY(y);

        circle = new Circle(150);
        circle.setLayoutX(x);
        circle.setLayoutY(y);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.GREY);

        imageView = new ImageView();
        imageView.setLayoutX(x - 75);
        imageView.setLayoutY(y - 75);

        group = new Group(imageView, circle, name, within);
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

    public void setImage(Image image) {
        this.image = image;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
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

    public void setGroup(Group group) {
        this.group = group;
    }
}
