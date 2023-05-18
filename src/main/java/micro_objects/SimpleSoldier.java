package micro_objects;

import java.util.Random;

public class SimpleSoldier extends Kamikaze {
    public SimpleSoldier(String name, double health) {
        super(name, health);
        setMove(5);
        setArmor(75);
        setDamage(50);
        getCircle().setRadius(100);
    }

    @Override
    public String toString() {
        return "Simple Soldier{" +
                "name=" + getName().getText() +
                ", x=" + getX() +
                ", y=" + getX() +
                ", active=" + isActive() +
                ", health=" + getHealth() +
                ", murders=" + getMurders().getCount() +
                ", inMacro=" + isInMacro() +
                ", team=" + (isTeam() != null ? isTeam() ? "Green" : "Red" : "None") +
                '}';
    }

    @Override
    public boolean inflictDamage(Kamikaze warrior) {
        if (new Random().nextInt() * 3 == 2) return false;
        warrior.setArmor(warrior.getArmor() - warrior.getDamage());
        if (warrior.getArmor() > 0) return false;
        warrior.setHealth(warrior.getHealth() - Math.abs(warrior.getArmor()));
        return warrior.getHealth() <= 0;
    }
}
