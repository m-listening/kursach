package Methods;

import app.kursova.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import macro_objects.Base;
import macro_objects.Bunker;
import macro_objects.GreenBase;
import macro_objects.RedBase;
import micro_objects.Kamikaze;
import micro_objects.Warrior;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static app.kursova.Game.*;

public class Utilities {
    private static final HashMap<Kamikaze, Boolean> warriorsToRemove = new HashMap<>();

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
        if (lvl == 1) {
            return new Image(new FileInputStream("src/images/kamikaze.png"), 50, 50, false, false);
        }
        if (lvl == 2) {
            return new Image(new FileInputStream("src/images/simpleSoldier.png"), 50, 50, false, false);
        }
        if (lvl == 3) {
            return new Image(new FileInputStream("src/images/SSO.png"), 50, 50, false, false);
        }
        return null;
    }

    public static void initializeStartGame() throws FileNotFoundException {
        Bunker bunker = new Bunker(640, 360);
        GreenBase greenBase = new GreenBase(150, 360);
        RedBase redBase = new RedBase(1130, 360);

        bases.add(bunker);
        bases.add(greenBase);
        bases.add(redBase);

        mainGroup.getChildren().addAll(
                bunker.getGroup(),
                greenBase.getGroup(),
                redBase.getGroup());

    }

    public static void mousePressedHandler(MouseEvent event) {
        if (event.isAltDown()) {
            Base base = checkClickBase(bases.stream().toList(), event.getX(), event.getY());
            if (base == null) return;

            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (warriorElect == null) return;

                warriorElect.setElect(false);
                warriorElect.setActive(false);
                /*warriorElect.getGroup().setVisible(false);*/
                mainGroup.getChildren().remove(warriorElect.getGroup());

                if (base instanceof GreenBase) {
                    ((GreenBase) base).getPersonnel().add(warriorElect);
                    base.setWithin(((GreenBase) base).getPersonnel().size());
                } else if (base instanceof RedBase) {
                    ((RedBase) base).getPersonnel().add(warriorElect);
                    base.setWithin(((RedBase) base).getPersonnel().size());
                } else if (base instanceof Bunker) {
                    ((Bunker) base).addWarrior(warriorElect);
                    base.setWithin(((Bunker) base).getGreenWarriors().size() +
                            ((Bunker) base).getRedWarriors().size() +
                            ((Bunker) base).getNobodyWarriors().size());
                }
                warriorElect.setInMacro(true);

                warriorElect = null;
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
                        Kamikaze warrior = checkClickWarrior(warriors.stream().toList(), e.getX(), e.getY());
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

                        mainGroup.getChildren().add(warrior.getGroup());
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
            Kamikaze objectWarrior = checkClickWarrior(warriors.stream().toList(), event.getX(), event.getY());
            if (objectWarrior != null) {
                objectWarrior.setActive();
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
        if (event.getCode().equals(KeyCode.DELETE)) {
            deleteWarrior();
        }
        if (event.getCode().equals(KeyCode.NUMPAD8)) {
            moveIfElect(0, -10);
        }
        if (event.getCode().equals(KeyCode.NUMPAD4)) {
            moveIfElect(-10, 0);
        }
        if (event.getCode().equals(KeyCode.NUMPAD6)) {
            moveIfElect(10, 0);
        }
        if (event.getCode().equals(KeyCode.NUMPAD2)) {
            moveIfElect(0, 10);
        }
        if (event.getCode().equals(KeyCode.C)) {
            if (Game.warriorElect == null)
                return;
            showWindow("ChangeParameters", "New parameters");
        }
        if (event.getCode().equals(KeyCode.Q)) {
            try {
                if (warriorElect != null) {
                    Kamikaze kamikaze = warriorElect.clone();
                    warriors.add(kamikaze);
                    mainGroup.getChildren().add(kamikaze.getGroup());
                }
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
        if (event.getCode().equals(KeyCode.S)) {
            ObservableList<Warrior> listWarriors = FXCollections.observableArrayList(warriors);
            ListView<Warrior> listView = new ListView<>(listWarriors);

            globalStage = new Stage();
            Scene secondScene = new Scene(listView, 400, 300);
            globalStage.setScene(secondScene);
            globalStage.show();
        }
        if (event.getCode().equals(KeyCode.ESCAPE)) {
            turnOf();
        }
    }

    private static Kamikaze checkClickWarrior(List<Kamikaze> list, double x, double y) {
        for (Kamikaze item : list) {
            if (item.getGroup().boundsInParentProperty().get().contains(x, y))
                return item;
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
        Kamikaze objectWarrior = checkClickWarrior(warriors.stream().toList(), x, y);
        if (objectWarrior == null) return;

        if (warriorElect != null) {
            warriorElect.setElect();
            if (warriorElect == objectWarrior) {
                warriorElect = null;
                return;
            }
        }
        warriorElect = objectWarrior;
        warriorElect.setElect();
    }

    private static void deleteWarrior() {
        for (Kamikaze item : warriorsActive) {
            warriorsToRemove.put(item, item.isTeam());
        }
        for (Kamikaze item : warriorsToRemove.keySet()) {
            warriors.remove(item);
            mainGroup.getChildren().remove(item.getGroup());
        }
        warriorsActive.clear();
    }

    private static void turnOf() {
        for (Kamikaze item : warriorsActive) {
            item.setActive(false);
        }
        warriorsActive.clear();
    }

    private static void moveIfElect(double x, double y) {
        if (warriorElect != null && warriorElect.isActive()) {
            warriorElect.setX(warriorElect.getX() + x);
            warriorElect.setY(warriorElect.getY() + y);
            warriorElect.getGroup().setLayoutX(warriorElect.getGroup().getLayoutX() + x);
            warriorElect.getGroup().setLayoutY(warriorElect.getGroup().getLayoutY() + y);
        }
    }
}