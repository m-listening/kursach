package Methods;

import app.kursova.Game;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import macro_objects.Base;
import macro_objects.Bunker;
import macro_objects.GreenBase;
import macro_objects.RedBase;
import micro_objects.Kamikaze;
import micro_objects.SSO;
import micro_objects.SimpleSoldier;
import micro_objects.Warrior;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static app.kursova.Game.globalStage;
import static app.kursova.Game.world;
import static app.kursova.World.*;

public class Utilities {
    private static final List<Warrior> warriorsToRemove = new ArrayList<>();

    /**
     * @Url -> Name of the .fxml file. Transfer without .fxml.
     * @title -> the name of the window
     */
    public static void showWindow(String url, String title) {
        Parent root;
        FXMLLoader loader = new FXMLLoader(Game.class.getResource(url + ".fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene secondScene = new Scene(root);

        globalStage = new Stage();
        globalStage.setTitle(title);
        globalStage.setScene(secondScene);
        globalStage.showAndWait();
    }

    /**
     * @param lvl -> 1 -> kamikaze, 2 -> simple soldier, 3-> SSO
     * @return -> new Image, or null if lvl != 1,2,3
     */
    public static Image lvlImage(int lvl) throws FileNotFoundException {
        if (lvl == 2)
            return new Image(new FileInputStream("src/images/simpleSoldier.png"), 50, 50, false, false);
        if (lvl == 3)
            return new Image(new FileInputStream("src/images/SSO.png"), 50, 50, false, false);
        return new Image(new FileInputStream("src/images/kamikaze.png"), 50, 50, false, false);
    }

    public static void initializeStartGame(Group group) throws FileNotFoundException, CloneNotSupportedException {
        Bunker bunker = new Bunker(640, 360);
        GreenBase greenBase = new GreenBase(150, 360);
        RedBase redBase = new RedBase(1130, 360);

        List<Warrior> warriorList = new ArrayList<>();
        warriorList.add(new Kamikaze("wee", 50));
        warriorList.add(new SimpleSoldier("wee", 75));
        warriorList.add(new SSO("wee", 100));
        warriorList.add(((Kamikaze) (warriorList.get(0))).clone());
        warriorList.add(((Kamikaze) (warriorList.get(1))).clone());
        warriorList.add(((Kamikaze) (warriorList.get(2))).clone());

        int flag = 1;
        for (Warrior item : warriorList) {
            if (item instanceof SSO) item.setImage(lvlImage(3));
            else if (item instanceof SimpleSoldier) item.setImage(lvlImage(2));
            else if (item instanceof Kamikaze) item.setImage(lvlImage(1));
            if (flag < 4) {
                item.setX(300);
                item.setTeam(true);
            } else {
                item.setX(950);
                item.setTeam(false);
            }
            item.getImageView().setImage(item.getImage());
            item.setActive(true);
            item.setY(110 * flag);
            group.getChildren().add(item.getGroup());
            warriorsActive.add((Kamikaze) item);
            warriors.add((Kamikaze) item);
            flag++;
        }

        bases.add(bunker);
        bases.add(greenBase);
        bases.add(redBase);
        group.getChildren().addAll(
                bunker.getGroup(),
                greenBase.getGroup(),
                redBase.getGroup());
    }

    public static void json(String object, String action) throws IOException {
        Writer file = new FileWriter("data.json", true);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(file);

        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName(object);
        jsonGenerator.writeString(action);
        jsonGenerator.writeEndObject();
        file.write("\n");
        jsonGenerator.close();
    }

    public static void mousePressedHandler(MouseEvent event) throws IOException {
        if (event.isAltDown()) {
            Base base = checkClickBase(bases.stream().toList(), event.getX(), event.getY());
            if (base == null) return;

            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (warriorsElect.isEmpty()) return;

                for (Warrior item : warriorsElect) {
                    item.setElect(false);
                    item.setActive(false);
                    warriorsActive.remove((Kamikaze) item);
                    world.getMainGroup().getChildren().remove(item.getGroup());
                    if (base instanceof GreenBase) {
                        ((GreenBase) base).getPersonnel().add(item);
                        base.setWithin(((GreenBase) base).getPersonnel().size());
                    } else if (base instanceof RedBase) {
                        ((RedBase) base).getPersonnel().add(item);
                        base.setWithin(((RedBase) base).getPersonnel().size());
                    } else if (base instanceof Bunker) {
                        ((Bunker) base).addWarrior(item);
                        base.setWithin(((Bunker) base).getGreenWarriors().size() +
                                ((Bunker) base).getRedWarriors().size() +
                                ((Bunker) base).getNobodyWarriors().size());
                    }
                    item.setInMacro(true);
                }
                warriorsElect.clear();
            }

            if (event.getButton().equals(MouseButton.SECONDARY)) {
                HBox hBox = new HBox(30);
                int flag;

                if (base instanceof GreenBase) {
                    flag = 1;
                    for (Warrior item : ((GreenBase) base).getPersonnel())
                        hBox.getChildren().add(item.getGroup());
                } else if (base instanceof RedBase) {
                    flag = 2;
                    for (Warrior item : ((RedBase) base).getPersonnel())
                        hBox.getChildren().add(item.getGroup());
                } else if (base instanceof Bunker) {
                    flag = 3;
                    for (Warrior item : ((Bunker) base).getGreenWarriors())
                        hBox.getChildren().add(item.getGroup());
                    for (Warrior item : ((Bunker) base).getRedWarriors())
                        hBox.getChildren().add(item.getGroup());
                    for (Warrior item : ((Bunker) base).getNobodyWarriors())
                        hBox.getChildren().add(item.getGroup());
                } else {
                    flag = 0;
                }

                globalStage = new Stage();
                Scene newScene = new Scene(hBox, 720, 480);

                newScene.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY)) {
                        Kamikaze warrior = checkClickWarrior(warriors.stream().toList(), e.getX(), e.getY(), true);
                        if (warrior == null) return;
                        if (flag == 1) {
                            ((GreenBase) base).getPersonnel().remove(warrior);
                            base.setWithin(((GreenBase) base).getPersonnel().size());
                        } else if (flag == 2) {
                            ((RedBase) base).getPersonnel().remove(warrior);
                            base.setWithin(((RedBase) base).getPersonnel().size());
                        } else if (flag == 3) {
                            ((Bunker) base).getGreenWarriors().remove(warrior);
                            ((Bunker) base).getRedWarriors().remove(warrior);
                            ((Bunker) base).getNobodyWarriors().remove(warrior);
                            base.setWithin(((Bunker) base).getGreenWarriors().size() +
                                    ((Bunker) base).getRedWarriors().size() +
                                    ((Bunker) base).getNobodyWarriors().size());
                        }
                        hBox.getChildren().remove(warrior.getGroup());

                        warrior.setInMacro(false);

                        world.getMainGroup().getChildren().add(warrior.getGroup());
                        warrior.getGroup().setLayoutX(warrior.getX());
                        warrior.getGroup().setLayoutY(warrior.getY());
                    }
                });

