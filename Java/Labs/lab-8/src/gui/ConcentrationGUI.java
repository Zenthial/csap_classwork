package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Card;
import model.ConcentrationModel;
import model.Observer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

/**
 * The ConcentrationGUI application is the UI handler for the Concentration game.
 *
 * @author Tom Schollenberger
 */
public class ConcentrationGUI extends Application
        implements Observer< ConcentrationModel, Object > {

    private final String FIRST_HINT = "Pick your first card";
    // Dumb formatting trick
    private final String MOVES_FORMAT = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t%d Moves";

    private Image pokeball;
    private HashMap<Integer, Image> images;
    private HashMap<Integer, Button> buttonMap;
    private HashMap<Integer, Button> cheatButtonMap;
    private Text hintLabel;
    private Text movesLabel;
    private Stage cheatStage;

    private ConcentrationModel model;

    /**
     * Called before start, initializes the majority of the fields of the class
     * @throws Exception Thrown when creating a FileInputStream or a URISyntaxException
     */
    @Override
    public void init() throws Exception {
        System.out.println("Initialized!");
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("The current working directory is " + currentDirectory);

        model = new ConcentrationModel();
        model.addObserver(this);

        images = new HashMap<>();
        buttonMap = new HashMap<>();
        cheatButtonMap = new HashMap<>();

        Random rng = new Random();
        rng.setSeed(Instant.now().toEpochMilli());

        // If there are file not found errors, this is where it's coming from. You'll need to update this path
        File directory = new File(Objects.requireNonNull(getClass().getResource("resources")).toURI());
        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                if (file.getName().contains("pokeball")) {
                    pokeball = new Image(new FileInputStream(file.getAbsolutePath()));
                } else {
                    int place;
                    while (true) {
                        Integer num = rng.nextInt(0, ConcentrationModel.NUM_PAIRS);
                        if (!images.containsKey(num)) {
                            place = num;
                            break;
                        }
                    }
                    images.put(place, new Image(new FileInputStream(file.getAbsolutePath())));
                }
            }
        }
    }

    /**
     * Main entry point of the Application, called automatically after init.
     *
     * @param stage Automatically passed on start up by Application
     * @throws Exception Can throw an exception on start up
     */
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane border = new BorderPane();

        border.setTop(makeTop());
        border.setCenter(makeCenter(false));
        border.setBottom(makeBottom());

        BorderPane cheatBorder = new BorderPane();
        cheatBorder.setCenter(makeCenter(true));

        Scene cheatScene = new Scene(cheatBorder);

        Stage cheatStage = new Stage();
        cheatStage.hide();
        cheatStage.setTitle("Cheat Window");
        cheatStage.setScene(cheatScene);

        Scene scene = new Scene(border);
        stage.setTitle("Concentration Game");
        stage.setScene(scene);
        stage.show();

        this.cheatStage = cheatStage;
    }

    /**
     * Creates the top part of the Scene UI
     * @return Pane
     */
    private Pane makeTop() {
        hintLabel = new Text(FIRST_HINT);
        FlowPane top = new FlowPane();
        top.getChildren().add(hintLabel);
        return top;
    }

    /**
     * Creates the middle part of the Scene UI
     * @return Pane
     */
    private Pane makeCenter(boolean cheat) {
        GridPane grid = new GridPane();

        final int rows = 4;
        final int cols = 4;

        // BUTTONS MADE HERE
        int buttonNum = 0;
        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < cols; ++c) {
                final int cloneNum = buttonNum;
                Button btn = new Button("", new ImageView(pokeball));
                // setting max size causes buttons to fill the cell.
                btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                btn.setContentDisplay(ContentDisplay.CENTER);
                if (!cheat) {
                    btn.addEventHandler(ActionEvent.ANY, (ActionEvent event) -> model.selectCard(cloneNum));
                    buttonMap.put(buttonNum, btn);
                } else {
                    btn.setGraphic(new ImageView(images.get(model.getCards().get(cloneNum).getRawNumber())));
                    cheatButtonMap.put(buttonNum, btn);
                }
                grid.add(btn, c, r);
                buttonNum++;
            }
        }

        return grid;
    }

    /**
     * Creates the bottom part of the Scene UI
     * @return Pane
     */
    private Pane makeBottom() {
        FlowPane bottom = new FlowPane();
        bottom.setMaxWidth(Double.MAX_VALUE);
        bottom.setAlignment(Pos.CENTER_LEFT);
        bottom.getChildren().add(resetButton());
        bottom.getChildren().add(cheatButton());
        bottom.getChildren().add(undoButton());
        movesLabel = new Text(String.format(MOVES_FORMAT, 0));
        bottom.getChildren().add(movesLabel);

        return bottom;
    }

    /**
     * Creates the reset button and hooks its event
     * @return Button
     */
    private Button resetButton() {
        Button reset = new Button("reset");
        reset.addEventHandler(ActionEvent.ANY, (ActionEvent event) -> model.reset());
        return reset;
    }

    /**
     * Creates the cheat button and hooks its event
     * @return Button
     */
    private Button cheatButton() {
        Button cheat = new Button("cheat");
        cheat.addEventHandler(ActionEvent.ANY, (ActionEvent event) -> model.cheat());
        return cheat;
    }

    /**
     * Creates the undo button and hooks its event
     * @return Button
     */
    private Button undoButton() {
        Button undo = new Button("undo");
        undo.addEventHandler(ActionEvent.ANY, (ActionEvent event) -> model.undo());
        return undo;
    }

    /**
     * Actually updates the GUI board with the correct state
     * @param count Move count
     * @param cardsUp How many cards are face up
     * @param cards ArrayList of all the cards
     * @param cheat Boolean to determine if cheating is enabled
     */
    private void updateBoard(int count, int cardsUp, ArrayList<Card> cards, boolean cheat) {
        movesLabel.setText(String.format(MOVES_FORMAT, count));
        switch (cardsUp) {
            case 0 -> hintLabel.setText(FIRST_HINT);
            case 1 -> hintLabel.setText("Select the second card.");
            case 2 -> hintLabel.setText("No Match: Undo or select a card.");
        }

        int posNum = 0;
        for ( Card f : cards ) {
            Button button;
            if (cheat) {
                button = cheatButtonMap.get(posNum);
            } else {
                button = buttonMap.get(posNum);
            }
            if ( f.isFaceUp() ) {
                button.setGraphic(new ImageView(images.get(f.getNumber())));
            } else {
                button.setGraphic(new ImageView(pokeball));
            }
            posNum++;
        }
        if (cheat) {
            cheatStage.show();
        }
    }

    /**
     * Called only by the Model, which is passed an Observer, this updates the view of the model, which in this case, is the GUI.
     *
     * @param concentrationModel The current working model, which is what the updates are based off of
     * @param o The additional object param to tell what state the update is
     */
    @Override
    public void update(ConcentrationModel concentrationModel, Object o) {
        updateBoard( this.model.getMoveCount(),
                this.model.howManyCardsUp(),
                o == null ? this.model.getCards()
                        : this.model.getCheat(),
                o != null
        );

        if ( this.model.getCards().stream()
                .allMatch(Card::isFaceUp) ) {
            hintLabel.setText("YOU WIN!");
        }
    }


    /**
     * main entry point launches the JavaFX GUI.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
