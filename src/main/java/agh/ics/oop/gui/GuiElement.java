package agh.ics.oop.gui;

import agh.ics.oop.*;
import com.sun.source.doctree.StartElementTree;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class GuiElement {

    private static final Map<MapElement, Image> elementsMap = new HashMap<>() {{
        put(MapElement.DESERT, new Image(MapElement.DESERT.getImagePath()));
        put(MapElement.RIVER, new Image(MapElement.RIVER.getImagePath()));
        put(MapElement.PLAIN, new Image(MapElement.PLAIN.getImagePath()));
        put(MapElement.FOREST, new Image(MapElement.FOREST.getImagePath()));
        put(MapElement.MOUNTAINS, new Image(MapElement.MOUNTAINS.getImagePath()));
    }};

    private static final Map<IndividualType, Image> individualsMap = new HashMap<>(){{
        put(IndividualType.AGGRESSOR, new Image(IndividualType.AGGRESSOR.getImagePath()));
        put(IndividualType.HIKER, new Image(IndividualType.HIKER.getImagePath()));
        put(IndividualType.MAGE, new Image(IndividualType.MAGE.getImagePath()));
        put(IndividualType.SETTLER, new Image(IndividualType.SETTLER.getImagePath()));
        put(IndividualType.MOUNTAIN_MAN, new Image(IndividualType.MOUNTAIN_MAN.getImagePath()));
        put(IndividualType.PIRATE, new Image(IndividualType.PIRATE.getImagePath()));
        put(IndividualType.SANDMAN, new Image(IndividualType.SANDMAN.getImagePath()));
    }};

    // hashmap just to make code uhm consistent
    private static final Map<Integer, Image> cityMap = new HashMap<>(){{
        put(1, new Image("smallcity.png"));
        put(2, new Image("mediumcity.png"));
        put(3, new Image("bigcity.png"));
    }};

    public static ImageView createElement(MapElement element, double CELL_WIDTH, double CELL_HEIGHT)
    {
        ImageView imageView = new ImageView(elementsMap.get(element));

        imageView.setFitHeight(CELL_HEIGHT);
        imageView.setFitWidth(CELL_WIDTH);

        return imageView;
    }

    public static Button createIndividualButton(Individual individual, double CELL_WIDTH, double CELL_HEIGHT, App app)
    {
        ImageView imageView = new ImageView(individualsMap.get(individual.getType()));

        imageView.setFitWidth(CELL_WIDTH);
        imageView.setFitHeight(CELL_HEIGHT);

        Button button = new Button();

        button.setMaxSize(CELL_WIDTH, CELL_HEIGHT);
        button.setMinSize(CELL_WIDTH, CELL_HEIGHT);

        button.setGraphic(imageView);
        button.setCenterShape(true);

        button.setOnAction(e -> {

            MoveControls moveControls = new MoveControls();
            Text belongsToText = new Text("this individual belongs to civilization no " + individual.getCivilization().getOrdinal());
            Text healthPointsText = new Text("this individual has " + individual.getHealthPoints() + " health points");
            Text movesLeftText = new Text("this individual has " + individual.getAvailableMovePoints() + " moves left");
            VBox indVbox = new VBox();
            Button createCityButton = new Button("CREATE CITY");
            Scene newScene = new Scene(indVbox, 400, 200);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.show();
            if (individual.getCivilization().getOrdinal() == 1)
            {
                if (individual.getType() == IndividualType.SETTLER)
                {
                    indVbox.getChildren().add(createCityButton);
                    createCityButton.setOnAction(event -> {
                        individual.getGameMap().createCity(individual);
                        app.mapUpdate();
                        newStage.hide();
                    });
                }
                indVbox.getChildren().addAll(belongsToText, healthPointsText, movesLeftText, moveControls);
                indVbox.setAlignment(Pos.CENTER);
                moveControls.leftButton.setOnAction(ev1 -> {
                    individual.getGameMap().move(individual, Direction.LEFT);
                    app.mapUpdate();
                    newStage.hide();
                });
                moveControls.upButton.setOnAction(ev2 -> {
                    individual.getGameMap().move(individual, Direction.BACKWARD);
                    app.mapUpdate();
                    newStage.hide();
                });
                moveControls.rightButton.setOnAction(ev3 -> {
                    individual.getGameMap().move(individual, Direction.RIGHT);
                    app.mapUpdate();
                    newStage.hide();
                });
                moveControls.downButton.setOnAction(ev4 -> {
                    individual.getGameMap().move(individual, Direction.FORWARD);
                    app.mapUpdate();
                    newStage.hide();
                });
            }
            else
            {
                indVbox.getChildren().addAll(belongsToText, healthPointsText);
                indVbox.setAlignment(Pos.CENTER);
            }
        });

        return button;
    }

    public static Button createCityButton(City city, double CELL_WIDTH, double CELL_HEIGHT)
    {
        ImageView imageView;
        if (city.getNumberOfCitizens() < 10)
            imageView = new ImageView(cityMap.get(1));
        else if (city.getNumberOfCitizens() < 20)
            imageView = new ImageView(cityMap.get(2));
        else
            imageView = new ImageView(cityMap.get(3));

        imageView.setFitWidth(CELL_WIDTH);
        imageView.setFitHeight(CELL_HEIGHT);

        Button button = new Button();

        button.setMaxSize(CELL_WIDTH, CELL_HEIGHT);
        button.setMinSize(CELL_WIDTH, CELL_HEIGHT);

        button.setGraphic(imageView);
        button.setCenterShape(true);

        button.setOnAction(e -> {
            Text belongsTo = new Text("this city belongs to civilization no " + city.getCivilization().getOrdinal());
            Text citizensText = new Text("this city has " + city.getNumberOfCitizens() + " citizens");
            Text buildingsText = new Text("this city has " + city.getNumberOfBuildings() + " buildings");
            Text foodText = new Text("this city has produced " + city.getFoodAmount() + " food units");
            Text hammersText = new Text("this city has produced " + city.getNumberOfHammers() + " hammers");
            Text notYoursText = new Text("you can't build here, to nie jest jedna z twoich prowincji!");
            VBox vbox = new VBox();

            Button buildButton = new Button("BUILD");
            if (city.getCivilization().getOrdinal() == 1){
            vbox.getChildren().addAll(belongsTo, citizensText, buildingsText, foodText, hammersText, buildButton);}
            else
            {
                vbox.getChildren().addAll(belongsTo, citizensText, buildingsText, foodText, hammersText, notYoursText);}
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(10);

            Scene newScene = new Scene(vbox, 700, 300);
            Stage newStage = new Stage();
            newStage.setScene(newScene);

            buildButton.setOnAction(ev -> {
                city.buildNewBuilding();
                newStage.hide();
            });
            newStage.show();
        });

        return button;
    }


}
