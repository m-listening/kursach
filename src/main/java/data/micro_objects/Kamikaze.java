package data.micro_objects;

import data.functional.Team;
import data.functional.Utilities;
import data.interfaces.LifeCycle;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.Objects;

import static app.Play.world;
import static data.functional.CONSTANTS.*;
import static data.functional.Utilities.addToWorld;
import static data.functional.Utilities.whatToDo;

public class Kamikaze implements Cloneable, Comparable<Kamikaze>, LifeCycle {
    private double x;
    private double y;
    private double aimX;
    private double aimY;
    private double maxHealth;
    private double move;
    private double health;
    private double damage;
    private double armor;
    private boolean powerUp;
    private boolean elect;
    private boolean active;
    private boolean inMacro;
    private boolean offering;
    private Team team;
    private Image image;
    private Image inFightImage;
    private ImageView imageView;
    private ImageView fightView;
    private Label name;
    private Circle circle;
    private Circle identifierTeam;
    private Rectangle rectangle;
    private Line life;
    private Murder murders;

    public Kamikaze(String name, double health) {
        move = 1;
        armor = 200;
        damage = 0.5;
        this.health = health;
        maxHealth = health;

        aimY = -1000;
        aimX = -1000;

        murders = new Murder();
        this.name = new Label(name);
        this.name.setFont(Font.font("Impact", 14));

        offering = false;
        powerUp = false;
        active = false;
        elect = false;
        team = null;
        inMacro = false;

        life = new Line(0, +15, +50, +15);
        life.setStrokeWidth(3);
        life.setStroke(Color.BLACK);


        inFightImage = Utilities.getImage("Fight");
        fightView = new ImageView(inFightImage);
        fightView.setVisible(false);

        imageView = new ImageView();

        rectangle = new Rectangle(0, 0, 65, 85);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStrokeWidth(2);
        rectangle.setStroke(Color.TRANSPARENT);

        identifierTeam = new Circle(5);

        circle = new Circle(40);
        circle.setFill(Color.TRANSPARENT);

        addToWorld(this);
        world.getAllWarriors().add(this);
        System.out.println("Конструктор викликаний.\n" + this);
    }

    public Kamikaze() {
        this("", 0);
    }

