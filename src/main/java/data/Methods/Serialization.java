package data.Methods;

import data.micro_objects.Kamikaze;

import java.io.Serializable;

public class Serialization {
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
            Boolean team,
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
}
