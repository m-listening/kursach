package app.kursova;

import Methods.Utilities;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import macro_objects.Base;
import micro_objects.Kamikaze;
import micro_objects.Warrior;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class World {
    public final static Set<Warrior> warriorsElect = new HashSet<>();
    public final static Set<Base> bases = new HashSet<>();
    public final static List<Kamikaze> warriors = new ArrayList<>();
    public final static List<Kamikaze> warriorsActive = new ArrayList<>();

    private final Group mainGroup;
    private Timeline timeline;

    public World() throws FileNotFoundException, CloneNotSupportedException {
        mainGroup = new Group();
        Utilities.initializeStartGame(mainGroup);
        timeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            List<Kamikaze> warriorsToRemove = new ArrayList<>();
            for (Kamikaze item : warriors) {
                for (Kamikaze item2 : warriors) {
                    if (item.equals(item2) || item.isTeam() == item2.isTeam()) continue;
                    if (item.getGroup().getBoundsInParent().intersects(item2.getGroup().getBoundsInParent())) {
                        item.getRectangle().setStroke(Color.GOLD);
                        item2.getRectangle().setStroke(Color.GOLD);
                        if (item.inflictDamage(item2))
                            warriorsToRemove.add(item2);
                        if (item2.getGroup().getBoundsInParent().intersects(item.getGroup().getBoundsInParent()))
                            if (item2.inflictDamage(item))
                                warriorsToRemove.add(item2);
                        break;
                    }
                    item.setRectangleColor();
                    item.setRectangleColor();
                }
            }
            for (Kamikaze item : warriorsToRemove) {
                mainGroup.getChildren().remove(item.getGroup());
                warriors.remove(item);
                warriorsActive.remove(item);
                warriorsElect.remove(item);
            }
            warriorsToRemove.clear();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
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
