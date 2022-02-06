package agh.ics.oop.gui;

import agh.ics.oop.MapElement;
import javafx.geometry.HPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

public class GuiElement {
    private static final Map<MapElement, Image> viewsMap = new HashMap<>() {{
        put(MapElement.DESERT, new Image(MapElement.DESERT.getImagePath()));
        put(MapElement.RIVER, new Image(MapElement.RIVER.getImagePath()));
        put(MapElement.PLAIN, new Image(MapElement.PLAIN.getImagePath()));
        put(MapElement.FOREST, new Image(MapElement.FOREST.getImagePath()));
        put(MapElement.MOUNTAINS, new Image(MapElement.MOUNTAINS.getImagePath()));
    }};

    public static ImageView createElement(MapElement element)
    {
        ImageView imageView = new ImageView(viewsMap.get(element));
        // TODO check if not rozjechane
        GridPane.setFillHeight(imageView, true);
        GridPane.setFillWidth(imageView, true);
        return new ImageView(viewsMap.get(element));
    }
}
