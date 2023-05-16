package micro_objects;

public class SSO extends SimpleSoldier {
    public SSO(String name, double health) {
        super(name, health);
        move = 7;
        setArmor(100);
        setDamage(75);
    }

    @Override
    public String toString() {
        return "SSO{" +
                "name=" + name.getText() +
                ", x=" + x +
                ", y=" + y +
                ", active=" + active +
                ", health=" + health +
                ", murders=" + murders.getCount() +
                ", inMacro=" + inMacro +
                ", team=" + (isTeam() != null ? isTeam() ? "Green" : "Red" : "None") +
                '}';
    }
}
