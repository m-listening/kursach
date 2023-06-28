package data.objects.micro_objects;

import data.functional.forObjects.micro.enums.Level;

import java.util.Random;

public class SimpleSoldier extends Kamikaze {
    public SimpleSoldier(String name, double health, double x, double y) {
        super(name, health, x, y);
        level = Level.SIMPLE_SOLDIER;
        move = 0.8;
        armor = 75;
        damage = 0.3;
        getCircle().setRadius(100);
    }

    @Override
    public void inflictDamage(Kamikaze warrior) {
        if (new Random().nextInt() * 3 == 2) return;
        damage(warrior);
    }

    public void damage(Kamikaze warrior) {
        if (warrior.getArmor() > 0)
            warrior.setArmor(warrior.getArmor() - this.getDamage());
        else if (warrior.getArmor() < 0) {
            warrior.setHealth(warrior.getHealth() - Math.abs(warrior.getArmor()));
            warrior.setArmor(0);
        } else if (warrior.getArmor() == 0)
            warrior.setHealth(warrior.getHealth() - this.getDamage());
    }

}