    @Override
    public void lifeCycle() {
        fight();

        if (!isElect() && !isInMacro() && isActive()) {
            if (isEmptyAim()) {
                whatToDo(this);
            } else {
                if ((Math.abs(aimX - x) + Math.abs(aimY - y)) < 5.0) {
                    if (offering) setActive(false);
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
    }

    public void fight() {
        colorHP();
        boolean inFight = false;
        if (offering) return;
        for (Kamikaze e : world.getAllWarriors()) {
            if (!e.equals(this) && e.getTeam() != this.getTeam() && !this.isInMacro() && !e.isInMacro()) {
                if (e.getImageView().getBoundsInParent().intersects(this.getCircle().getBoundsInParent())) {
                    inFight = true;
                    this.inflictDamage(e);
                    if (e.getHealth() <= 0) this.getMurders().implementCount();
                }
            }
        }
        getFightView().setVisible(inFight);
    }

    public void clearAim() {
        this.aimX = -1000;
        this.aimY = -1000;
    }

    public boolean isEmptyAim() {
        return (this.aimX < 0) && (this.aimY < 0);
    }

    public void moveActive(double stepByX, double stepByY) {
        if (borderCheckByX(stepByX))
            this.setX(this.getX() + stepByX);
        if (borderCheckByY(stepByY))
            this.setY(this.getY() + stepByY);
    }

    private boolean borderCheckByX(double stepByX) {
        return getX() + stepByX < WORLD_SIZE_WIDTH
                && getX() + stepByX > 0;
    }

    private boolean borderCheckByY(double stepByY) {
        return getY() + stepByY < WORLD_SIZE_HEIGHT
                && getY() + stepByY > 0;
    }

    @Override
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
                ", team=" + getTeam() +
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
        return Objects.equals(imageView, kamikaze.imageView) && Objects.equals(fightView, kamikaze.fightView) && Objects.equals(name, kamikaze.name) && Objects.equals(circle, kamikaze.circle) && Objects.equals(identifierTeam, kamikaze.identifierTeam) && Objects.equals(rectangle, kamikaze.rectangle) && Objects.equals(life, kamikaze.life);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageView, fightView, name, circle, identifierTeam, rectangle, life);
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

        kamikaze.powerUp = false;

        kamikaze.setName(new Label(kamikaze.getName().getText() + ".cl"));
        kamikaze.getName().setFont(Font.font("Impact", 14));

        kamikaze.setLife(new Line(0, +15, +50, +15));
        kamikaze.getLife().setStrokeWidth(3);
        kamikaze.getLife().setStroke(Color.BLACK);

        kamikaze.setRectangle(new Rectangle(0, 0, 65, 85));
        kamikaze.getRectangle().setFill(Color.TRANSPARENT);
        kamikaze.getRectangle().setStrokeWidth(2);
        kamikaze.getRectangle().setStroke(Color.TRANSPARENT);

        kamikaze.setElect(false);
        kamikaze.setInMacro(false);
        kamikaze.setOffering(false);
        kamikaze.setActive(kamikaze.isActive());

        kamikaze.setImage(Utilities.getImage(kamikaze.getClass().getSimpleName()));
        kamikaze.setImageView(new ImageView(kamikaze.getImage()));

        kamikaze.setInFightImage(Utilities.getImage("Fight"));
        kamikaze.setFightView(new ImageView(kamikaze.getInFightImage()));
        kamikaze.getFightView().setVisible(false);

        kamikaze.setIdentifierTeam(new Circle(5));
        kamikaze.setTeam(kamikaze.getTeam());

        kamikaze.setCircle(new Circle(kamikaze.getCircle().getRadius()));
        kamikaze.getCircle().setFill(Color.TRANSPARENT);

        addToWorld(kamikaze);
        world.getAllWarriors().add(kamikaze);
        kamikaze.setX(kamikaze.getX() + 50);
        kamikaze.setY(kamikaze.getY() + 50);

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

    public double getMaxHealth() {
        return maxHealth;
    }

    public ImageView getFightView() {
        return fightView;
    }

    public void setFightView(ImageView fightView) {
        this.fightView = fightView;
    }

    private void setColorIdentifierTeam() {
        if (getTeam().equals(Team.GREEN)) {
            identifierTeam.setFill(Color.GREEN);
        } else identifierTeam.setFill(Color.RED);
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public boolean isOffering() {
        return offering;
    }

    public void setInMacro(boolean inMacro) {
        this.inMacro = inMacro;
    }

    public void setOffering(boolean offering) {
        this.offering = offering;
    }

    public void flipOffering() {
        this.offering = !offering;
    }

    public Circle getIdentifierTeam() {
        return identifierTeam;
    }

    public void setIdentifierTeam(Circle identifierTeam) {
        this.identifierTeam = identifierTeam;
    }

    public boolean isPowerUp() {
        return powerUp;
    }

    public void setPowerUp(boolean powerUp) {
        this.powerUp = powerUp;
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

    public Image getInFightImage() {
        return inFightImage;
    }

    public void setInFightImage(Image inFightImage) {
        this.inFightImage = inFightImage;
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
            rectangle.setStroke(Color.AZURE);
        } else rectangle.setStroke(Color.TRANSPARENT);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
        setColorIdentifierTeam();
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

    public double getMove() {
        return move;
    }

    public void setMove(double move) {
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
        colorHP();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        this.imageView.setLayoutX(x + MICRO_IMAGE_VIEW_LAYOUT_X);
        this.rectangle.setLayoutX(x + MICRO_RECTANGLE_LAYOUT_X);
        this.circle.setLayoutX(x + MICRO_CIRCLE_LAYOUT_X);
        this.name.setLayoutX(x + MICRO_NAME_LAYOUT_X);
        this.life.setLayoutX(x + MICRO_LIFE_LAYOUT_X);
        this.identifierTeam.setLayoutX(x + MICRO_IDENTIFIER_TEAM_LAYOUT_X);
        this.fightView.setLayoutX(x + MICRO_FIGHT_VIEW_LAYOUT_X);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        this.imageView.setLayoutY(y + MICRO_IMAGE_VIEW_LAYOUT_Y);
        this.rectangle.setLayoutY(y + MICRO_RECTANGLE_LAYOUT_Y);
        this.circle.setLayoutY(y + MICRO_CIRCLE_LAYOUT_Y);
        this.name.setLayoutY(y + MICRO_NAME_LAYOUT_Y);
        this.life.setLayoutY(y + MICRO_LIFE_LAYOUT_Y);
        this.identifierTeam.setLayoutY(y + MICRO_IDENTIFIER_TEAM_LAYOUT_Y);
        this.fightView.setLayoutY(y + MICRO_FIGHT_VIEW_LAYOUT_Y);
    }

    public boolean isElect() {
        return elect;
    }

    public void flipElect() {
        setElect(!isElect());
    }

    public void setElect(boolean elect) {
        this.elect = elect;
        if (isElect())
            world.getElectedWarriors().add(this);
        else
            world.getElectedWarriors().remove(this);
        setRectangleColor();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        if (isActive()) {
            colorHP();
            world.getWarriorsActive().add(this);
        } else {
            this.life.setStroke(Color.BLACK);
            world.getWarriorsActive().remove(this);
        }
    }

    public void colorHP() {
        if (health > maxHealth / 1.5 && isActive())
            this.life.setStroke(Color.LIGHTGREEN);
        else if (health > maxHealth / 3 && isActive()) {
            this.life.setStroke(Color.LIGHTYELLOW);
        } else if (isActive()) this.life.setStroke(Color.RED);
    }

    public void flipActive() {
        setActive(!active);
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