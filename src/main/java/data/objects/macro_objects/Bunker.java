package data.objects.macro_objects;

import data.objects.micro_objects.Kamikaze;
import data.objects.micro_objects.SSO;

import java.util.Random;

import static data.functional.forObjects.MethodsOfMacro.*;

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
        interactionWithMacro(this, getTeam());
        for (Kamikaze e : getState()) {
            if (e instanceof SSO && new Random().nextInt(0, 300) == 228) {
                removeFromMacro(e, this);
                break;
            }
        }
        getState().forEach(this::inflictDamage);
        setWithin(getState().size());
    }

    @Override
    public void inflictDamage(Kamikaze kamikaze) {
        kamikaze.setHealth(kamikaze.getHealth() - 0.05);
    }
}