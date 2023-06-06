package data.World;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static data.Methods.CONSTANTS.*;
import static data.Methods.Utilities.*;


public class MiniMap {
    private double x, y;
    private Pane map;
    private Rectangle outline;
    private Rectangle miniMapCamera;
    private ImageView view;

    public MiniMap() {
        view = new ImageView(getImage("Map"));
        view.setFitWidth(MINIMAP_SIZE_FIT_X);
        view.setFitHeight(MINIMAP_SIZE_FIT_Y);

        outline = new Rectangle(MINIMAP_SIZE_FIT_X, MINIMAP_SIZE_FIT_Y);
        outline.setFill(Color.TRANSPARENT);
        outline.setStrokeWidth(2);
        outline.setStroke(Color.AZURE);

        miniMapCamera = new Rectangle(SCREEN_WIDTH / DIVISOR, SCREEN_HEIGHT / DIVISOR);
        miniMapCamera.setFill(Color.TRANSPARENT);
        miniMapCamera.setStrokeWidth(2);
        miniMapCamera.setStroke(Color.AZURE);

        miniMapCamera.setX(MINIMAP_LAYOUT_X);
        miniMapCamera.setY(MINIMAP_LAYOUT_Y);
        view.setX(MINIMAP_LAYOUT_X);
        view.setY(MINIMAP_LAYOUT_Y);
        outline.setX(MINIMAP_LAYOUT_X);
        outline.setY(MINIMAP_LAYOUT_Y);
        map = new Pane(view, outline, miniMapCamera);
    }

    public Rectangle getMiniMapCamera() {
        return miniMapCamera;
    }

    public void setMiniMapCamera(Rectangle miniMapCamera) {
        this.miniMapCamera = miniMapCamera;
    }

    public ImageView getView() {
        return view;
    }

    public void setView(ImageView view) {
        this.view = view;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Pane getMap() {
        return map;
    }

    public void setMap(Pane map) {
        this.map = map;
    }

    public Rectangle getOutline() {
        return outline;
    }

    public void setOutline(Rectangle outline) {
        this.outline = outline;
    }
}
