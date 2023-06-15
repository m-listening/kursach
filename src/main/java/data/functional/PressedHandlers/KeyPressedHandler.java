package data.functional.PressedHandlers;

import data.functional.forObjects.JSON;
import data.objects.micro_objects.Kamikaze;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

import static app.Play.world;
import static data.functional.Utilities.showWindow;
import static data.functional.forObjects.CONSTANTS.*;
import static data.functional.forObjects.MethodsOfMacro.removeFromMacro;
import static data.functional.forObjects.micro.MethodsOfMicro.*;

public class KeyPressedHandler {
    public static void handle(KeyEvent event) throws IOException {
        switch (event.getCode()) {
            case W -> {
                if (world.getCamera().getPositionY() + MOVE_CAMERA_BY_Y <= 0)
                    world.getCamera().setPositionY(1);
            }

            case S -> {
                if (Math.abs(world.getCamera().getPositionY() - MOVE_CAMERA_BY_Y) <= WORLD_CAMERA_HEIGHT)
                    world.getCamera().setPositionY(-1);
            }

            case A -> {
                if (world.getCamera().getPositionX() + MOVE_CAMERA_BY_X <= 0)
                    world.getCamera().setPositionX(1);
            }

            case D -> {
                if (Math.abs(world.getCamera().getPositionX() - MOVE_CAMERA_BY_X) <= WORLD_CAMERA_WIDTH)
                    world.getCamera().setPositionX(-1);
            }
            case DIGIT1 -> world.getAllWarriors().forEach(e -> {
                if (e.isInMacro()) world.getBaseSet().forEach(base -> removeFromMacro(e, base));
                e.setOffering(true);
                e.setActive(true);
                e.setElect(false);
                e.setAimX(MACRO_BUNKER_LAYOUT_X);
                e.setAimY(MACRO_BUNKER_LAYOUT_Y);
            });
            case DIGIT0 -> world.getAllWarriors().forEach(e -> {
                e.setHealth(e.getMaxHealth());
                e.setElect(false);
                e.setActive(true);
                e.setOffering(false);
                e.clearAim();
            });

            case F1 -> JSON.saveDataToFile();
            case F2 -> JSON.openDataFile();
            case NUMPAD8 -> moveIfActiveAndElect(0, -10);
            case NUMPAD4 -> moveIfActiveAndElect(-10, 0);
            case NUMPAD6 -> moveIfActiveAndElect(10, 0);
            case NUMPAD2 -> moveIfActiveAndElect(0, 10);

            case INSERT -> showWindow("Parameters", "Parameters");
            case DELETE -> deleteWarrior(world.getElectedWarriors().stream().toList());
            case C -> {
                if (!world.getElectedWarriors().isEmpty()) showWindow("ChangeParameters", "New parameters");
            }
            case Q -> {
                try {
                    for (Kamikaze item : world.getElectedWarriors())
                        item.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
            }
            case I -> showWindow("Search", "Search warrior");
            case ESCAPE -> turnOf();
        }
    }
}
