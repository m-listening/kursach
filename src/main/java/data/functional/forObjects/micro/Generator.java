package data.functional.forObjects.micro;

import data.functional.forObjects.CONSTANTS;
import data.functional.forObjects.micro.enums.Team;
import data.objects.micro_objects.Kamikaze;
import data.objects.micro_objects.SimpleSoldier;

import java.util.ArrayList;

public class Generator {
    public static void generateMicro(int number, Team team) {
        String name = team == Team.GREEN ? "Зелені" : "Червоні";
        ArrayList<Kamikaze> list = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            if (i % 2 == 0) list.add(new Kamikaze(name, 200, 0, 0));
            else list.add(new SimpleSoldier(name, 300, 0, 0));
        }
        setTeam(list, team);
        setCoordinates(list, team);
    }

    private static void setTeam(ArrayList<Kamikaze> list, Team team) {
        list.forEach(object -> object.setTeam(team));
    }

    private static void setCoordinates(ArrayList<Kamikaze> list, Team team) {
        list.forEach(object -> {
            if (team.equals(Team.GREEN)) {
                object.setX(CONSTANTS.MACRO_GREEN_BASE_LAYOUT_X);
                object.setY(CONSTANTS.MACRO_GREEN_BASE_LAYOUT_Y);
            } else if (team.equals(Team.RED)) {
                object.setX(CONSTANTS.MACRO_RED_BASE_LAYOUT_X);
                object.setY(CONSTANTS.MACRO_RED_BASE_LAYOUT_Y);
            }
        });
    }
}