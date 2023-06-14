package data.functional.PressedHandlers;

import data.objects.macro_objects.Base;
import data.objects.macro_objects.Bunker;
import data.objects.micro_objects.Kamikaze;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static app.Play.globalStage;
import static app.Play.world;
import static data.functional.forObjects.Macro.*;
import static data.functional.forObjects.Micro.*;

public class MousePressedHandler {
    public static void handle(MouseEvent event) {
        if (event.isAltDown()) {
            Base base = checkClickBase(world.getBaseSet().stream().toList(), event.getX(), event.getY());
            if (base == null || base instanceof Bunker) return;

            if (event.getButton().equals(MouseButton.PRIMARY)) {
                List<Kamikaze> nodes = new ArrayList<>(world.getElectedWarriors());
                nodes.forEach(e -> addToMacro(e, base));
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
}
