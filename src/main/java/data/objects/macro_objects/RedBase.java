package data.objects.macro_objects;

import data.objects.micro_objects.Kamikaze;
import data.objects.micro_objects.SSO;

import java.util.ArrayList;
import java.util.Random;

import static data.functional.forObjects.MethodsOfMacro.*;
import static data.functional.forObjects.micro.MethodsOfMicro.deleteWarrior;
import static data.functional.forObjects.micro.Team.GREEN;
import static data.functional.forObjects.micro.Team.RED;

public class RedBase extends Base {

    public RedBase(double x, double y) {
        super();
        setTeam(RED);
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
    public void lifeCycle() {
        interactionWithMacro(this, getTeam());
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
        SSO sso = new SSO("IMBA", health,getX(),getY());
        sso.setTeam(RED);
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
        if (kamikaze.getTeam().equals(GREEN))
            kamikaze.setHealth(kamikaze.getHealth() - 0.05);
    }
}
