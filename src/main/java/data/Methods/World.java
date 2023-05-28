package data.Methods;

import data.macro_objects.Base;
import data.macro_objects.Bunker;
import data.macro_objects.GreenBase;
import data.macro_objects.RedBase;
import data.micro_objects.Kamikaze;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static data.Methods.Utilities.addToMacro;
import static data.Methods.Utilities.initializeStartGame;

public class World {
    private final List<Kamikaze> allWarriors;
    private final List<Kamikaze> warriorsActive;
    private final Set<Kamikaze> electedWarriors;
    private final Set<Base> baseSet;

    private final Group worldGroup;
    private final Timeline activeWorld;

    public World() {
        electedWarriors = new HashSet<>();
        baseSet = new HashSet<>();
        allWarriors = new ArrayList<>();
        warriorsActive = new ArrayList<>();
        worldGroup = new Group();

        activeWorld = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            baseSet.forEach(e -> e.setWithin(e.getState().size()));
            allWarriors.forEach(object -> {
                baseSet.forEach(base -> {
                    if (base instanceof GreenBase)
                        if (object.getImageView().getBoundsInParent().intersects(base.getGroup().getBoundsInParent())
                                && object.getTeam() && !base.getState().contains(object)) {
                            addToBase(object, base);
                        }
                    if (base instanceof RedBase) {
                        if (object.getImageView().getBoundsInParent().intersects(base.getGroup().getBoundsInParent())
                                && !object.getTeam() && !base.getState().contains(object)) {
                            addToBase(object, base);
                        }
                    }
                    if (base instanceof Bunker) {
                        if (object.getImageView().getBoundsInParent().intersects(base.getGroup().getBoundsInParent())
                                && !base.getState().contains(object)) {
                            addToBase(object, base);
                        }
                    }
                });
            });
        }));
        activeWorld.setCycleCount(Animation.INDEFINITE);
        activeWorld.play();
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
