package data.functional.forObjects;

import javafx.stage.Screen;

public class CONSTANTS {
    //Scene
    public static final double START_SCENE_WIDTH_X = Screen.getPrimary().getBounds().getWidth() - 100;
    public static final double START_SCENE_HEIGHT_Y = Screen.getPrimary().getBounds().getHeight() - 100;
    public static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
    public static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();

    //World + Camera
    public static final double WORLD_WIDTH = 4000;
    public static final double WORLD_HEIGHT = 4000;
    public static final double WORLD_CAMERA_WIDTH = WORLD_WIDTH - SCREEN_WIDTH;
    public static final double WORLD_CAMERA_HEIGHT = WORLD_HEIGHT - SCREEN_HEIGHT;
    public static final double MOVE_CAMERA_BY_X = 20;
    public static final double MOVE_CAMERA_BY_Y = 20;

    public static final int DIVISOR = 10;

    //Mini-Map
    public static final double MINIMAP_FIT_WIDTH = WORLD_WIDTH / DIVISOR;
    public static final double MINIMAP_FIT_HEIGHT = WORLD_HEIGHT / DIVISOR;
    public static final double MINIMAP_LAYOUT_X = SCREEN_WIDTH - MINIMAP_FIT_WIDTH;
    public static final double MINIMAP_LAYOUT_Y = 0;

    //for Micro-object
    public static final double MICRO_OBJECT_IMAGE_WIDTH = 50;
    public static final double MICRO_OBJECT_IMAGE_HEIGHT = 50;
    public static final double MICRO_FIGHT_IMAGE_WIDTH = MICRO_OBJECT_IMAGE_WIDTH / 2.5;
    public static final double MICRO_FIGHT_IMAGE_HEIGHT = MICRO_OBJECT_IMAGE_HEIGHT / 2.5;
    //Coordinates by X
    public static final double MICRO_RECTANGLE_LAYOUT_X = -MICRO_OBJECT_IMAGE_WIDTH / 10;
    public static final double MICRO_IMAGE_VIEW_LAYOUT_X = -MICRO_OBJECT_IMAGE_WIDTH / 10;
    public static final double MICRO_CIRCLE_LAYOUT_X = MICRO_OBJECT_IMAGE_WIDTH / 2.5;
    public static final double MICRO_LIFE_LAYOUT_START_X = 0;
    public static final double MICRO_LIFE_LAYOUT_END_X = MICRO_OBJECT_IMAGE_WIDTH;
    public static final double MICRO_IDENTIFIER_TEAM_LAYOUT_X = MICRO_OBJECT_IMAGE_WIDTH - MICRO_OBJECT_IMAGE_WIDTH / 5;
    public static final double MICRO_FIGHT_VIEW_LAYOUT_X = MICRO_OBJECT_IMAGE_WIDTH / 3;
    //Coordinates by Y for Micro-object
    public static final double MICRO_RECTANGLE_LAYOUT_Y = -MICRO_OBJECT_IMAGE_HEIGHT / 10;
    public static final double MICRO_IMAGE_VIEW_LAYOUT_Y = MICRO_OBJECT_IMAGE_HEIGHT / 2.5;
    public static final double MICRO_CIRCLE_LAYOUT_Y = MICRO_OBJECT_IMAGE_HEIGHT - MICRO_OBJECT_IMAGE_HEIGHT / 5;
    public static final double MICRO_IDENTIFIER_TEAM_LAYOUT_Y = MICRO_OBJECT_IMAGE_HEIGHT + MICRO_OBJECT_IMAGE_HEIGHT / 2.5;
    public static final double MICRO_FIGHT_VIEW_LAYOUT_Y = -MICRO_OBJECT_IMAGE_HEIGHT / 4;

    //for Macro-object
    public static final double MACRO_IMAGE_WIDTH = 300;
    public static final double MACRO_IMAGE_HEIGHT = 300;

    public static final double MACRO_CIRCLE_RADIUS = MACRO_IMAGE_WIDTH;
    //Coordinates by X
    public static final double MACRO_BUNKER_LAYOUT_X = WORLD_WIDTH / 2;
    public static final double MACRO_GREEN_BASE_LAYOUT_X = 400;
    public static final double MACRO_RED_BASE_LAYOUT_X = WORLD_WIDTH - 400;
    public static final double MACRO_NAME_LAYOUT_X = -90;
    public static final double MACRO_WITHIN_LAYOUT_X = -10;
    public static final double MACRO_IMAGE_VIEW_LAYOUT_X = -150;
    //Coordinates by Y
    public static final double MACRO_BUNKER_LAYOUT_Y = WORLD_HEIGHT / 2;
    public static final double MACRO_GREEN_BASE_LAYOUT_Y = 400;
    public static final double MACRO_RED_BASE_LAYOUT_Y = WORLD_HEIGHT - 400;
    public static final double MACRO_NAME_LAYOUT_Y = -200;
    public static final double MACRO_WITHIN_LAYOUT_Y = 200;
    public static final double MACRO_IMAGE_VIEW_LAYOUT_Y = -150;
}