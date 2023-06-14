package data.functional.forObjects;

import data.objects.macro_objects.Base;
import data.objects.macro_objects.Bunker;
import data.objects.macro_objects.GreenBase;
import data.objects.macro_objects.RedBase;
import data.objects.micro_objects.Kamikaze;
import data.objects.micro_objects.SSO;
import data.objects.micro_objects.Team;

import java.util.List;
import java.util.Random;

import static app.Play.world;
import static data.functional.forObjects.Micro.addToWorld;
import static data.functional.forObjects.Micro.removeFromWorld;
import static data.objects.micro_objects.Team.GREEN;
import static data.objects.micro_objects.Team.RED;

public class Macro {
    public static void interactionWithMacro(Base base, Team teamBase) {
        for (Kamikaze object : world.getAllWarriors()) {
            if (object.isOffering()) continue;
            if (boundsIntersectBaseBounds(object, base) && !object.getTeam().equals(teamBase))
                base.inflictDamage(object);
            if (boundsIntersectBaseBounds(object, base) && object.getTeam().equals(teamBase) && !base.getState().contains(object) && base.getState().size() < 3) {
                if (!(object instanceof SSO))
                    if (new Random().nextInt(0, 1000) == 3)
                        world.addToBase(object, base);
            }
        }
    }

    public static boolean boundsIntersectBaseBounds(Kamikaze object, Base base) {
        return object.getImageView().getBoundsInParent().intersects(base.getGroup().getBoundsInParent());
    }

    public static void addToMacro(Kamikaze object, Base base) {
        if (base.getState().contains(object)) return;
        removeFromWorld(object);
        object.setInMacro(true);
        if (object.isElect()) object.flipElect();
        base.getState().add(object);
    }

    public static Base checkClickBase(List<Base> list, double x, double y) {
        for (Base base : list)
            if (base.getGroup().boundsInParentProperty().get().contains(x - world.getWorldPane().getTranslateX(),
                    y - world.getWorldPane().getTranslateY()))
                return base;
        return null;
    }

    public static void removeFromMacro(Kamikaze object, Base base) {
        if (base.getState().remove(object) && !(base instanceof Bunker)) {
            addToWorld(object);
            object.setInMacro(false);
            object.setX(base.getX());
            object.setY(base.getY());
        }
    }

    public static double coordinatesBaseX(Team team) {
        for (Base e : world.getBaseSet()) {
            if (e instanceof GreenBase && team.equals(GREEN))
                return e.getX();
            if (e instanceof RedBase && team.equals(RED))
                return e.getX();
        }
        return 0;
    }

    public static double coordinatesBaseY(Team team) {
        for (Base e : world.getBaseSet()) {
            if (e instanceof GreenBase && team.equals(GREEN))
                return e.getY();
            if (e instanceof RedBase && team.equals(RED))
                return e.getY();
        }
        return 0;
    }
}
