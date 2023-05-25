package data.micro_objects;

public class SSO extends SimpleSoldier {
    public SSO(String name, double health) {
        super(name, health);
        setMove(7);
        setArmor(100);
        setDamage(75);
        getCircle().setRadius(200);
    }

    @Override
    public boolean inflictDamage(Kamikaze warrior) {
        if (warrior.getArmor() > 0)
            warrior.setArmor(warrior.getArmor() - this.getDamage());
        else if (warrior.getArmor() < 0) {
            warrior.setHealth(warrior.getHealth() - Math.abs(warrior.getArmor()));
            warrior.setArmor(0);
            return warrior.getHealth() <= 0;
        } else if (warrior.getArmor() == 0)
            warrior.setHealth(warrior.getHealth() - this.getDamage());
        return warrior.getHealth() <= 0;
    }
}
