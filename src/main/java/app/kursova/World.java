package app.kursova;

import Methods.Utilities;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.util.Duration;
import macro_objects.Base;
import micro_objects.Kamikaze;

import java.io.FileNotFoundException;
import java.util.*;

public class World {
    public static Kamikaze warriorElect;
    public final static Set<Base> bases = new HashSet<>();
    public final static List<Kamikaze> warriors = new ArrayList<>();
    public final static List<Kamikaze> warriorsActive = new ArrayList<>();

    private final Group mainGroup;
    private Timeline timeline;

    public World() throws FileNotFoundException {
        mainGroup = new Group();
        Utilities.initializeStartGame(mainGroup);
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public Group getMainGroup() {
        return mainGroup;

    }
}
