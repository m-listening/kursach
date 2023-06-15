package data.objects.macro_objects;

import data.functional.forObjects.MethodsOfMacro;
import data.objects.micro_objects.Kamikaze;

import java.util.ArrayList;
import java.util.Random;

import static data.functional.forObjects.MethodsOfMacro.interactionWithMacro;
import static data.functional.forObjects.micro.Team.*;

public class GreenBase extends Base {
    public GreenBase(double x, double y) {
        super();
        setTeam(GREEN);
        getName().setText("Бог зна Що");
        getWithin().setText("0");

        setX(x);
        setY(y);
        getGroup().setLayoutX(x);
        getGroup().setLayoutY(y);
    }

    public GreenBase() {
        this(150, 360);
    }

    @Override
    public void lifeCycle() {
        interactionWithMacro(this, getTeam());
        setWithin(getState().size());
        ArrayList<Kamikaze> toRemoveFromMacro = new ArrayList<>();
        getState().forEach(e -> {
            if (e.getHealth() <= e.getMaxHealth())
                e.setHealth(e.getHealth() + 0.1);
            if (e.getHealth() >= e.getMaxHealth())
                toRemoveFromMacro.add(e);
        });
        toRemoveFromMacro.forEach(e -> {
            if (new Random().nextInt(0, 1000) == 3) {
                if (!e.isPowerUp()) {
                    e.setDamage(e.getDamage() * 2);
                    e.setMove(e.getMove() + 2);
                    e.setPowerUp(true);
                }
                MethodsOfMacro.removeFromMacro(e, this);
            }
        });
    }

    @Override
    public void inflictDamage(Kamikaze kamikaze) {
        if (kamikaze.getTeam().equals(RED))
            kamikaze.setHealth(kamikaze.getHealth() - 0.05);
    }
}
