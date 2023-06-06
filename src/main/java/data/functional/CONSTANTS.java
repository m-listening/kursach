package data.functional;

import javafx.stage.Screen;

public class CONSTANTS {
    //Scene
    public static final double START_SCENE_SIZE_X = 720;
    public static final double START_SCENE_SIZE_Y = 480;
    public static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
    public static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();

    //World + Camera
    public static final double WORLD_SIZE_WIDTH = 4000;
    public static final double WORLD_SIZE_HEIGHT = 4000;
    public static final double WORLD_CAMERA_SIZE_WIDTH = WORLD_SIZE_WIDTH - SCREEN_WIDTH;
    public static final double WORLD_CAMERA_SIZE_HEIGHT = WORLD_SIZE_HEIGHT - SCREEN_HEIGHT;
    public static final double MOVE_CAMERA_BY_X = 20;
    public static final double MOVE_CAMERA_BY_Y = 20;

    public static final int DIVISOR = 10;

    //Mini-Map
    public static final double MINIMAP_SIZE_FIT_X = WORLD_SIZE_WIDTH / DIVISOR;
    public static final double MINIMAP_SIZE_FIT_Y = WORLD_SIZE_HEIGHT / DIVISOR;
    public static final double MINIMAP_LAYOUT_X = SCREEN_WIDTH - MINIMAP_SIZE_FIT_X;
    public static final double MINIMAP_LAYOUT_Y = 0;

    //for Micro-object
    //Coordinates by X
    public static final double MICRO_RECTANGLE_LAYOUT_X = -5;
    public static final double MICRO_IMAGE_VIEW_LAYOUT_X = -5;
    public static final double MICRO_CIRCLE_LAYOUT_X = 20;
    public static final double MICRO_NAME_LAYOUT_X = 5;
    public static final double MICRO_LIFE_LAYOUT_X = 0;
    public static final double MICRO_IDENTIFIER_TEAM_LAYOUT_X = 40;
    public static final double MICRO_FIGHT_VIEW_LAYOUT_X = 15;
    //Coordinates by Y for Micro-object
    public static final double MICRO_RECTANGLE_LAYOUT_Y = -5;
    public static final double MICRO_IMAGE_VIEW_LAYOUT_Y = 20;
    public static final double MICRO_CIRCLE_LAYOUT_Y = 40;
    public static final double MICRO_NAME_LAYOUT_Y = -2;
    public static final double MICRO_LIFE_LAYOUT_Y = 0;
    public static final double MICRO_IDENTIFIER_TEAM_LAYOUT_Y = 70;
    public static final double MICRO_FIGHT_VIEW_LAYOUT_Y = -12;

    //for Macro-object
    public static final double MACRO_CIRCLE_RADIUS = 300;
    //Coordinates by X
    public static final double MACRO_BUNKER_LAYOUT_X = WORLD_SIZE_WIDTH / 2;
    public static final double MACRO_GREEN_BASE_LAYOUT_X = 400;
    public static final double MACRO_RED_BASE_LAYOUT_X = WORLD_SIZE_WIDTH - 400;
    public static final double MACRO_NAME_LAYOUT_X = -90;
    public static final double MACRO_WITHIN_LAYOUT_X = -10;
    public static final double MACRO_IMAGE_VIEW_LAYOUT_X = -150;
    //Coordinates by Y
    public static final double MACRO_BUNKER_LAYOUT_Y = WORLD_SIZE_HEIGHT / 2;
    public static final double MACRO_GREEN_BASE_LAYOUT_Y = 400;
    public static final double MACRO_RED_BASE_LAYOUT_Y = WORLD_SIZE_HEIGHT - 400;
    public static final double MACRO_NAME_LAYOUT_Y = -200;
    public static final double MACRO_WITHIN_LAYOUT_Y = 200;
    public static final double MACRO_IMAGE_VIEW_LAYOUT_Y = -150;

}