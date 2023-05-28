package data.micro_objects;

import java.util.Random;

public class SimpleSoldier extends Kamikaze {
    public SimpleSoldier(String name, double health) {
        super(name, health);
        setMove(5);
        setArmor(75);
        setDamage(10);
        getCircle().setRadius(100);
    }

    @Override
    public void inflictDamage(Kamikaze warrior) {
        if (new Random().nextInt() * 3 == 2) return;
        if (warrior.getArmor() > 0)
            warrior.setArmor(warrior.getArmor() - this.getDamage());
        else if (warrior.getArmor() < 0) {
            warrior.setHealth(warrior.getHealth() - Math.abs(warrior.getArmor()));
            warrior.setArmor(0);
        } else if (warrior.getArmor() == 0)
            warrior.setHealth(warrior.getHealth() - this.getDamage());
    }
}
