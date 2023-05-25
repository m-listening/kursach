package data.Methods;

import app.Play;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.macro_objects.Base;
import data.macro_objects.Bunker;
import data.macro_objects.GreenBase;
import data.macro_objects.RedBase;
import data.micro_objects.Kamikaze;
import data.micro_objects.SSO;
import data.micro_objects.SimpleSoldier;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static app.Play.globalStage;
import static app.Play.world;

public class Utilities {
    /**
     * @return null if both survive or Kamikaze to remove
     */
    public static Kamikaze fightBetweenTwo(Kamikaze o1, Kamikaze o2) {
        if (o1.equals(o2) || o1.getTeam() == o2.getTeam()
                || o1.getHealth() <= 0 || o2.getHealth() <= 0) return null;
        if (o1.getCircle().getBoundsInParent().intersects(o2.getImageView().getBoundsInParent()))
            if (o1.inflictDamage(o2)) {
                o1.getMurders().implementCount();
                return o2;
            }
        if (o2.getCircle().getBoundsInParent().intersects(o1.getImageView().getBoundsInParent()))
            if (o2.inflictDamage(o1)) {
                o2.getMurders().implementCount();
                return o1;
            }
        return null;
    }

    public static void json(Object object) throws IOException {
        Writer file = new FileWriter("data.json", true);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(file);

        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName(object.getClass().getSimpleName());
        jsonGenerator.writeString(object.toString());
        jsonGenerator.writeEndObject();
        file.write("\n");
        jsonGenerator.close();
    }

    /**
     * @Url -> Name of the .fxml file. Transfer without .fxml.
     * @title -> the name of the window
     */
    public static void showWindow(String url, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(Play.class.getResource(url + ".fxml"));
            Scene secondScene = new Scene(loader.load());

            globalStage = new Stage();
            globalStage.setTitle(title);
            globalStage.getIcons().add(new Image(new FileInputStream("src/images/icon.jpg")));
            globalStage.setScene(secondScene);
            globalStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initializeStartGame() throws FileNotFoundException {
        Bunker bunker = new Bunker(640, 360);
        GreenBase greenBase = new GreenBase(150, 360);
        RedBase redBase = new RedBase(1130, 360);

        List<Kamikaze> warriorList = new ArrayList<>();

        int number = 8, flag = 0;
        for (int i = 0; i < number; i++) {
            int rand = new Random().nextInt(3);
            if (rand == 0) {
                warriorList.add(new Kamikaze("", 100));
            } else if (rand == 1) {
                warriorList.add(new SimpleSoldier("", 100));
            } else {
                warriorList.add(new SSO("", 100));
            }
        }
        for (Kamikaze item : warriorList) {
            int lvl = 1, x, y = flag * 30;
            boolean team;
            if (item instanceof SSO) lvl = 3;
            else if (item instanceof SimpleSoldier) lvl = 2;
            if (flag < number / 2) {
                x = 300;
                team = true;
            } else {
                x = 950;
                team = false;
            }
            updateWarrior(item, x, y, lvl, team);
            world.getWarriorsActive().add(item);
            world.getAllWarriors().add(item);
            flag++;
        }

        world.getBaseSet().add(bunker);
        world.getBaseSet().add(greenBase);
        world.getBaseSet().add(redBase);
    }

    /**
     * @param lvl -> 1 -> kamikaze, 2 -> simple soldier, 3-> SSO
     * @return -> new Image, if lvl != 1,2,3, return Image lvl 1
     */
    public static Image lvlImage(int lvl, boolean isMacro) throws FileNotFoundException {
        if (isMacro) {
            if (lvl == 2) return new Image(new FileInputStream("src/images/greenB.png"), 150, 150, false, false);
            else if (lvl == 3) return new Image(new FileInputStream("src/images/bunker.png"), 150, 150, false, false);
            return new Image(new FileInputStream("src/images/redB.png"), 150, 150, false, false);
        } else {
            if (lvl == 2) return new Image(new FileInputStream("src/images/sS.png"), 50, 50, false, false);
            if (lvl == 3) return new Image(new FileInputStream("src/images/SSO.png"), 50, 50, false, false);
            return new Image(new FileInputStream("src/images/kamikaze.png"), 50, 50, false, false);
        }
    }

    public static void mousePressedHandler(MouseEvent event) throws IOException {
        if (event.isAltDown()) {
            Base base = checkClickBase(world.getBaseSet().stream().toList(), event.getX(), event.getY());
            if (base == null) return;

            if (event.getButton().equals(MouseButton.PRIMARY)) {
                world.getElectedWarriors().forEach(e -> {
                    e.flipInMacro();
                    e.flipElect();
                    world.getWorldGroup().getChildren().removeAll(e.getCircle(), e.getImageView(), e.getLife(), e.getName(), e.getRectangle());
                    base.getState().add(e);
                });
                world.getElectedWarriors().clear();
            }

            if (event.getButton().equals(MouseButton.SECONDARY)) {
                FlowPane flowPane = new FlowPane();
                HashMap<Group, Kamikaze> kamikazeHashMap = new HashMap<>(16, 0.75f);
                Scene newScene = new Scene(flowPane, 720, 480);

                base.getState().forEach(e -> {
                    Group group = new Group(e.getName(), e.getRectangle(), e.getLife(), e.getImageView());
                    kamikazeHashMap.put(group, e);
                    flowPane.getChildren().add(group);
                });
                newScene.setOnMouseClicked(e -> {
                    Kamikaze k = checkClickOnWarriorInBase(kamikazeHashMap, e.getX(), e.getY());
                    if (k == null) return;
                    k.flipInMacro();
                    flowPane.getChildren().removeAll(k.getRectangle(), k.getImageView(), k.getName(), k.getLife());
                    world.getWorldGroup().getChildren().addAll(k.getCircle(), k.getRectangle(), k.getImageView(), k.getName(), k.getLife());
                    base.getState().remove(k);
                    k.setX(base.getX() + 160);
                });
                globalStage = new Stage();
                globalStage.setTitle("Warriors in Base");
                globalStage.setScene(newScene);
                globalStage.show();
            }
            return;
        }
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            Kamikaze objectWarrior = checkClickWarrior(world.getAllWarriors().stream().toList(), event.getX(), event.getY());
            if (objectWarrior != null) {
                objectWarrior.flipActive();
                if (objectWarrior.isActive()) world.getWarriorsActive().add(objectWarrior);
                else world.getWarriorsActive().remove(objectWarrior);
                return;
            }
            showWindow("Parameters", "Parameters");
        }
        if (event.getButton().equals(MouseButton.SECONDARY)) {
            electWarrior(event.getX(), event.getY());
        }
    }

