package data.functional.forObjects.micro;

import data.objects.macro_objects.Base;
import data.objects.macro_objects.GreenBase;
import data.objects.macro_objects.RedBase;
import data.objects.micro_objects.Kamikaze;
import data.objects.micro_objects.SSO;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static app.Play.globalStage;
import static app.Play.world;
import static data.functional.forObjects.CONSTANTS.WORLD_HEIGHT;
import static data.functional.forObjects.CONSTANTS.WORLD_WIDTH;
import static data.functional.forObjects.micro.enums.Team.GREEN;
import static data.functional.forObjects.micro.enums.Team.RED;

public class MethodsOfMicro {
    public static void deleteWarrior(List<Kamikaze> list) {
        world.getBaseSet().forEach(base -> list.forEach(obj -> base.getState().remove(obj)));
        list.forEach(MethodsOfMicro::removeFromWorld);
        world.getAllWarriors().removeAll(list);
        list.forEach(e -> e.setActive(false));
        list.forEach(e -> e.setElect(false));
    }

    public static void turnOf() {
        world.getAllWarriors().forEach(e -> e.setActive(false));
    }

    public static void whatToDo(SSO sso) {
        int rand = new Random().nextInt(0, 3);
        if (rand == 1) whatToDo((Kamikaze) sso);
        else {
            for (Kamikaze obj : world.getAllWarriors())
                if (sso.getTeam() != obj.getTeam() && new Random().nextBoolean() && !obj.isMonster()) {
                    sso.setAim(obj);
                    if (sso.isMonster())
                        sso.setMove(obj.getMove() * 1.9);
                }
        }
    }

    public static void whatToDo(Kamikaze kamikaze) {
        int rand = new Random().nextInt(0, 5);
        if (rand == 1)
            for (Kamikaze e : world.getAllWarriors()) {
                if (kamikaze.getTeam() != e.getTeam() && !e.isMonster()) {
                    kamikaze.setAimX(e.getX());
                    kamikaze.setAimY(e.getY());
                    if (kamikaze.isMonster())
                        kamikaze.setMove(e.getMove() * 1.9);
                    return;
                }
            }
        else if (rand == 2) {
            for (Base base : world.getBaseSet()) {
                if (base instanceof GreenBase && kamikaze.getTeam().equals(GREEN) && !kamikaze.isMonster()) {
                    kamikaze.setAimX(base.getX());
                    kamikaze.setAimY(base.getY());
                    return;
                }
                if (base instanceof RedBase && kamikaze.getTeam().equals(RED) && !kamikaze.isMonster()) {
                    kamikaze.setAimX(base.getX());
                    kamikaze.setAimY(base.getY());
                    return;
                }
            }
        } else {
            kamikaze.setAimX(new Random().nextDouble() * WORLD_WIDTH);
            kamikaze.setAimY(new Random().nextDouble() * WORLD_HEIGHT);
        }
    }

    public static void moveIfActiveAndElect(double dx, double dy) {
        for (Kamikaze item : world.getElectedWarriors()) {
            if (item.isActive()) {
                item.moveActive(dx, dy);
            }
        }
    }

    public static void electWarrior(double x, double y) {
        Kamikaze objectWarrior = checkClickWarrior(world.getAllWarriors().stream().toList(), x, y);
        if (objectWarrior == null) return;

        objectWarrior.flipElect();
        notification();
    }

    public static void removeFromWorld(Kamikaze object) {
        world.getWorldPane().getChildren().removeAll(object.getCircle(), object.getImageView(), object.getLife(), object.getName(), object.getRectangle(), object.getIdentifierTeam(), object.getFightView());
    }

    public static void addToWorld(Kamikaze object) {
        world.getWorldPane().getChildren().addAll(object.getCircle(), object.getImageView(), object.getLife(), object.getName(), object.getRectangle(), object.getIdentifierTeam(), object.getFightView());
    }

    public static Kamikaze checkClickWarrior(List<Kamikaze> list, double x, double y) {
        for (Kamikaze item : list)
            if (item.getImageView().getBoundsInParent().contains(
                    x - world.getWorldPane().getTranslateX(),
                    y - world.getWorldPane().getTranslateY()) && !item.isInMacro())
                return item;
        return null;
    }

    public static Kamikaze checkClickOnWarriorInBase(HashMap<Group, Kamikaze> hashLow, double x, double y) {
        for (Group group : hashLow.keySet()) {
            if (group.getBoundsInParent().contains(x, y))
                return hashLow.get(group);
        }
        return null;
    }

    public static void notification() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        Label label = new Label("Chosen: " + world.getElectedWarriors().size());
        Button showChosen = new Button("Show chosen");
        vBox.getChildren().addAll(label, showChosen);

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
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
}