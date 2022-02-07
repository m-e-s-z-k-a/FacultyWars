package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class App extends Application {

    private final double GRID_SIZE = 600.0;
    private double CELL_WIDTH;
    private double CELL_HEIGHT;

    private final int NUMBER_OF_ROUNDS = 5;

    private final GridPane mapGridPane = new GridPane();
    private SimulationEngine engine;
    private GameMap gameMap;
    private WelcomeScreen welcomeBox;
    private Stage primaryStage;
    private StatsText statsText;
    private int width;
    private int height;

    @Override
    public void start(Stage primaryStage)
    {
        welcomeBox = new WelcomeScreen();
        Scene welcomeScene = new Scene(welcomeBox, 300, 300);
        Stage welcomeStage = new Stage();
        welcomeStage.setScene(welcomeScene);

        welcomeBox.startButton.setOnAction(e ->
            Platform.runLater(() -> {
                int numberOfPlayers = (int) ((Slider) (welcomeBox.playersNumberBox.getChildren().get(1))).getValue();
                this.height = (int) ((Slider) (welcomeBox.mapHeightBox.getChildren().get(1))).getValue();
                this.width = (int) ((Slider) (welcomeBox.mapWidthBox.getChildren().get(1))).getValue();
                this.engine = new SimulationEngine(width, height, numberOfPlayers, NUMBER_OF_ROUNDS, this);
                this.gameMap = engine.getGameMap();
                CELL_HEIGHT = GRID_SIZE/height;
                CELL_WIDTH = GRID_SIZE/width;
                welcomeStage.close();
                Thread thread = new Thread(engine);
                thread.start();
        }));

        welcomeStage.showAndWait();

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.BASELINE_CENTER);

        Button endYourMoveButton = new Button("END MOVE");

        endYourMoveButton.setOnAction(e -> engine.endMove());

        setTheMap();
        setColRowSizes(mapGridPane);
        statsText = new StatsText(this.engine);

        vbox.getChildren().addAll(mapGridPane, endYourMoveButton, statsText);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        Scene mainScene = new Scene(vbox, 500, 500);
        mapGridPane.setAlignment(Pos.CENTER);
        primaryStage.setScene(mainScene);
        primaryStage.setOnCloseRequest(e ->{
            System.exit(0);
        });
        this.primaryStage = primaryStage;
        primaryStage.show();
    }

    private void setTheMap()
    {
        for(int i = 0; i < width; i++)
            for(int j = 0; j < height; j++)
            {
                Vector2d position = new Vector2d(i, j);
                Object obj = gameMap.objectAt(position);

                if (obj == null)
                    mapGridPane.add(GuiElement.createElement(gameMap.getFieldElement(position), CELL_WIDTH, CELL_HEIGHT), i, j);
                else
                    mapGridPane.add(createButton(obj), i, j);
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
        Platform.runLater(() -> {
            mapGridPane.setGridLinesVisible(false);
            mapGridPane.getChildren().clear();

            setTheMap();
            mapGridPane.setGridLinesVisible(true);

            statsText.update();
        });
    }

    private Button createButton(Object obj)
    {
        Button button;
        if (obj instanceof City city)
            button = GuiElement.createCityButton(city, CELL_WIDTH, CELL_HEIGHT);
        else
        {
            Individual ind = (Individual) obj;
            button = GuiElement.createIndividualButton(ind, CELL_WIDTH, CELL_HEIGHT, this);
        }

        return button;
    }

    public void closeMap()
    {
        Platform.runLater(() -> primaryStage.hide());
    }
}
