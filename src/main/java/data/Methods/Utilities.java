package data.Methods;

import app.Play;
import data.macro_objects.Base;
import data.macro_objects.Bunker;
import data.macro_objects.GreenBase;
import data.macro_objects.RedBase;
import data.micro_objects.Kamikaze;
import data.micro_objects.SSO;
import data.micro_objects.SimpleSoldier;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static app.Play.globalStage;
import static app.Play.world;
import static data.Methods.CONSTANTS.*;
import static data.Methods.Team.*;

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
            globalStage.getIcons().add(new Image("icon.jpg"));
            globalStage.setScene(secondScene);
            globalStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createStartWorld() {
        Bunker bunker = new Bunker(MACRO_BUNKER_LAYOUT_X, MACRO_BUNKER_LAYOUT_Y);
        GreenBase greenBase = new GreenBase(MACRO_GREEN_BASE_LAYOUT_X, MACRO_GREEN_BASE_LAYOUT_Y);
        RedBase redBase = new RedBase(MACRO_RED_BASE_LAYOUT_X, MACRO_RED_BASE_LAYOUT_Y);

        world.getBaseSet().add(bunker);
        world.getBaseSet().add(greenBase);
        world.getBaseSet().add(redBase);
    }

    public static void createMicroObjects(int count) {
        List<Kamikaze> warriorList = createWarriors(count);
        setCoordinatesForWarriors(warriorList);
    }

    private static List<Kamikaze> createWarriors(int count) {
        List<Kamikaze> warriorList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (i % 2 == 0) warriorList.add(new Kamikaze("Agent " + i, 200));
            else warriorList.add(new SimpleSoldier("Agent " + i, 220));
        }
        return warriorList;
    }

    private static void setCoordinatesForWarriors(List<Kamikaze> warriors) {
        int count = warriors.size();
        int flag = count;
        for (Kamikaze obj : warriors) {
            double x, y;

            Team team = (count / 2 < flag) ? GREEN : RED;

            x = coordinatesBaseX(team);
            y = coordinatesBaseY(team);

            updateWarrior(obj, x, y, team);

            flag--;
        }
    }

    private static double coordinatesBaseX(Team team) {
        for (Base e : world.getBaseSet()) {
            if (e instanceof GreenBase && team.equals(GREEN))
                return e.getX();
            if (e instanceof RedBase && team.equals(RED))
                return e.getX();
        }
        return 0;
    }

    private static double coordinatesBaseY(Team team) {
        for (Base e : world.getBaseSet()) {
            if (e instanceof GreenBase && team.equals(GREEN))
                return e.getY();
            if (e instanceof RedBase && team.equals(RED))
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
                    removeFromMacro(k, base);
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

    public static void keyPressedHandler(KeyEvent event) throws IOException, ClassNotFoundException {
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
            case T -> world.getAllWarriors().forEach(e -> {
                if (e.isInMacro()) world.getBaseSet().forEach(base -> Utilities.removeFromMacro(e, base));
                e.setActive(true);
                e.setElect(false);
                e.setAimX(MACRO_BUNKER_LAYOUT_X);
                e.setAimY(MACRO_BUNKER_LAYOUT_Y);
                e.flipOffering();
            });

            case F1 -> saveDataToFile();
            case F2 -> openDataFile();
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
                    for (Kamikaze item : world.getElectedWarriors())
                        item.clone();
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
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        Label label = new Label(text);
        Button showChosen = new Button("Show chosen");
        vBox.getChildren().addAll(label, showChosen);

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
        pauseTransition.setOnFinished(e -> globalStage.close());
        pauseTransition.play();
        showChosen.setOnAction(e -> {
            pauseTransition.stop();
            globalStage.close();
            showChosen();
        });

        globalStage = new Stage();
        globalStage.setScene(new Scene(vBox, 100, 100));
        globalStage.setTitle("Notification");
        globalStage.show();
    }

    public static void addToMacro(Kamikaze object, Base base) {
        removeFromWorld(object);
        object.flipInMacro();
        if (object.isElect()) object.flipElect();
        base.getState().add(object);
    }

    public static void removeFromMacro(Kamikaze object, Base base) {
        if (base.getState().remove(object)) {
            addToWorld(object);
            object.flipInMacro();
            object.setX(base.getX());
            object.setY(base.getY());
        }
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

    public static void updateWarrior(Kamikaze object, double x, double y, Team team) {
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
                item.moveActive(dx, dy);
            }
        }
    }

    private static void electWarrior(double x, double y) {
        Kamikaze objectWarrior = checkClickWarrior(world.getAllWarriors().stream().toList(), x, y);
        if (objectWarrior == null) return;

        objectWarrior.flipElect();
        notification("Chosen: " + world.getElectedWarriors().size());
    }

    private static void showChosen() {
        VBox vBox = new VBox();
        ObservableList<String> items = FXCollections.observableArrayList();
        ListView<String> listView = new ListView<>();
        world.getElectedWarriors().forEach(e -> items.add(e.toString()));
        listView.setItems(items);
        vBox.getChildren().add(listView);
        globalStage = new Stage();
        globalStage.setScene(new Scene(vBox, 800, 200));
        globalStage.setTitle("Chosen warriors");
        globalStage.show();
    }

    public static void deleteWarrior(List<Kamikaze> list) {
        world.getBaseSet().forEach(base -> list.forEach(obj -> base.getState().remove(obj)));
        list.forEach(Utilities::removeFromWorld);
        world.getAllWarriors().removeAll(list);
        list.forEach(e -> e.setActive(false));
        list.forEach(e -> e.setElect(false));
    }

    private static void turnOf() {
        List<Kamikaze> forFlip = world.getAllWarriors().subList(0, world.getAllWarriors().size());
        forFlip.forEach(e -> e.setActive(false));
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
                if (base instanceof GreenBase && kamikaze.getTeam().equals(GREEN)) {
                    kamikaze.setAimX(base.getX());
                    kamikaze.setAimY(base.getY());
                    return;
                }
                if (base instanceof RedBase && kamikaze.getTeam().equals(RED)) {
                    kamikaze.setAimX(base.getX());
                    kamikaze.setAimY(base.getY());
                    return;
                }
            }
        } else {
            kamikaze.setAimX(new Random().nextDouble() * WORLD_SIZE_WIDTH);
            kamikaze.setAimY(new Random().nextDouble() * WORLD_SIZE_HEIGHT);
        }
    }

    public static boolean boundsIntersectOtherBounds(Kamikaze object, Base base) {
        return object.getImageView().getBoundsInParent().intersects(base.getGroup().getBoundsInParent()) && !base.getState().contains(object);
    }

    public static void interactionWithMacro(Base base, Team teamBase) {
        for (Kamikaze object : world.getAllWarriors()) {
            if (object.isOffering()) continue;
            if (boundsIntersectOtherBounds(object, base) && !object.getTeam().equals(teamBase))
                base.inflictDamage(object);
            if (boundsIntersectOtherBounds(object, base) && object.getTeam().equals(teamBase) && !base.getState().contains(object) && base.getState().size() < 3) {
                if (!(object instanceof SSO))
                    if (new Random().nextInt(0, 1000) == 3)
                        world.addToBase(object, base);
            }
        }
    }

    public static void saveDataToFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.setInitialFileName("data.json");
        FileChooser.ExtensionFilter textFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(textFilter);
        File file = fileChooser.showSaveDialog(globalStage);
        if (file != null) {
            JSON.saveData(world.getAllWarriors(), file.getPath());
        } else {
            System.out.println("Вибір файлу скасовано.");
        }
    }

    private static void openDataFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\"));
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("JSON files(*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(globalStage);
        JSON.parseData(file);
    }
}