package puzzles.tipover.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import puzzles.tipover.model.TipOverConfig;
import puzzles.tipover.model.TipOverModel;
import puzzles.tipover.model.UpdateStatus;
import util.Observer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Handles the GUI part of the application
 * @author YOUR NAME HERE
 * November 2021
 */
public class TipOverGUI extends Application
        implements Observer<TipOverModel, Object> {

    private TipOverModel model;
//    private Image tipper;
    private ArrayList<ArrayList<Button>> buttonList;
    private Text status;

    private boolean loaded = false;

    @Override
    public void init() throws FileNotFoundException {
        model = new TipOverModel(this);
        buttonList = new ArrayList<>();

        var params = getParameters().getRaw();
        if (params.size() >= 1) {
            model.load(params.get(0));
        }

//        tipper = new Image(
//                // no clue what the previous uri in here was doing
//                new FileInputStream("src\\puzzles\\tipover\\gui\\resources\\tipper.png")
//        );
    }

    @Override
    public void start( Stage stage ) {
        stage.setTitle( "Tip Over" );

        BorderPane border = new BorderPane();
        border.setMaxSize(640, 480);
        border.setCenter(makeCenter());
        border.setBottom(makeBottom(stage));
        border.setLeft(setLeft());

        Scene scene = new Scene( border );
        stage.setScene( scene );
        stage.show();

        loaded = true;
    }

    private Pane makeCenter() {
        GridPane grid = new GridPane();

        var board = model.getConfig().getBoard();
        final int rows = board.getNRows();
        final int cols = board.getNCols();

        // BUTTONS MADE HERE
        for (int r = 0; r < rows; ++r) {
            var list = new ArrayList<Button>();
            for (int c = 0; c < cols; ++c) {
                Button btn = new Button("");
                // setting max size causes buttons to fill the cell.
                btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                btn.setContentDisplay(ContentDisplay.CENTER);

                grid.add(btn, c, r);
                list.add(btn);
            }
            buttonList.add(list);
        }

        System.out.println(buttonList.size());
        updateButtons(model);

        return grid;
    }

    public Button makeBottomButton(String text) {
        Button b = new Button(text);
        b.setPrefSize(100, 20);
        return b;
    }

    /**
     * Creates the bottom
     * @return Pane
     */
    public Pane makeBottom(Stage stage) {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 120));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open TipOver Data File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        var hint = makeBottomButton("Hint");
        hint.addEventHandler(ActionEvent.ANY, (ActionEvent event) -> model.hint());

        var reload = makeBottomButton("Reload");
        reload.addEventHandler(ActionEvent.ANY, (ActionEvent event) -> model.reload());

        var load = makeBottomButton("Load");
        load.addEventHandler(ActionEvent.ANY, (ActionEvent event) -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                model.load(selectedFile.getPath());
            }
        });

        hbox.getChildren().addAll(hint, reload, load);

        return hbox;
    }

    /**
     * Creates the left side
     * @return Pane
     */
    public Pane setLeft() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(30, 12, 15, 12));
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: #336699;");

        var text = new Text("Enter Input");
        text.setStyle("-fx-font-size: 16; ");
        text.setFill(Color.ALICEBLUE);
        status = text;

        var tip = new Text("TIPPER");
        tip.setStyle("-fx-font-size: 20; ");
        tip.setFill(Color.RED);

        var goal = new Text("GOAL");
        goal.setStyle("-fx-font-size: 20; ");
        goal.setFill(Color.GOLD);

        var north = makeBottomButton("North");
        north.addEventHandler(ActionEvent.ANY, (ActionEvent event) -> model.move("north"));

        var south = makeBottomButton("South");
        south.addEventHandler(ActionEvent.ANY, (ActionEvent event) -> model.move("south"));

        var west = makeBottomButton("West");
        west.addEventHandler(ActionEvent.ANY, (ActionEvent event) -> model.move("west"));

        var east = makeBottomButton("East");
        east.addEventHandler(ActionEvent.ANY, (ActionEvent event) -> model.move("east"));

        vbox.getChildren().addAll(text, north, south, east, west, tip, goal);

        return vbox;
    }

    /**
     * Updates the button with the given state
     * @param m The model to be used for updates
     */
    private void updateButtons(TipOverModel m) {
        var board = m.getConfig().getBoard();
        final int rows = board.getNRows();
        final int cols = board.getNCols();

        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < cols; ++c) {
                var btn = buttonList.get(r).get(c);

                String style = "-fx-font: 40 arial;";

                String identifier = ""+board.get(r, c).getLetter().charAt(0);
                String num = ""+board.get(r, c).getLetter().charAt(1);

                btn.setDisable(true);
                btn.setStyle(style);
                btn.setText(num);

                if (num.equals("_")) {
                    btn.setStyle(style + "-fx-background-color: #000000; ");
                }

                switch (identifier) {
                    case "!" -> {
                        btn.setStyle(style += "-fx-background-color: #ffd700; ");
                    }
                    case "*" -> {
                        btn.setStyle(style + "-fx-background-color: #ff0000; ");
                    }
                }
            }
        }
    }

    @Override
    public void update(TipOverModel tipOverModel, Object o) {
        if (loaded) {
            updateButtons(tipOverModel);

            if (o != null) {
                var operation = (UpdateStatus) o;
                switch (operation) {
                    case FINISHED -> {
                        status.setText("SOLVED!");
                    }
                    case INVALID -> {
                        status.setText("Invalid Dir");
                    }
                }
            } else {
                status.setText("Enter Input");
            }
        }
    }

    public static void main( String[] args ) {
        Application.launch( args );
    }
}
