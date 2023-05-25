package data.Methods;

import data.macro_objects.Base;
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

import static data.Methods.Utilities.*;

public class World {
    private final List<Kamikaze> allWarriors;
    private final List<Kamikaze> warriorsActive;
    private final Set<Kamikaze> electedWarriors;
    private final Set<Base> baseSet;

    private final Group worldGroup;
    private Timeline timeline;

    public World() {
        electedWarriors = new HashSet<>();
        baseSet = new HashSet<>();
        allWarriors = new ArrayList<>();
        warriorsActive = new ArrayList<>();
        worldGroup = new Group();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            List<Kamikaze> warriorsToRemove = new ArrayList<>();
            for (Kamikaze o1 : allWarriors) {
                for (Kamikaze o2 : allWarriors) {
                    Kamikaze toRemove = fightBetweenTwo(o1, o2);
                    if (toRemove != null) warriorsToRemove.add(toRemove);
                }
            }
            deleteWarrior(warriorsToRemove);
        }), new KeyFrame(Duration.seconds(0.5), event -> {
            baseSet.forEach(e -> e.setWithin(e.getState().size()));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void initialize() throws FileNotFoundException {
        initializeStartGame();
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
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
