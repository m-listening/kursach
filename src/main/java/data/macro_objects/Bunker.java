package data.macro_objects;

import data.micro_objects.Kamikaze;

import static app.Play.world;
import static data.Methods.Utilities.boundsIntersectOtherBounds;

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
            if (boundsIntersectOtherBounds(obj, this))
                getState().add(obj);
            else if(!boundsIntersectOtherBounds(obj, this))getState().remove(obj);
        });
        getState().forEach(this::inflictDamage);
        setWithin(getState().size());
    }

    @Override
    public void inflictDamage(Kamikaze kamikaze) {
        kamikaze.setHealth(kamikaze.getHealth() - 0.005);
    }
}