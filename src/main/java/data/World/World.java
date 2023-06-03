package data.World;

import data.Methods.Utilities;
import data.macro_objects.Base;
import data.micro_objects.Kamikaze;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static data.Methods.CONSTANTS.SCENE_SIZE_X;
import static data.Methods.CONSTANTS.SCENE_SIZE_Y;
import static data.Methods.Utilities.*;

public class World {
    private final List<Kamikaze> allWarriors;
    private final List<Kamikaze> warriorsActive;
    private final Set<Kamikaze> electedWarriors;
    private final Set<Base> baseSet;
    private final StackPane game;
    private final Pane worldPane;
    private final MiniMap miniMap;
    private final Camera camera;
    private final ImageView view = new ImageView(getImage("Map"));
    private final Scene scene;

    public World() {
        electedWarriors = new HashSet<>();
        baseSet = new HashSet<>();
        allWarriors = new ArrayList<>();
        warriorsActive = new ArrayList<>();

        worldPane = new Pane(view);
        miniMap = new MiniMap();
        game = new StackPane(worldPane, miniMap.getMap());

        camera = new Camera();

        AnimationTimer activeWorld = new AnimationTimer() {
            @Override
            public void handle(long l) {
                checkWarriorsForDelete();
                for (Base base : baseSet) {
                    try {
                        base.lifeCycle();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                allWarriors.forEach(Kamikaze::lifeCycle);
            }
        };
        activeWorld.start();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            WritableImage screen = worldPane.snapshot(new SnapshotParameters(), null);
            miniMap.getView().setImage(screen);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        scene = new Scene(game, SCENE_SIZE_X, SCENE_SIZE_Y);
        scene.setOnMouseClicked(event1 -> {
            try {
                Utilities.mousePressedHandler(event1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        scene.setOnKeyPressed(event -> {
            try {
                Utilities.keyPressedHandler(event);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        World world = (World) o;
        return Objects.equals(allWarriors, world.allWarriors) && Objects.equals(warriorsActive, world.warriorsActive) && Objects.equals(electedWarriors, world.electedWarriors) && Objects.equals(baseSet, world.baseSet) && Objects.equals(game, world.game) && Objects.equals(worldPane, world.worldPane) && Objects.equals(miniMap, world.miniMap) && Objects.equals(camera, world.camera) && Objects.equals(view, world.view) && Objects.equals(scene, world.scene);
    }

    @Override
    public int hashCode() {
        return Objects.hash(allWarriors, warriorsActive, electedWarriors, baseSet, game, worldPane, miniMap, camera, view, scene);
    }

    public Camera getCamera() {
        return camera;
    }

    public ImageView getView() {
        return view;
    }

    private void checkWarriorsForDelete() {
        ArrayList<Kamikaze> arrayList = new ArrayList<>();
        allWarriors.forEach(object -> {
            if (object.getHealth() <= 0)
                arrayList.add(object);
        });
        Utilities.deleteWarrior(arrayList);
    }

    public void addToBase(Kamikaze object, Base base) {
        base.getState().add(object);
        addToMacro(object, base);
        electedWarriors.remove(object);
    }

    public void initializeWithMicroObjects(int count) {
        createStartWorld();
        createMicroObjects(count);
    }

    public void initializeWithoutMicroObjects() {
        createStartWorld();
    }

    public Scene getScene() {
        return scene;
    }

    public MiniMap getMiniMap() {
        return miniMap;
    }

    public StackPane getGame() {
        return game;
    }

    public Set<Kamikaze> getElectedWarriors() {
        return electedWarriors;
    }

    public Set<Base> getBaseSet() {
        return baseSet;
    }

    public List<Kamikaze> getAllWarriors() {
        return allWarriors;
    }

    public List<Kamikaze> getWarriorsActive() {
        return warriorsActive;
    }

    public Pane getWorldPane() {
        return worldPane;
    }
}
