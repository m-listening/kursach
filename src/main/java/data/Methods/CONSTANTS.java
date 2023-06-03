package data.Methods;

import javafx.stage.Screen;

public class CONSTANTS {
    public static final double SCENE_SIZE_X = 720;
    public static final double SCENE_SIZE_Y = 480;
    public static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
    public static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();
    public static final double WORLD_SIZE_WIDTH = 4000;
    public static final double WORLD_SIZE_HEIGHT = 4000;
    public static final double WORLD_CAMERA_SIZE_WIDTH = WORLD_SIZE_WIDTH - SCREEN_WIDTH;
    public static final double WORLD_CAMERA_SIZE_HEIGHT = WORLD_SIZE_HEIGHT - SCREEN_HEIGHT;
    public static final int DIVISOR = 10;
    public static final double MINIMAP_SIZE_FIT_X = WORLD_SIZE_WIDTH / DIVISOR;
    public static final double MINIMAP_SIZE_FIT_Y = WORLD_SIZE_HEIGHT / DIVISOR;
    public static final double MINIMAP_LAYOUT_X = SCREEN_WIDTH - MINIMAP_SIZE_FIT_X;
    public static final double MINIMAP_LAYOUT_Y = 0;

    public static final double MOVE_CAMERA_BY_X = 20;
    public static final double MOVE_CAMERA_BY_Y = 20;
}
