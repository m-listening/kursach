package data.Methods;

import data.macro_objects.Base;
import data.macro_objects.Bunker;
import data.macro_objects.GreenBase;
import data.macro_objects.RedBase;
import data.micro_objects.Kamikaze;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static app.Play.scene;
import static data.Methods.Utilities.addToMacro;
import static data.Methods.Utilities.initializeStartGame;

public class World {
    private final List<Kamikaze> allWarriors;
    private final List<Kamikaze> warriorsActive;
    private final Set<Kamikaze> electedWarriors;
    private final Set<Base> baseSet;

    private final Group worldGroup;

    public final ImageView view = new ImageView(new Image("background.png"));

    public World() {
        electedWarriors = new HashSet<>();
        baseSet = new HashSet<>();
        allWarriors = new ArrayList<>();
        warriorsActive = new ArrayList<>();
        worldGroup = new Group();

        worldGroup.getChildren().add(view);
        AnimationTimer activeWorld = new AnimationTimer() {
            @Override
            public void handle(long l) {
                ArrayList<Kamikaze> arrayList = new ArrayList<>();
                allWarriors.forEach(object -> {
                    if (object.getHealth() <= 0)
                        arrayList.add(object);
                });
                Utilities.deleteWarrior(arrayList);
                scene.setOnMouseClicked(event -> {
                    try {
                        Utilities.mousePressedHandler(event);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                scene.setOnMouseClicked(event -> {
                    try {
                        Utilities.mousePressedHandler(event);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                scene.setOnKeyPressed(Utilities::keyPressedHandler);
                baseSet.forEach(e -> e.setWithin(e.getState().size()));
                allWarriors.forEach(object -> {
                    baseSet.forEach(base -> {
                        if (base instanceof GreenBase)
                            if (object.getImageView().getBoundsInParent().intersects(base.getGroup().getBoundsInParent())
                                    && object.getTeam() && !base.getState().contains(object) && base.getState().size() < 4)
                                addToBase(object, base);
                        if (base instanceof RedBase)
                            if (object.getImageView().getBoundsInParent().intersects(base.getGroup().getBoundsInParent())
                                    && !object.getTeam() && !base.getState().contains(object) && base.getState().size() < 4)
                                addToBase(object, base);
                        if (base instanceof Bunker) {
                            if (object.getImageView().getBoundsInParent().intersects(base.getGroup().getBoundsInParent())
                                    && !base.getState().contains(object)) {
                                object.setHealth(object.getHealth() - 0.001);
                                base.getState().add(object);
                            }
                            if (!object.getImageView().getBoundsInParent().intersects(base.getGroup().getBoundsInParent()))
                                base.getState().remove(object);
                        }
                    });
                });
                allWarriors.forEach(Kamikaze::lifeCycle);
            }
        };
        activeWorld.start();
    }

    public void addToBase(Kamikaze object, Base base) {
        base.getState().add(object);
        addToMacro(object, base);
        electedWarriors.remove(object);
    }

    public void initialize() throws FileNotFoundException {
        initializeStartGame();
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

    public Group getWorldGroup() {
        return worldGroup;
    }
}
