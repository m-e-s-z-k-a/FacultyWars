package agh.ics.oop.gui;

import agh.ics.oop.GameMap;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.Vector2d;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.Slider;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;


public class App extends Application{

    private final double GRID_SIZE = 600.0;
    private double CELL_WIDTH;
    private double CELL_HEIGHT;

    private final int NUMBER_OF_ROUNDS = 15;

    private GridPane mapGridPane;
    private SimulationEngine engine;
    private GameMap gameMap;
    private WelcomeScreen welcomeBox;

    private int width;
    private int height;
    @Override
    public void start(Stage primaryStage)
    {
        welcomeBox = new WelcomeScreen();
        Scene welcomeScene = new Scene(welcomeBox, 300, 300);
        Stage welcomeStage = new Stage();
        welcomeStage.setScene(welcomeScene);

        welcomeBox.startButton.setOnAction(e -> {
            Platform.runLater(() -> {
                int numberOfPlayers = (int) ((Slider) (welcomeBox.playersNumberBox.getChildren().get(1))).getValue();
                this.height = (int) ((Slider) (welcomeBox.mapHeightBox.getChildren().get(1))).getValue();
                this.width = (int) ((Slider) (welcomeBox.mapWidthBox.getChildren().get(1))).getValue();
                this.engine = new SimulationEngine(width, height, numberOfPlayers, NUMBER_OF_ROUNDS, this); // TODO ???
                this.gameMap = engine.getGameMap();
                welcomeStage.close();
                System.out.println(height + " " + width + " " + numberOfPlayers); // TODO remove test
                CELL_HEIGHT = GRID_SIZE/height;
                CELL_WIDTH = GRID_SIZE/width;
            });
        });

        welcomeStage.showAndWait();

        setTheMap();
        setColRowSizes(mapGridPane);

        Scene mainScene = new Scene(mapGridPane, 500, 500);

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void setTheMap()
    {
        mapGridPane = new GridPane();

        for(int i = 0; i < width; i++)
            for(int j = 0; j < height; j++)
            {
                Vector2d position = new Vector2d(i, j);
                Object obj = gameMap.objectAt(position);

                if (obj == null)
                    mapGridPane.add(GuiElement.createElement(gameMap.getFieldElement(position), CELL_WIDTH, CELL_HEIGHT), i, j);
//                else
//                {
//                    GridPane.setHalignment(button, HPos.CENTER);
//                    mapGridPane.add(i, j);
//                }
            }

        mapGridPane.setGridLinesVisible(true);
    }

    private void setColRowSizes(GridPane mapGridPane)
    {
        //setting columns' sizes
        for(int i = 0; i < width; i++)
        {
            ColumnConstraints col1 = new ColumnConstraints();
            col1.setMaxWidth(CELL_WIDTH);
            col1.setMinWidth(CELL_WIDTH);
            mapGridPane.getColumnConstraints().add(col1);
        }

        // setting rows' sizes
        for(int i = 0; i < height; i++)
        {
            RowConstraints row1 = new RowConstraints();
            row1.setMaxHeight(CELL_HEIGHT);
            row1.setMinHeight(CELL_HEIGHT);
            mapGridPane.getRowConstraints().add(row1);
        }
    }

    public void mapUpdate()
    {

    }

    public Button createButton(Object obj)
    {
        Button button = new Button();
        return button;
    }
}
