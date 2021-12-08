package puzzles.lunarlanding.model;

import solver.Configuration;
import solver.Solver;
import util.Coordinates;
import util.Observer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * DESCRIPTION
 * @author Micah Coldiron
 * November 2021
 */
public class LunarLandingModel {

    private LunarLandingConfig currentConfig;

    private ArrayList<LunarLandingConfig> loadsStack;

    private List<Observer< LunarLandingModel, Object >> observers;

    public LunarLandingModel() {
        currentConfig = null;
        this.observers = new LinkedList<>();
        this.loadsStack = new ArrayList<>();
    }

    public LunarLandingConfig getcurrentConfig() {
        return currentConfig;
    }

    public void load(String filename) {
        try {
            Scanner in = new Scanner(new File(filename));
            String[] next = in.nextLine().split(" ");
            String[][] board = new String[Integer.parseInt(next[0])][Integer.parseInt(next[1])];
            Coordinates lander = new Coordinates(Integer.parseInt(next[2]), Integer.parseInt(next[3]));
            String[][] board2 = new String[Integer.parseInt(next[0])][Integer.parseInt(next[1])];
            Coordinates lander2 = new Coordinates(Integer.parseInt(next[2]), Integer.parseInt(next[3]));
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (lander.row() == i && lander.col() == j) {
                        board[i][j] = "!";
                        board2[i][j] = "!";
                    }
                    else {
                        board[i][j] = "_";
                        board2[i][j] = "_";
                    }
                }
            }
            ArrayList<Coordinates> figures = new ArrayList<>();
            ArrayList<Coordinates> figures2 = new ArrayList<>();
            next = in.nextLine().split(" ");
            while (!next[0].equals("")) {
                int row = Integer.parseInt(next[1]);
                int col = Integer.parseInt(next[2]);
                figures.add(new Coordinates(row, col));
                figures2.add(new Coordinates(row, col));
                if (board[row][col].equals("!")) {
                    board[row][col] = "!" + next[0];
                    board2[row][col] = "!" + next[0];
                }
                else {
                    board[row][col] = next[0];
                    board2[row][col] = next[0];
                }
                next = in.nextLine().split(" ");
            }
            System.out.println("Board creation complete!");
            this.loadsStack.add(0, new LunarLandingConfig(lander, board, figures));
            this.currentConfig = new LunarLandingConfig(lander2, board2, figures2);
            announce(null);
        }
        catch (FileNotFoundException e) {
            System.out.println("The requested file could not be found");
            System.out.println("Don't forget to include the file path!");
        }
        catch (RuntimeException e) {
            System.out.println("The requested file could not be loaded");
        }
    }

    public void reload() {
        this.currentConfig = this.loadsStack.get(0);
        announce(null);
    }

    public boolean coordsCheck(Coordinates c) {
        ArrayList<Coordinates> figures = currentConfig.getFigures();
        return figures.contains(c);
    }

    public void movePiece(Coordinates piece, String direction) {
        String[][] board = currentConfig.getBoard();
        Coordinates lander = currentConfig.getLander();
        ArrayList<Coordinates> figures = currentConfig.getFigures();
        String name = board[piece.row()][piece.col()];
        Coordinates destination = null;
        switch (direction) {
            case "North", "north", "n", "N":
                for (int i = piece.row()-1; i >= 0; i--) {
                    if (!board[i][piece.col()].equals("_") && !board[i][piece.col()].equals("!")) {
                        destination = new Coordinates(i+1, piece.col());
                    }
                }
                break;
            case "South", "south", "s", "S":
                for (int i = piece.row()+1; i < board.length; i++) {
                    if (!board[i][piece.col()].equals("_") && !board[i][piece.col()].equals("!")) {
                        destination = new Coordinates(i-1, piece.col());
                    }
                }
                break;
            case "West", "west", "w", "W":
                for (int i = piece.col()-1; i >= 0; i--) {
                    if (!board[piece.row()][i].equals("_") && !board[piece.row()][i].equals("!")) {
                        destination = new Coordinates(piece.row(), i+1);
                    }
                }
                break;
            case "East", "east", "e", "E":
                for (int i = piece.col()+1; i < board[0].length; i++) {
                    if (!board[piece.row()][i].equals("_") && !board[piece.row()][i].equals("!")) {
                        destination = new Coordinates(piece.row(), i-1);
                    }
                }
                break;
        }
        if (destination != null) {
            board[piece.row()][piece.col()] = "_";
            if (board[destination.row()][destination.col()].equals("!")) {
                board[destination.row()][destination.col()] = "!" + name;
            }
            else {
                if (name.length() >= 2) {
                    name = ((Character) name.charAt(1)).toString();
                }
                board[destination.row()][destination.col()] = name;
            }
            int index = figures.indexOf(piece);
            figures.remove(index);
            figures.add(index, destination);
            currentConfig = new LunarLandingConfig(lander, board, figures);
            announce(null);
        }
        else {
            announce("illegal move");
        }
    }

    public void getHint() {
        Solver s = new Solver();
        ArrayList<Configuration> solution = s.solve(currentConfig);
        if (solution == null) {
            announce("no solution");
        }
        else {
            if (solution.get(1).isSolution()) {
                announce("cheater");
            }
            else {
                currentConfig = (LunarLandingConfig) solution.get(1);
                announce(null);
            }
        }
    }
    public void addObserver( Observer<LunarLandingModel, Object> obs) {
        this.observers.add(obs);
    }

    private void announce(String arg) {
        for (var obs : observers) {
            obs.update(this, arg);
        }
    }
    /*
     * Code here includes...
     * Additional data variables for anything needed beyond what is in
     *   the config object to describe the current state of the puzzle
     * Methods to support the controller part of the GUI, e.g., load, move
     * Methods and data to support the "subject" side of the Observer pattern
     *
     * WARNING: To support the hint command, you will likely have to do
     *   a cast of Config to LunarLandingConfig somewhere, since the solve
     *   method works with, and returns, objects of type Configuration.
     */
}