    public static void keyPressedHandler(KeyEvent event) {
        if (event.getCode().equals(KeyCode.K)) {
            for (Kamikaze item : world.getWarriorsActive()) {
                double x = new Random().nextDouble() * item.getMove();
                double y = new Random().nextDouble() * item.getMove();
                moveIfActive(item, x, y);
            }
        }
        if (event.getCode().equals(KeyCode.DELETE)) deleteWarrior(world.getElectedWarriors().stream().toList());

        if (event.getCode().equals(KeyCode.NUMPAD8)) moveIfActiveAndElect(0, -1);
        if (event.getCode().equals(KeyCode.NUMPAD4)) moveIfActiveAndElect(-1, 0);
        if (event.getCode().equals(KeyCode.NUMPAD6)) moveIfActiveAndElect(1, 0);
        if (event.getCode().equals(KeyCode.NUMPAD2)) moveIfActiveAndElect(0, 1);

        if (event.getCode().equals(KeyCode.C)) {
            if (!world.getElectedWarriors().isEmpty()) showWindow("ChangeParameters", "New parameters");
        }
        if (event.getCode().equals(KeyCode.Q)) {
            try {
                for (Kamikaze item : world.getElectedWarriors()) {
                    Kamikaze clone = (item).clone();
                    world.getAllWarriors().add(clone);
                    if (clone.isActive()) world.getWarriorsActive().add(clone);
                }
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
        if (event.getCode().equals(KeyCode.S)) {
            world.getAllWarriors().sort(Kamikaze::compareTo);
            world.getWarriorsActive().sort(Kamikaze::compareTo);
            showWindow("Search", "Search warrior");
        }
        if (event.getCode().equals(KeyCode.ESCAPE)) {
            turnOf();
        }
    }

    private static Kamikaze checkClickWarrior(List<Kamikaze> list, double x, double y) {
        for (Kamikaze item : list)
            if (item.getImageView().getBoundsInParent().contains(x, y) && !item.isInMacro())
                return item;
        return null;
    }

    private static Kamikaze checkClickOnWarriorInBase(HashMap<Group, Kamikaze> hashLow, double x, double y) {
        for (Group group : hashLow.keySet())
            if (group.getBoundsInParent().contains(x, y))
                return hashLow.get(group);
        return null;
    }

    private static Base checkClickBase(List<Base> list, double x, double y) {
        for (Base base : list)
            if (base.getGroup().boundsInParentProperty().get().contains(x, y))
                return base;
        return null;
    }

    public static void updateWarrior(Kamikaze object, double x, double y, int lvl, Boolean team) throws FileNotFoundException {
        object.setTeam(team);
        object.setImage(lvlImage(lvl, false));
        object.getImageView().setImage(object.getImage());
        object.setX(x);
        object.setY(y);
        object.setActive(true);
    }

    public static void moveIfActiveAndElect(double x, double y) {
        for (Kamikaze item : world.getElectedWarriors()) {
            if (item.isActive()) {
                item.setX(item.getX() + x * item.getMove());
                item.setY(item.getY() + y * item.getMove());
            }
        }
    }

    private static void electWarrior(double x, double y) throws IOException {
        Kamikaze objectWarrior = checkClickWarrior(world.getAllWarriors().stream().toList(), x, y);
        if (objectWarrior == null) return;

        objectWarrior.flipElect();
        if (world.getElectedWarriors().contains(objectWarrior))
            world.getElectedWarriors().remove(objectWarrior);
        else world.getElectedWarriors().add(objectWarrior);
    }

    public static void deleteWarrior(List<Kamikaze> list) {
        for (Kamikaze item : list)
            world.getWorldGroup().getChildren().removeAll(item.getCircle(), item.getImageView(), item.getLife(), item.getName(), item.getRectangle());
        list.forEach(world.getAllWarriors()::remove);
        list.forEach(world.getWarriorsActive()::remove);
        list.forEach(world.getElectedWarriors()::remove);
    }

    public static void moveIfActive(Kamikaze warrior, double x, double y) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            warrior.setX(warrior.getX() + x);
        }));
        timeline.setCycleCount(100);
        timeline.play();
    }

    private static void turnOf() {
        world.getWarriorsActive().forEach(Kamikaze::flipActive);
        world.getWarriorsActive().clear();
    }
}