package micro_objects;

public class SSO extends SimpleSoldier {
    public SSO(String name, double health) {
        super(name, health);
        setMove(7);
        setArmor(100);
        setDamage(75);
        getCircle().setRadius(200);
    }

    @Override
    public String toString() {
        return "SSO{" +
                "name=" + getName().getText() +
                ", x=" + getX() +
                ", y=" + getY() +
                ", active=" + isActive() +
                ", health=" + getHealth() +
                ", murders=" + getMurders().getCount() +
                ", inMacro=" + isInMacro() +
                ", team=" + (getTeam() != null ? getTeam() ? "Green" : "Red" : "None") +
                '}';
    }

    @Override
    public boolean inflictDamage(Kamikaze warrior) {
        warrior.setArmor(warrior.getArmor() - warrior.getDamage());
        if (warrior.getArmor() > 0) return false;
        warrior.setHealth(warrior.getHealth() - Math.abs(warrior.getArmor()));
        return warrior.getHealth() <= 0;
    }

}
