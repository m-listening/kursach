package data.macro_objects;

import data.micro_objects.Kamikaze;
import data.micro_objects.SSO;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import static app.Play.world;
import static data.Methods.Utilities.*;

public class RedBase extends Base {

    public RedBase(double x, double y) {
        super();

        getName().setText("Україна");
        getWithin().setText("0");

        setX(x);
        setY(y);
        getGroup().setLayoutX(x);
        getGroup().setLayoutY(y);
    }

    public RedBase() {
        this(1130, 360);
    }

    @Override
    public void lifeCycle()   {
        interactionWithMacro(this, false);
        setWithin(getState().size());
        boolean withoutSSO = true;
        if (getState().size() >= 3) {
            for (Kamikaze kamikaze : getState()) {
                if (kamikaze instanceof SSO) {
                    withoutSSO = false;
                    break;
                }
            }
            if (withoutSSO) {
                withoutSSO();
            }
        }
        for (Kamikaze e : getState()) {
            if (e instanceof SSO && new Random().nextInt(0, 1000) == 228) {
                removeFromMacro(e, this);
                break;
            }
        }
    }

    private void withoutSSO() {
        double health = 0;
        for (Kamikaze e : getState())
            health += e.getHealth();
        SSO sso = new SSO("IMBA", health);
        updateWarrior(sso, this.getX(), this.getY(), false);
        world.getAllWarriors().add(sso);
        world.getWarriorsActive().add(sso);
        addToMacro(sso, this);
        deleteWarrior(toRemove());
    }

    private ArrayList<Kamikaze> toRemove() {
        ArrayList<Kamikaze> arrayList = new ArrayList<>();
        getState().forEach(e -> {
            if (!(e instanceof SSO))
                arrayList.add(e);
        });
        return arrayList;
    }

    @Override
    public void inflictDamage(Kamikaze kamikaze) {
        if (kamikaze.getTeam())
            kamikaze.setHealth(kamikaze.getHealth() - 0.005);
    }
}
