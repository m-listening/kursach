package data.Methods;

import javafx.stage.Screen;

public class CONSTANTS {
    public static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
    public static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();
    public static final double WORLD_SIZE_WIDTH_MAX = 4000;
    public static final double WORLD_SIZE_HEIGHT_MAX = 4000;
    public static final double WORLD_CAMERA_SIZE_WIDTH = WORLD_SIZE_WIDTH_MAX - SCREEN_WIDTH;
    public static final double WORLD_CAMERA_SIZE_HEIGHT = WORLD_SIZE_HEIGHT_MAX - SCREEN_HEIGHT;
    public static final int DIVISOR = 10;
    public static final double MINIMAP_SIZE_FIT_X = WORLD_SIZE_WIDTH_MAX / DIVISOR;
    public static final double MINIMAP_SIZE_FIT_Y = WORLD_SIZE_HEIGHT_MAX / DIVISOR;
    public static final double MINIMAP_LAYOUT_X = SCREEN_WIDTH - MINIMAP_SIZE_FIT_X;
    public static final double MINIMAP_LAYOUT_Y = 0;

    public static final double MOVE_CAMERA_BY_X = 20;
    public static final double MOVE_CAMERA_BY_Y = 20;
}
