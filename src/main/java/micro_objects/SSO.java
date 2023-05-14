package micro_objects;

import java.io.FileNotFoundException;

public class SSO extends SimpleSoldier {
    public SSO(String name, int health) throws FileNotFoundException {
        super(name, health);
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