                globalStage.setTitle("Warriors in Base");
                globalStage.setScene(newScene);
                globalStage.show();
            }

            return;
        }
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            Kamikaze objectWarrior = checkClickWarrior(warriors.stream().toList(), event.getX(), event.getY(), false);
            if (objectWarrior != null) {
                objectWarrior.flipActive();
                if (objectWarrior.isActive()) warriorsActive.add(objectWarrior);
                else warriorsActive.remove(objectWarrior);
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
            for (Warrior item : warriorsActive) {
                double x = new Random().nextDouble() * item.getMove();
                double y = new Random().nextDouble() * item.getMove();

                moveIfActive(item, x, y);
            }
        }
        if (event.getCode().equals(KeyCode.DELETE)) {
            deleteWarrior();
        }
        if (event.getCode().equals(KeyCode.NUMPAD8)) {
            moveIfActiveAndElect(0, -1);
        }
        if (event.getCode().equals(KeyCode.NUMPAD4)) {
            moveIfActiveAndElect(-1, 0);
        }
        if (event.getCode().equals(KeyCode.NUMPAD6)) {
            moveIfActiveAndElect(1, 0);
        }
        if (event.getCode().equals(KeyCode.NUMPAD2)) {
            moveIfActiveAndElect(0, 1);
        }
        if (event.getCode().equals(KeyCode.C)) {
            if (warriorsElect.isEmpty())
                return;
            showWindow("ChangeParameters", "New parameters");
        }
        if (event.getCode().equals(KeyCode.Q)) {
            try {
                for (Warrior item : warriorsElect) {
                    Kamikaze kamikaze = ((Kamikaze) item).clone();
                    warriors.add(kamikaze);
                    world.getMainGroup().getChildren().add(kamikaze.getGroup());
                }
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
        if (event.getCode().equals(KeyCode.S)) {
            warriors.sort(Kamikaze::compareTo);
            showWindow("Search", "Search warrior");
        }
        if (event.getCode().equals(KeyCode.ESCAPE)) {
            turnOf();
        }
    }

    /**
     * @param x  -> event.getX()
     * @param y  -> event.getY()
     * @param in -> True = in macro, False = nowhere
     * @return kamikaze or null
     */
    private static Kamikaze checkClickWarrior(List<Kamikaze> list, double x, double y, boolean in) {
        for (Kamikaze item : list) {
            if (item.getGroup()
                    .getBoundsInParent().contains(x, y)) {
                if (item.isInMacro() && in)
                    return item;
                if (!item.isInMacro() && !in)
                    return item;
            }
        }
        return null;
    }

    private static Base checkClickBase(List<Base> list, double x, double y) {
        for (Base base : list) {
            if (base.getGroup().boundsInParentProperty().get().contains(x, y))
                return base;
        }
        return null;
    }

    private static void electWarrior(double x, double y) {
        Kamikaze objectWarrior = checkClickWarrior(warriors.stream().toList(), x, y, false);
        if (objectWarrior == null) return;

        if (warriorsElect.contains(objectWarrior)) {
            objectWarrior.flipElect();
            warriorsElect.remove(objectWarrior);
        } else {
            objectWarrior.flipElect();
            warriorsElect.add(objectWarrior);
        }
    }

    public static void moveIfActiveAndElect(double x, double y) {
        for (Warrior item : warriorsElect) {
            if (item.isActive()) {
                item.setX(item.getX() + x * item.getMove());
                item.setY(item.getY() + y * item.getMove());
            }
        }
    }

    private static void deleteWarrior() {
        for (Warrior item : warriorsElect) {
            world.getMainGroup().getChildren().remove(item.getGroup());
        }
        warriors.removeAll(warriorsElect);
        warriorsActive.removeAll(warriorsElect);
        warriorsElect.clear();
    }

    private static void turnOf() {
        for (Kamikaze item : warriorsActive) {
            item.setActive(false);
        }
        warriorsActive.clear();
    }

    public static void moveIfActive(Warrior warrior, double x, double y) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            warrior.setX(warrior.getX() + x);
        }));
        timeline.setCycleCount(100);
        timeline.play();
    }
}