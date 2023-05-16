package micro_objects;

public class SimpleSoldier extends Kamikaze {
    public SimpleSoldier(String name, double health)  {
        super(name, health);
        move = 5;
        setArmor(75);
        setDamage(50);
    }

    @Override
    public String toString() {
        return "Simple Soldier{" +
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
