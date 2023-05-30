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
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static app.Play.*;

public class Utilities {

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
            globalStage.getIcons().add(new Image("icon.jpg"));
            globalStage.setScene(secondScene);
            globalStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initializeStartGame() throws FileNotFoundException {
        Bunker bunker = new Bunker(sceneSizeMaxX * 1.5 / 2, sceneSizeMaxY);
        GreenBase greenBase = new GreenBase(400, sceneSizeMaxY);
        RedBase redBase = new RedBase(sceneSizeMaxX * 1.5 - 400, sceneSizeMaxY);

        List<Kamikaze> warriorList = new ArrayList<>();

        int number = 200, flag = number;
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
            int lvl = 1;
            double x, y = sceneSizeMaxY / 2;
            boolean team;
            if (item instanceof SSO) lvl = 3;
            else if (item instanceof SimpleSoldier) lvl = 2;
            if (number / 2 < flag) {
                x = 0;
                team = true;
            } else {
                x = sceneSizeMaxX * 1.5;
                team = false;
            }
            flag--;
            updateWarrior(item, x, y, lvl, team);
            world.getWarriorsActive().add(item);
            world.getAllWarriors().add(item);
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
            if (lvl == 2) return new Image("greenB.png", 300, 300, false, false);
            else if (lvl == 3) return new Image("bunker.png", 300, 300, false, false);
            return new Image("redB.png", 300, 300, false, false);
        } else {
            if (lvl == 2) return new Image("sS.png", 50, 50, false, false);
            if (lvl == 3) return new Image("SSO.png", 50, 50, false, false);
            return new Image("kamikaze.png", 50, 50, false, false);
        }
    }

    public static void mousePressedHandler(MouseEvent event) throws IOException {
        if (event.isAltDown()) {
            Base base = checkClickBase(world.getBaseSet().stream().toList(), event.getX(), event.getY());
            if (base == null || base instanceof  Bunker) return;

            if (event.getButton().equals(MouseButton.PRIMARY)) {
                world.getElectedWarriors().forEach(e -> addToMacro(e, base));
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
        switch (event.getCode()) {
            case W -> {
                if (world.getWorldGroup().getTranslateY() + 20 <= 0)
                    world.getWorldGroup().setTranslateY(world.getWorldGroup().getTranslateY() + 20);
            }

            case A -> {
                if (world.getWorldGroup().getTranslateX() + 20 <= 0)
                    world.getWorldGroup().setTranslateX(world.getWorldGroup().getTranslateX() + 20);
            }

            case S -> {
                if (Math.abs(world.getWorldGroup().getTranslateY() - 20) <= sceneSizeMaxY)
                    world.getWorldGroup().setTranslateY(world.getWorldGroup().getTranslateY() - 20);
            }

            case D -> {
                if (Math.abs(world.getWorldGroup().getTranslateX() - 20) <= sceneSizeMaxX)
                    world.getWorldGroup().setTranslateX(world.getWorldGroup().getTranslateX() - 20);
            }


            case NUMPAD8 -> moveIfActiveAndElect(0, -10);
            case NUMPAD4 -> moveIfActiveAndElect(-10, 0);
            case NUMPAD6 -> moveIfActiveAndElect(10, 0);
            case NUMPAD2 -> moveIfActiveAndElect(0, 10);

            case DELETE -> deleteWarrior(world.getElectedWarriors().stream().toList());
            case C -> {
                if (!world.getElectedWarriors().isEmpty()) showWindow("ChangeParameters", "New parameters");
            }
            case Q -> {
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
            case I -> {
                world.getAllWarriors().sort(Kamikaze::compareTo);
                world.getWarriorsActive().sort(Kamikaze::compareTo);
                showWindow("Search", "Search warrior");
            }
            case ESCAPE -> turnOf();
        }
    }

    public static void addToMacro(Kamikaze object, Base base) {
        object.flipInMacro();
        if (object.isElect()) object.flipElect();
        world.getWorldGroup().getChildren().removeAll(
                object.getCircle(), object.getImageView(), object.getLife(), object.getName(), object.getRectangle());
        base.getState().add(object);
    }

    public static void removeFromMacro(Kamikaze object, Base base) {
        object.flipInMacro();
        base.getState().remove(object);
        world.getWorldGroup().getChildren().addAll(
                object.getCircle(), object.getImageView(), object.getLife(), object.getName(), object.getRectangle());
    }

    private static Kamikaze checkClickWarrior(List<Kamikaze> list, double x, double y) {
        for (Kamikaze item : list)
            if (item.getImageView().getBoundsInParent().contains(x - world.getWorldGroup().getTranslateX(),
                    y - world.getWorldGroup().getTranslateY()) && !item.isInMacro())
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

    public static void moveIfActiveAndElect(double dx, double dy) {
        for (Kamikaze item : world.getElectedWarriors()) {
            if (item.isActive()) {
                if (item.getX() + dx < sceneSizeMaxX * 1.5 && item.getX() + dx > 0) item.setX(item.getX() + dx);
                if (item.getY() + dy < sceneSizeMaxY * 2 && item.getY() + dy > 0) item.setY(item.getY() + dy);
            }
        }
    }

    private static void electWarrior(double x, double y) {
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
        list.forEach(obj -> {
            world.getBaseSet().forEach(base -> {
                base.getState().remove(obj);
            });
        });
    }

    private static void turnOf() {
        world.getWarriorsActive().forEach(Kamikaze::flipActive);
        world.getWarriorsActive().clear();
    }

    public static void whatToDo(Kamikaze kamikaze) {
        int rand = new Random().nextInt(0, 3);
        if (rand == 1)
            for (Kamikaze e : world.getAllWarriors()) {
                if (kamikaze.getTeam() != e.getTeam()) {
                    kamikaze.setAimX(e.getX());
                    kamikaze.setAimY(e.getY());
                    return;
                }
            }
        else if (rand == 2) {
            for (Base base : world.getBaseSet()) {
                if (base instanceof GreenBase && kamikaze.getTeam()) {
                    kamikaze.setAimX(base.getX());
                    kamikaze.setAimY(base.getY());
                    return;
                }
                if (base instanceof RedBase && !kamikaze.getTeam()) {
                    kamikaze.setAimX(base.getX());
                    kamikaze.setAimY(base.getY());
                    return;
                }
            }
        } else {
            kamikaze.setAimX(new Random().nextDouble() * sceneSizeMinX * 1.5);
            kamikaze.setAimY(new Random().nextDouble() * sceneSizeMaxY * 2);
        }
    }
}