package data.world;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static data.functional.forObjects.CONSTANTS.*;


public class MiniMap {
    private double x, y;
    private final Pane map;
    private final Rectangle miniMapCamera;
    private final ImageView view;

    public MiniMap() {
        view = new ImageView();
        view.setFitWidth(MINIMAP_FIT_WIDTH);
        view.setFitHeight(MINIMAP_FIT_HEIGHT);

        Rectangle outline = new Rectangle(MINIMAP_FIT_WIDTH, MINIMAP_FIT_HEIGHT);
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

    public ImageView getView() {
        return view;
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
}
