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
    private final Set<Kamikaze> warriorsElect;
    private final Set<Base> bases;
    private final List<Kamikaze> warriors;
    private final List<Kamikaze> warriorsActive;

    private final Group mainGroup;
    private Timeline timeline;

    public World() {
        warriorsElect = new HashSet<>();
        bases = new HashSet<>();
        warriors = new ArrayList<>();
        warriorsActive = new ArrayList<>();
        mainGroup = new Group();

        timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            List<Kamikaze> warriorsToRemove = new ArrayList<>();
            for (Kamikaze item : warriors) {
                for (Kamikaze item2 : warriors) {
                    if (item.equals(item2) || item.getTeam() == item2.getTeam()
                            || item.getHealth() <= 0 || item2.getHealth() <= 0) continue;
                    if (item.getCircle().getBoundsInParent().intersects(item2.getImageView().getBoundsInParent()))
                        if (item.inflictDamage(item2)) {
                            warriorsToRemove.add(item2);
                            continue;
                        }
                    if (item2.getCircle().getBoundsInParent().intersects(item.getImageView().getBoundsInParent()))
                        if (item2.inflictDamage(item))
                            warriorsToRemove.add(item);
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

    public void initialize() throws FileNotFoundException {
        Utilities.initializeStartGame();
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public Group getWorldGroup() {
        return mainGroup;
    }

    public Set<Kamikaze> getWarriorsElect() {
        return warriorsElect;
    }

    public Set<Base> getBases() {
        return bases;
    }

    public List<Kamikaze> getWarriors() {
        return warriors;
    }

    public List<Kamikaze> getWarriorsActive() {
        return warriorsActive;
    }

    public Group getMainGroup() {
        return mainGroup;
    }
}
