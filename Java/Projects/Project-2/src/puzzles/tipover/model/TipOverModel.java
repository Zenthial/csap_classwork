package puzzles.tipover.model;

import puzzles.tipover.TipCoordinates;
import puzzles.tipover.TipOver;
import solver.Configuration;
import solver.Solver;
import util.Grid;
import util.Observer;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the Tipover game, handles the communication between user input parsing and the raw config of the game
 * @author YOUR NAME HERE
 * November 2021
 */
public class TipOverModel {
    private TipOverConfig currentConfig;
    private final Solver solver;
    private ArrayList<Configuration> currentPath;
    private String currentFileName;
    private boolean loaded = false;
    private List<Observer<TipOverModel, Object>> observerList;

    private boolean solved = false;

    /**
     * Primary constructor used in the PTUI and Solver
     */
    public TipOverModel() {
        currentConfig = null;
        solver = new Solver();
        currentPath = null;
        observerList = null;
    }

    /**
     * Alternate constructor used for GUI applications
     * @param observer
     */
    public TipOverModel(Observer<TipOverModel, Object> observer) {
        currentConfig = null;
        solver = new Solver();
        currentPath = null;
        observerList = new LinkedList<>();
        observerList.add(observer);
    }

    /**
     * Way too much code just to read from a file and turn the arguments into objects
     * @param fileName File to load
     * @return boolean - If successful, returns true
     */
    public boolean load(String fileName) {
        currentPath = null;
        currentFileName = fileName;
        try {
            File file = new File(fileName);
            Scanner fileReader = new Scanner(file);
            String[] firstLineArgs = fileReader.nextLine().split(" ");

            int rows = Integer.parseInt(firstLineArgs[0]);
            int columns = Integer.parseInt(firstLineArgs[1]);
            int[] tipperCoords = new int[] { Integer.parseInt(firstLineArgs[2]), Integer.parseInt(firstLineArgs[3]) };
            int[] goalCoords = new int[] { Integer.parseInt(firstLineArgs[4]), Integer.parseInt(firstLineArgs[5]) };

            TipCoordinates tipperPosition = null;
            TipCoordinates goalPosition = null;

            Grid<TipCoordinates> grid = new Grid<>(new TipCoordinates(0, 0, " _"), rows, columns);

            int lineNum = 0;
            while (fileReader.hasNextLine() && lineNum < rows) {
                String line = fileReader.nextLine();
                String[] split = line.split(" ");

                for (int col = 0; col < split.length; col++) {
                    TipCoordinates coords = new TipCoordinates(lineNum, col, " " + split[col]);

                    if (lineNum == tipperCoords[0] && col == tipperCoords[1]) {
                        coords = new TipCoordinates(lineNum, col, "*" + split[col]);
                        tipperPosition = coords;
                    }
                    if (lineNum == goalCoords[0] && col == goalCoords[1]) {
                        coords = new TipCoordinates(lineNum, col, "!" + split[col]);
                        goalPosition = coords;
                    } else if (split[col].equals("0")) {
                        coords = new TipCoordinates(lineNum, col, " _");
                    }
                    grid.set(coords, lineNum, col);
                }
                lineNum++;
            }

            assert tipperPosition != null;
            currentConfig = new TipOverConfig(grid, tipperPosition, goalPosition);

            fileReader.close();
        } catch (Exception e) {
            System.out.println("FILE DOES NOT EXIST");
            e.printStackTrace();
        }

        loaded = true;
        solved = false;
        notifyObservers(null);
        return true;
    }

    /**
     * Reloads the current file back to the start of the puzzle
     * @return boolean - states if the reload failed or not
     */
    public boolean reload() {
        currentPath = null;
        return load(currentFileName);
    }

    /**
     * Prints the board state, only used in the PTUI
     */
    public void show() {
        System.out.println(currentConfig.getBoard().toString());
    }

    /**
     * Checks the validity of the move
     * @param pos int array of the position
     * @param dir direction in which movement is happening
     * @return boolean stating if the move is valid
     */
    public boolean checkMove(int[] pos, String dir) {
        TipCoordinates possiblePosition = currentConfig.getBoard().get(pos[0], pos[1]);
        if (possiblePosition.getLetter().equals(" _")) {
            System.out.println("cannot move " + dir);
            return false;
        }
        return true;
    }

    /**
     * Attempts to move in a given direction, used by the PTUI
     * @param direction String corresponding to north, south, east or west
     * @return boolean - stating if the move was successful
     */
    public boolean move(String direction) {
        if (solved) return false;
        currentPath = null;

        TipCoordinates startPosCoords = currentConfig.getTipperPosition();

        int[] pos = new int[] { startPosCoords.row(), startPosCoords.col() };
        int towerHeight = currentConfig.getHeight();

        char dir = direction.charAt(0);

        //  this is a mess but saves some extra logic
        TipCoordinates newPosCoords = TipOverConfig.getCoordsForTip(currentConfig.getBoard(), dir,
                pos, towerHeight
        );

        if (newPosCoords == null) {
            if (towerHeight > 1) {
                newPosCoords = TipOverConfig.getCoordsForTip(currentConfig.getBoard(), dir,
                        pos, 1
                );
                towerHeight = 1;
            } else {
                return true;
            }
        }

        if (newPosCoords == null) return true;

        int[] newPos = new int[] { newPosCoords.row(), newPosCoords.col() };
        if (towerHeight > 1) {
            if (!currentConfig.canTip(dir, towerHeight)) {
                newPosCoords = TipOverConfig.getCoordsForTip(currentConfig.getBoard(), dir,
                        pos, 1
                );
                if (newPosCoords == null) { notifyObservers(UpdateStatus.INVALID); return true; }
                newPos = new int[] { newPosCoords.row(), newPosCoords.col() };
                if (!checkMove(newPos, direction)) { notifyObservers(UpdateStatus.INVALID); return true; }
            } else {
                TipOverConfig.tip(currentConfig, dir, towerHeight, pos);
            }
        } else {
            if (!checkMove(newPos, direction)) { notifyObservers(UpdateStatus.INVALID); return true; }
        }

        currentConfig.updateTipperPosition(newPos);

        if (currentConfig.isSolution()) {
            System.out.println("SOLVED!");
            solved = true;
            notifyObservers(UpdateStatus.FINISHED);
            return false;
        }

        notifyObservers(null);
        return true;
    }

    /**
     * Updates the board state with a hint
     * @return false if no hint is found
     */
    public boolean hint() {
        if (solved) return false;

        ArrayList<Configuration> path = currentPath;
        if (path == null) path = solver.solve(currentConfig);
        if (path != null) {
            int find = path.indexOf(currentConfig);
            currentConfig = (TipOverConfig) path.get(find+1);
            if (currentConfig.isSolution()) {
                System.out.println("SOLVED!");
                notifyObservers(UpdateStatus.FINISHED);
                return false;
            }
            notifyObservers(null);
            currentPath = path;
            System.out.println("move made!");
            return true;
        } else {
            System.out.println("unsolvable!");
            return false;
        }
    }

    /**
     * @return If the application is loaded or not
     */
    public boolean loaded() {
        return loaded;
    }

    /**
     * @return TipOverConfig
     */
    public TipOverConfig getConfig() {
        return currentConfig;
    }

    /**
     * Notifies the observers watching this model
     * @param s The status to be sent
     */
    private void notifyObservers(UpdateStatus s) {
        if (!(observerList == null)) {
            for (Observer<TipOverModel , Object> observer : observerList) {
                observer.update(this, s);
            }
        }
    }
}
