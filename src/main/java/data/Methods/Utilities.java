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

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static app.Play.globalStage;
import static app.Play.world;
import static data.Methods.CONSTANTS.*;
import static data.Methods.Serialization.MicroObjectConfig.convertToConfig;

public class Utilities {

    public static void json(ArrayList<Kamikaze> arrayToJson) throws IOException {
        FileWriter file = new FileWriter("data.json", false);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(file);

        jsonGenerator.writeStartArray();
        arrayToJson.forEach(object -> {
            try {
                jsonGenerator.writeObject(convertToConfig(object));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        jsonGenerator.writeEndArray();
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

    public static void initializeStartWorld() throws FileNotFoundException {
        Bunker bunker = new Bunker(WORLD_SIZE_WIDTH_MAX / 2, WORLD_SIZE_HEIGHT_MAX / 2);
        GreenBase greenBase = new GreenBase(400, 400);
        RedBase redBase = new RedBase(WORLD_SIZE_HEIGHT_MAX - 400, WORLD_SIZE_HEIGHT_MAX - 400);

        world.setCamera();

        world.getBaseSet().add(bunker);
        world.getBaseSet().add(greenBase);
        world.getBaseSet().add(redBase);

        List<Kamikaze> warriorList = new ArrayList<>();

        int count = 40, flag = count;
        for (int i = 0; i < count; i++) {
            int rand = new Random().nextInt(0, 2);
            if (rand == 0) {
                warriorList.add(new Kamikaze("", 100));
            } else if (rand == 1) {
                warriorList.add(new SimpleSoldier("", 100));
            }
        }
        for (Kamikaze obj : warriorList) {
            int lvl = 1;
            double x, y;
            boolean team;
            if (obj instanceof SSO) lvl = 3;
            else if (obj instanceof SimpleSoldier) lvl = 2;

            team = count / 2 < flag;

            x = coordinatesBaseX(team);
            y = coordinatesBaseY(team);

            updateWarrior(obj, x, y, team);
            world.getWarriorsActive().add(obj);
            world.getAllWarriors().add(obj);

            flag--;
        }
    }

    private static double coordinatesBaseX(boolean team) {
        for (Base e : world.getBaseSet()) {
            if (e instanceof GreenBase && team)
                return e.getX();
            if (e instanceof RedBase && !team)
                return e.getX();
        }
        return 0;
    }

    private static double coordinatesBaseY(boolean team) {
        for (Base e : world.getBaseSet()) {
            if (e instanceof GreenBase && team)
                return e.getY();
            if (e instanceof RedBase && !team)
                return e.getY();
        }
        return 0;
    }

    public static Image getImage(String name) {
        switch (name) {
            case "GreenBase" -> {
                return new Image("greenB.png", 300, 300, false, false);
            }
            case "RedBase" -> {
                return new Image("redB.png", 300, 300, false, false);
            }
            case "Bunker" -> {
                return new Image("bunker.png", 300, 300, false, false);
            }
            case "Kamikaze" -> {
                return new Image("kamikaze.png", 50, 50, false, false);
            }
            case "SimpleSoldier" -> {
                return new Image("sS.png", 50, 50, false, false);
            }
            case "SSO" -> {
                return new Image("SSO.png", 50, 50, false, false);
            }
            case "Icon" -> {
                return new Image("icon.png");
            }
            case "Map" -> {
                return new Image("map.png");
            }
            case "Fight" -> {
                return new Image("fight.png", 20, 20, false, false);
            }
        }
        return null;
    }

    public static void mousePressedHandler(MouseEvent event) throws IOException {
        if (event.isAltDown()) {
            Base base = checkClickBase(world.getBaseSet().stream().toList(), event.getX(), event.getY());
            if (base == null || base instanceof Bunker) return;

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
                    flowPane.getChildren().removeAll(k.getRectangle(), k.getImageView(), k.getName(), k.getLife());
                    base.getState().remove(k);
                    k.flipInMacro();
                    addToWorld(k);
                });
                globalStage = new Stage();
                globalStage.setTitle("Warriors in Base");
                globalStage.setScene(newScene);
                globalStage.show();
            }
            return;
        }
        if (event.isControlDown()) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                Kamikaze objectWarrior = checkClickWarrior(world.getAllWarriors().stream().toList(), event.getX(), event.getY());
                if (objectWarrior != null) {
                    objectWarrior.flipActive();
                    if (objectWarrior.isActive()) world.getWarriorsActive().add(objectWarrior);
                    else world.getWarriorsActive().remove(objectWarrior);
                    return;
                }
            }
            if (event.getButton().equals(MouseButton.SECONDARY)) {
                electWarrior(event.getX(), event.getY());
            }
            return;
        }
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (world.getMiniMap().getView().contains(event.getX(), event.getY())) {
                double x = world.getCamera().calculateX(event.getX());
                double y = world.getCamera().calculateY(event.getY());
                world.getCamera().moveCameraByX(-x);
                world.getCamera().moveCameraByY(-y);
            }
        }
    }

    public static void keyPressedHandler(KeyEvent event) throws IOException {
        switch (event.getCode()) {
            case W -> {
                if (world.getCamera().getPositionY() + MOVE_CAMERA_BY_Y <= 0)
                    world.getCamera().setPositionY(1);
            }

            case S -> {
                if (Math.abs(world.getCamera().getPositionY() - MOVE_CAMERA_BY_Y) <= WORLD_CAMERA_SIZE_HEIGHT)
                    world.getCamera().setPositionY(-1);
            }

            case A -> {
                if (world.getCamera().getPositionX() + MOVE_CAMERA_BY_X <= 0)
                    world.getCamera().setPositionX(1);
            }

            case D -> {
                if (Math.abs(world.getCamera().getPositionX() - MOVE_CAMERA_BY_X) <= WORLD_CAMERA_SIZE_WIDTH)
                    world.getCamera().setPositionX(-1);
            }
            case T -> {
                world.getAllWarriors().forEach(e -> {
                    if (e.isInMacro()) {
                        world.getBaseSet().forEach(base -> base.getState().remove(e));
                    }
                    e.setActive(true);
                    if (!world.getWarriorsActive().contains(e))
                        world.getWarriorsActive().add(e);
                    e.setAimX(WORLD_SIZE_WIDTH_MAX / 2);
                    e.setAimY(WORLD_SIZE_HEIGHT_MAX / 2);
                    e.flipSaint();
                });
            }

            case U -> json((ArrayList<Kamikaze>) world.getAllWarriors());
            case NUMPAD8 -> moveIfActiveAndElect(0, -10);
            case NUMPAD4 -> moveIfActiveAndElect(-10, 0);
            case NUMPAD6 -> moveIfActiveAndElect(10, 0);
            case NUMPAD2 -> moveIfActiveAndElect(0, 10);

            case INSERT -> showWindow("Parameters", "Parameters");
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

    private static void notification(String text) {

    }

    public static void addToMacro(Kamikaze object, Base base) {
        object.flipInMacro();
        if (object.isElect()) object.flipElect();
        removeFromWorld(object);
        base.getState().add(object);
    }

    public static void removeFromMacro(Kamikaze object, Base base) {
        object.flipInMacro();
        base.getState().remove(object);
        addToWorld(object);
        object.setX(base.getX());
        object.setY(base.getY());
    }

    public static void removeFromWorld(Kamikaze object) {
        world.getWorldPane().getChildren().removeAll(object.getCircle(), object.getImageView(), object.getLife(), object.getName(), object.getRectangle(), object.getIdentifierTeam(), object.getFightView());
    }

    public static void addToWorld(Kamikaze object) {
        world.getWorldPane().getChildren().addAll(object.getCircle(), object.getImageView(), object.getLife(), object.getName(), object.getRectangle(), object.getIdentifierTeam(), object.getFightView());
    }

    private static Kamikaze checkClickWarrior(List<Kamikaze> list, double x, double y) {
        for (Kamikaze item : list)
            if (item.getImageView().getBoundsInParent().contains(
                    x - world.getWorldPane().getTranslateX(),
                    y - world.getWorldPane().getTranslateY()) && !item.isInMacro())
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
            if (base.getGroup().boundsInParentProperty().get().contains(x - world.getWorldPane().getTranslateX(),
                    y - world.getWorldPane().getTranslateY()))
                return base;
        return null;
    }

    public static void updateWarrior(Kamikaze object, double x, double y, Boolean team) {
        object.setTeam(team);
        object.setImage(getImage(object.getClass().getSimpleName()));
        object.getImageView().setImage(object.getImage());
        object.setX(x);
        object.setY(y);
        object.setActive(true);
    }

    public static void moveIfActiveAndElect(double dx, double dy) {
        for (Kamikaze item : world.getElectedWarriors()) {
            if (item.isActive()) {
                if (item.getX() + dx < WORLD_SIZE_WIDTH_MAX * 1.5 && item.getX() + dx > 0) item.setX(item.getX() + dx);
                if (item.getY() + dy < WORLD_SIZE_HEIGHT_MAX * 2 && item.getY() + dy > 0) item.setY(item.getY() + dy);
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
        for (Kamikaze object : list)
            removeFromWorld(object);
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

    public static void whatToDo(SSO sso) {
        int rand = new Random().nextInt(0, 3);
        if (rand == 1) whatToDo((Kamikaze) sso);
        else {
            for (Kamikaze obj : world.getAllWarriors())
                if (sso.getTeam() != obj.getTeam() && new Random().nextBoolean())
                    sso.setAim(obj);
        }
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
            kamikaze.setAimX(new Random().nextDouble() * WORLD_SIZE_WIDTH_MAX);
            kamikaze.setAimY(new Random().nextDouble() * WORLD_SIZE_HEIGHT_MAX);
        }
    }

    public static boolean boundsIntersectOtherBounds(Kamikaze object, Base base) {
        return object.getImageView().getBoundsInParent().intersects(base.getGroup().getBoundsInParent()) && !base.getState().contains(object);
    }

    public static void interactionWithMacro(Base base, boolean teamBase) {
        for (Kamikaze object : world.getAllWarriors()) {
            if (object.isOffering()) continue;
            if (boundsIntersectOtherBounds(object, base) && object.getTeam() != teamBase)
                base.inflictDamage(object);
            if (boundsIntersectOtherBounds(object, base) && object.getTeam() == teamBase && !base.getState().contains(object) && base.getState().size() < 3) {
                if (!(object instanceof SSO))
                    if (new Random().nextInt(0, 1000) == 3)
                        world.addToBase(object, base);
            }
        }
    }
}