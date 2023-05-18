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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class World {
    public final static Set<Kamikaze> warriorsElect = new HashSet<>();
    public final static Set<Base> bases = new HashSet<>();
    public final static List<Kamikaze> warriors = new ArrayList<>();
    public final static List<Kamikaze> warriorsActive = new ArrayList<>();

    private final Group mainGroup;
    private Timeline timeline;

    public World() {
        mainGroup = new Group();
        timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            List<Kamikaze> warriorsToRemove = new ArrayList<>();
            for (Kamikaze item : warriors) {
                for (Kamikaze item2 : warriors) {
                    if (item.equals(item2) || item.isTeam() == item2.isTeam()) continue;
                    if (item.getCircle().getBoundsInParent().intersects(item2.getImageView().getBoundsInParent()))
                        if (item.inflictDamage(item2))
                            warriorsToRemove.add(item2);
                    if (item2.getCircle().getBoundsInParent().intersects(item.getImageView().getBoundsInParent()))
                        if (item2.inflictDamage(item))
                            warriorsToRemove.add(item);
                    item.setRectangleColor();
                    item2.setRectangleColor();
                }
            }
            Utilities.deleteWarrior(warriorsToRemove);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void initialize() throws FileNotFoundException, CloneNotSupportedException {
        Utilities.initializeStartGame(this.mainGroup);
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public Group getMainGroup() {
        return mainGroup;

    }
}
