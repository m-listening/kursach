package data.Methods;

import app.Play;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import data.macro_objects.Base;
import data.macro_objects.Bunker;
import data.macro_objects.GreenBase;
import data.macro_objects.RedBase;
import data.micro_objects.Kamikaze;
import data.micro_objects.SSO;
import data.micro_objects.SimpleSoldier;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static app.Play.globalStage;
import static app.Play.world;

public class Utilities {
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
            globalStage.setScene(secondScene);
            globalStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            world.getWarriors().add(item);
            flag++;
        }

        world.getBases().add(bunker);
        world.getBases().add(greenBase);
        world.getBases().add(redBase);
    }

    public static void json(String objectType, String state) throws IOException {
        Writer file = new FileWriter("data.json", true);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(file);

        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName(objectType);
        jsonGenerator.writeString(state);
        jsonGenerator.writeEndObject();
        file.write("\n");
        jsonGenerator.close();
    }

    public static void mousePressedHandler(MouseEvent event) throws IOException {
        if (event.isAltDown()) {
            Base base = checkClickBase(world.getBases().stream().toList(), event.getX(), event.getY());
            if (base == null) return;

            if (event.getButton().equals(MouseButton.PRIMARY)) {
                world.getWarriorsElect().forEach(e -> {
                    e.flipInMacro();
                    e.setElect(false);
                    world.getWorldGroup().getChildren().removeAll(e.getCircle(), e.getImageView(), e.getLife(), e.getName(), e.getRectangle());
                    base.getState().add(e);
                });
                world.getWarriorsElect().clear();
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
                    world.getMainGroup().getChildren().addAll(k.getCircle(), k.getRectangle(), k.getImageView(), k.getName(), k.getLife());
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
            Kamikaze objectWarrior = checkClickWarrior(world.getWarriors().stream().toList(), event.getX(), event.getY());
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
        if (event.getCode().equals(KeyCode.DELETE)) deleteWarrior(world.getWarriorsElect().stream().toList());

        if (event.getCode().equals(KeyCode.NUMPAD8)) moveIfActiveAndElect(0, -1);
        if (event.getCode().equals(KeyCode.NUMPAD4)) moveIfActiveAndElect(-1, 0);
        if (event.getCode().equals(KeyCode.NUMPAD6)) moveIfActiveAndElect(1, 0);
        if (event.getCode().equals(KeyCode.NUMPAD2)) moveIfActiveAndElect(0, 1);

        if (event.getCode().equals(KeyCode.C)) {
            if (!world.getWarriorsElect().isEmpty()) showWindow("ChangeParameters", "New parameters");
        }
        if (event.getCode().equals(KeyCode.Q)) {
            try {
                for (Kamikaze item : world.getWarriorsElect()) {
                    Kamikaze clone = (item).clone();
                    world.getWarriors().add(clone);
                    if (clone.isActive()) world.getWarriorsActive().add(clone);
                }
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
        if (event.getCode().equals(KeyCode.S)) {
            world.getWarriors().sort(Kamikaze::compareTo);
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

    private static void electWarrior(double x, double y) {
        Kamikaze objectWarrior = checkClickWarrior(world.getWarriors().stream().toList(), x, y);
        if (objectWarrior == null) return;

        if (world.getWarriorsElect().contains(objectWarrior)) {
            objectWarrior.flipElect();
            world.getWarriorsElect().remove(objectWarrior);
        } else {
            objectWarrior.flipElect();
            world.getWarriorsElect().add(objectWarrior);
        }
    }

    public static void moveIfActiveAndElect(double x, double y) {
        for (Kamikaze item : world.getWarriorsElect()) {
            if (item.isActive()) {
                item.setX(item.getX() + x * item.getMove());
                item.setY(item.getY() + y * item.getMove());
            }
        }
    }

    public static void deleteWarrior(List<Kamikaze> list) {
        for (Kamikaze item : list)
            world.getWorldGroup().getChildren().removeAll(item.getCircle(), item.getImageView(), item.getLife(), item.getName(), item.getRectangle());
        list.forEach(world.getWarriors()::remove);
        list.forEach(world.getWarriorsActive()::remove);
        list.forEach(world.getWarriorsElect()::remove);
    }

    private static void turnOf() {
        world.getWarriorsActive().forEach(Kamikaze::flipActive);
        world.getWarriorsActive().clear();
    }

    public static void moveIfActive(Kamikaze warrior, double x, double y) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            warrior.setX(warrior.getX() + x);
        }));
        timeline.setCycleCount(100);
        timeline.play();
    }

    public static void updateWarrior(Kamikaze object, double x, double y, int lvl, Boolean team) throws FileNotFoundException {
        object.setTeam(team);
        object.setImage(lvlImage(lvl, false));
        object.getImageView().setImage(object.getImage());
        object.setX(x);
        object.setY(y);
        object.setActive(true);
    }
}