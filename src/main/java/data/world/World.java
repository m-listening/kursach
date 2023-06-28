package data.world;

import data.functional.PressedHandlers.KeyPressedHandler;
import data.functional.PressedHandlers.MousePressedHandler;
import data.functional.forObjects.micro.Generator;
import data.functional.forObjects.micro.enums.Team;
import data.objects.macro_objects.Base;
import data.objects.macro_objects.Bunker;
import data.objects.macro_objects.GreenBase;
import data.objects.macro_objects.RedBase;
import data.objects.micro_objects.Kamikaze;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static app.Play.world;
import static data.functional.Utilities.getImage;
import static data.functional.forObjects.CONSTANTS.*;
import static data.functional.forObjects.MethodsOfMacro.addToMacro;
import static data.functional.forObjects.micro.MethodsOfMicro.deleteWarrior;

public class World {
    private final List<Kamikaze> allWarriors;
    private final List<Kamikaze> warriorsActive;
    private final Set<Kamikaze> electedWarriors;
    private final Set<Base> baseSet;
    private final Pane worldPane;
    private final MiniMap miniMap;
    private final Camera camera;
    private final Scene scene;

    public World() {
        electedWarriors = new HashSet<>();
        baseSet = new HashSet<>();
        allWarriors = new ArrayList<>();
        warriorsActive = new ArrayList<>();

        ImageView view = new ImageView(getImage("Map"));
        worldPane = new Pane(view);
        miniMap = new MiniMap();
        camera = new Camera();
        StackPane game = new StackPane(worldPane, miniMap.getMap());

        scene = new Scene(game, START_SCENE_WIDTH_X, START_SCENE_HEIGHT_Y);

        AnimationTimer activeWorld = new AnimationTimer() {
            @Override
            public void handle(long l) {
                lifeCycle();
            }
        };
        activeWorld.start();
        scene.setOnMouseClicked(MousePressedHandler::handle);
        scene.setOnKeyPressed(event -> {
            try {
                KeyPressedHandler.handle(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            WritableImage screen = worldPane.snapshot(new SnapshotParameters(), null);
            miniMap.getView().setImage(screen);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void lifeCycle() {
        checkWarriorsForDelete();
        generateMicro();

        for (Base base : baseSet) {
            try {
                base.lifeCycle();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        for (Kamikaze obj : allWarriors)
            obj.lifeCycle();
    }

    private void generateMicro() {
        for (Base base : baseSet)
            if (countMicroInTeam(base.getTeam()) < 20 && !(base instanceof Bunker))
                Generator.generateMicro(20, base.getTeam());
    }

    private int countMicroInTeam(Team team) {
        int result = 0;
        for (Kamikaze obj : allWarriors)
            if (obj.getTeam().equals(team))
                result++;
        return result;
    }

    public Camera getCamera() {
        return camera;
    }

    private void checkWarriorsForDelete() {
        ArrayList<Kamikaze> arrayList = new ArrayList<>();
        allWarriors.forEach(object -> {
            if (object.getHealth() <= 0)
                arrayList.add(object);
        });
        deleteWarrior(arrayList);
    }

    public void addToBase(Kamikaze object, Base base) {
        addToMacro(object, base);
    }

    public void initializeWithMicroObjects(int count) {
        initializeWithoutMicroObjects();
        Generator.generateMicro(count / 2, Team.GREEN);
        Generator.generateMicro(count / 2, Team.RED);
    }

    public void initializeWithoutMicroObjects() {
        createStartWorld();
    }

    private static void createStartWorld() {
        Bunker bunker = new Bunker(MACRO_BUNKER_LAYOUT_X, MACRO_BUNKER_LAYOUT_Y);
        GreenBase greenBase = new GreenBase(MACRO_GREEN_BASE_LAYOUT_X, MACRO_GREEN_BASE_LAYOUT_Y);
        RedBase redBase = new RedBase(MACRO_RED_BASE_LAYOUT_X, MACRO_RED_BASE_LAYOUT_Y);

        world.getBaseSet().add(bunker);
        world.getBaseSet().add(greenBase);
        world.getBaseSet().add(redBase);
    }

    public Scene getScene() {
        return scene;
    }

    public MiniMap getMiniMap() {
        return miniMap;
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
