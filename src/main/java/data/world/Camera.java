package data.world;

import static app.Play.world;
import static data.functional.forObjects.CONSTANTS.*;

public class Camera {
    private double positionX;
    private double positionY;

    public void moveCameraByX(double positionX) {
        this.positionX = positionX;
        world.getWorldPane().setTranslateX(positionX);
        world.getMiniMap().getMiniMapCamera().setX(miniMapPositionByX(positionX));
    }

    public void moveCameraByY(double positionY) {
        this.positionY = positionY;
        world.getWorldPane().setTranslateY(positionY);
        world.getMiniMap().getMiniMapCamera().setY(miniMapPositionByY(positionY));
    }

    public double getPositionX() {
        return positionX;
    }

    /**
     * @param LR -> (-) - right, (+) - left
     */
    public void setPositionX(int LR) {
        positionX += MOVE_CAMERA_BY_X * LR;
        moveCameraByX(positionX);
    }

    public double getPositionY() {
        return positionY;
    }

    /**
     * @param UD -> (-) - up, (+) - down
     */
    public void setPositionY(int UD) {
        positionY += MOVE_CAMERA_BY_Y * UD;
        moveCameraByY(positionY);
    }

    public double calculateX(double x) {
        double dx = (x - MINIMAP_LAYOUT_X) * DIVISOR;
        dx -= SCREEN_WIDTH / 2;
        if (dx < 0) return 0;
        else if (dx > WORLD_CAMERA_WIDTH) return WORLD_CAMERA_WIDTH;
        return dx;
    }

    public double calculateY(double y) {
        double dy = (y - MINIMAP_LAYOUT_Y) * DIVISOR;
        dy -= SCREEN_HEIGHT / 2;
        if (dy < 0) return 0;
        else if (dy > WORLD_CAMERA_HEIGHT) return WORLD_CAMERA_HEIGHT;
        return dy;
    }

    private double miniMapPositionByX(double positionX) {
        return Math.abs(positionX / DIVISOR - MINIMAP_LAYOUT_X);
    }

    private double miniMapPositionByY(double positionY) {
        return Math.abs(positionY / DIVISOR - MINIMAP_LAYOUT_Y);
    }
}
