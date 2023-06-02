package data.World;

import data.Methods.Utilities;
import data.macro_objects.Base;
import data.micro_objects.Kamikaze;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static data.Methods.Utilities.*;

public class World {
    private final List<Kamikaze> allWarriors;
    private final List<Kamikaze> warriorsActive;
    private final Set<Kamikaze> electedWarriors;
    private final Set<Base> baseSet;
    private final StackPane game;
    private final Pane worldPane;
    private final MiniMap miniMap;
    private Camera camera;
    private final ImageView view = new ImageView(getImage("Map"));

    public World() {
        electedWarriors = new HashSet<>();
        baseSet = new HashSet<>();
        allWarriors = new ArrayList<>();
        warriorsActive = new ArrayList<>();

        worldPane = new Pane(view);
        miniMap = new MiniMap();
        game = new StackPane(worldPane, miniMap.getMap());
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
    }

    public void setCamera() {
        this.camera = new Camera();
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

    public void initialize() throws FileNotFoundException {
        initializeStartWorld();
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
