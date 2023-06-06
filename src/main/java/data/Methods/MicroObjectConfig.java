package data.Methods;

import data.micro_objects.Kamikaze;
import data.micro_objects.SSO;
import data.micro_objects.SimpleSoldier;

import java.io.Serializable;

import static data.Methods.Utilities.*;

public record MicroObjectConfig(
        String classType,
        String name,
        double x,
        double y,
        double health,
        double maxHealth,
        double armor,
        boolean powerUp,
        boolean active,
        boolean elect,
        Team team,
        int murders
) implements Serializable {
    public static MicroObjectConfig convertToConfig(Kamikaze kamikaze) {
        return new MicroObjectConfig(
                kamikaze.getClass().getSimpleName(),
                kamikaze.getName().getText(),
                kamikaze.getX(),
                kamikaze.getY(),
                kamikaze.getHealth(),
                kamikaze.getMaxHealth(),
                kamikaze.getArmor(),
                kamikaze.isPowerUp(),
                kamikaze.isActive(),
                kamikaze.isElect(),
                kamikaze.getTeam(),
                kamikaze.getMurders().getCount()
        );
    }

    public void convertToObject() {
        var result = switch (classType) {
            case "Kamikaze" -> new Kamikaze(name, maxHealth);
            case "SimpleSoldier" -> new SimpleSoldier(name, maxHealth);
            case "SSO" -> new SSO(name, maxHealth);
            default -> throw new IllegalStateException("Unexpected value: " + classType);
        };
        updateWarrior(result, x, y, team);
        result.setHealth(health);
        result.setArmor(armor);
        result.setPowerUp(powerUp);
        result.setActive(active);
        result.setElect(elect);
        result.getMurders().setCount(murders);
    }

    @Override
    public String toString() {
        return "{" +
                "classType='" + classType + '\'' +
                ", name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", health=" + health +
                ", maxHealth=" + maxHealth +
                ", armor=" + armor +
                ", powerUp=" + powerUp +
                ", active=" + active +
                ", elect=" + elect +
                ", team=" + team +
                ", murders=" + murders +
                '}';
    }
}
