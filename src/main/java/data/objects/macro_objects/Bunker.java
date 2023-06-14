package data.objects.macro_objects;

import data.objects.micro_objects.Kamikaze;

import static app.Play.world;
import static data.functional.forObjects.Macro.boundsIntersectBaseBounds;

public class Bunker extends Base {
    public Bunker(double x, double y) {
        super();
        getName().setText("Bunker");
        getWithin().setText("0");
        setX(x);
        setY(y);
        getGroup().setLayoutX(x);
        getGroup().setLayoutY(y);
    }

    public Bunker() {
        this(0, 0);
    }

    @Override
    public void lifeCycle() {
        world.getAllWarriors().forEach(obj -> {
            if (boundsIntersectBaseBounds(obj, this)) {
                getState().add(obj);
                obj.flipInMacro();
            } else if (!boundsIntersectBaseBounds(obj, this)) {
                getState().remove(obj);
                obj.flipInMacro();
            }
        });
        getState().forEach(this::inflictDamage);
        setWithin(getState().size());
    }

    @Override
    public void inflictDamage(Kamikaze kamikaze) {
        kamikaze.setHealth(kamikaze.getHealth() - 0.005);
    }
}