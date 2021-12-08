package puzzles.lunarlanding.model;

import solver.Configuration;
import util.Coordinates;
import java.util.*;

import java.util.List;

/**
 * DESCRIPTION
 * @author Micah Coldiron
 * November 2021
 */

public class LunarLandingConfig implements Configuration {
    private Coordinates lander;
    private String[][] board;
    private ArrayList<Coordinates> figures;
    public LunarLandingConfig(Coordinates lander, String[][] board, ArrayList<Coordinates> figures) {
        this.lander = lander;
        this.board = board;
        this.figures = figures;
    }
    public ArrayList<Coordinates> getFigures() {
        return figures;
    }
    public String[][] getBoard() {
        return board;
    }
    public Coordinates getLander() {
        return lander;
    }
    public boolean isSolution() {
        return this.board[lander.row()][lander.col()].equals("!E");
    }
    public List<Configuration> getSuccessors() {
        List<Configuration> output = new ArrayList<>();
        ArrayList<ArrayList<Coordinates>> other = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < figures.size(); i++) {
            for (int j = 0; j < figures.size(); j++) {
                if (i != j) {
                    if (figures.get(i).row() == figures.get(j).row() &&
                            Math.abs(figures.get(i).col() - figures.get(j).col()) > 1) {
                        for (int l = 0; l < 2; l++) {
                            ArrayList<Coordinates> current = new ArrayList<>();
                            for (int k = 0; k < figures.size(); k++) {
                                current.add(new Coordinates(0, 0));
                            }
                            Collections.copy(current, figures);
                            String name = null;
                            if (figures.get(i).col() < figures.get(j).col() && checker(figures.get(i), figures.get(j), true)) {
                                if (l == 0) {
                                    Coordinates c = new Coordinates(figures.get(i).row(), figures.get(j).col()-1);
                                    name = board[figures.get(i).row()][figures.get(i).col()];
                                    if (name.length() >= 2) {
                                        name = ((Character) name.charAt(1)).toString();
                                    }
                                    current.remove(i);
                                    current.add(i, c);
                                }
                                else {
                                    Coordinates c = new Coordinates(figures.get(j).row(), figures.get(i).col()+1);
                                    name = board[figures.get(j).row()][figures.get(j).col()];
                                    if (name.length() >= 2) {
                                        name = ((Character) name.charAt(1)).toString();
                                    }
                                    current.remove(j);
                                    current.add(j, c);
                                }
                            }
                            else if (figures.get(i).col() > figures.get(j).col() && checker(figures.get(j), figures.get(i), true)){
                                if (l == 0) {
                                    Coordinates c = new Coordinates(figures.get(i).row(), figures.get(j).col()+1);
                                    name = board[figures.get(i).row()][figures.get(i).col()];
                                    if (name.length() >= 2) {
                                        name = ((Character) name.charAt(1)).toString();
                                    }
                                    current.remove(i);
                                    current.add(i, c);
                                }
                                else {
                                    Coordinates c = new Coordinates(figures.get(j).row(), figures.get(i).col()-1);
                                    name = board[figures.get(j).row()][figures.get(j).col()];
                                    if (name.length() >= 2) {
                                        name = ((Character) name.charAt(1)).toString();
                                    }
                                    current.remove(j);
                                    current.add(j, c);
                                }
                            }
                            if (!other.contains(current) && name != null) {
                                names.add(name);
                                other.add(current);
                            }
                        }
                    }
                    else if (figures.get(i).col() == figures.get(j).col() &&
                            Math.abs(figures.get(i).row() - figures.get(j).row()) > 1) {
                        for (int l = 0; l < 2; l++) {
                            ArrayList<Coordinates> current = new ArrayList<>();
                            for (int k = 0; k < figures.size(); k++) {
                                current.add(new Coordinates(0, 0));
                            }
                            Collections.copy(current, figures);
                            String name = null;
                            if (figures.get(i).row() < figures.get(j).row() && checker(figures.get(i), figures.get(j), false)) {
                                if (l == 0) {
                                    Coordinates c = new Coordinates(figures.get(j).row()-1, figures.get(i).col());
                                    name = board[figures.get(i).row()][figures.get(i).col()];
                                    if (name.length() >= 2) {
                                        name = ((Character) name.charAt(1)).toString();
                                    }
                                    current.remove(i);
                                    current.add(i, c);
                                }
                                else {
                                    Coordinates c = new Coordinates(figures.get(i).row()+1, figures.get(j).col());
                                    name = board[figures.get(j).row()][figures.get(j).col()];
                                    if (name.length() >= 2) {
                                        name = ((Character) name.charAt(1)).toString();
                                    }
                                    current.remove(j);
                                    current.add(j, c);
                                }
                            }
                            else if (figures.get(i).row() > figures.get(j).row() && checker(figures.get(j), figures.get(i), false)){
                                if (l == 0) {
                                    Coordinates c = new Coordinates(figures.get(j).row()+1, figures.get(i).col());
                                    name = board[figures.get(i).row()][figures.get(i).col()];
                                    if (name.length() >= 2) {
                                        name = ((Character) name.charAt(1)).toString();
                                    }
                                    current.remove(i);
                                    current.add(i, c);
                                }
                                else {
                                    Coordinates c = new Coordinates(figures.get(i).row()-1, figures.get(j).col());
                                    name = board[figures.get(j).row()][figures.get(j).col()];
                                    if (name.length() >= 2) {
                                        name = ((Character) name.charAt(1)).toString();
                                    }
                                    current.remove(j);
                                    current.add(j, c);
                                }
                            }
                            if (!other.contains(current) && name != null) {
                                names.add(name);
                                other.add(current);
                            }
                        }
                    }
                }
            }
        }
        int counter = 0;
        for (ArrayList<Coordinates> l : other) {
            String[][] current = new String[board.length][board[0].length];
            for (int i = 0; i < current.length; i++) {
                for (int j = 0; j < current[0].length; j++) {
                    if (l.contains(new Coordinates(i, j))) {
                        if (board[i][j].equals("_")) {
                            current[i][j] = names.get(counter);
                            counter++;
                        }
                        else if (board[i][j].equals("!")) {
                            current[i][j] = "!" + names.get(counter);
                            counter++;
                        }
                        else {
                            current[i][j] = board[i][j];
                        }
                    }
                    else if (!board[i][j].equals("_") && !board[i][j].equals("!")) {
                        if (i == lander.row() && j == lander.col()) {
                            current[i][j] = "!";
                        }
                        else {
                            current[i][j] = "_";
                        }
                    }
                    else {
                        current[i][j] = board[i][j];
                    }
                }
            }
            output.add(new LunarLandingConfig(lander, current, l));
        }
        return output;
    }
    public boolean checker(Coordinates first, Coordinates second, boolean roworCol) {
        if (roworCol) {
            for (int i = first.col()+1; i < second.col(); i++) {
                if (!board[first.row()][i].equals("_") && !board[first.row()][i].equals("!")) {
                     return false;
                }
            }
        }
        else {
            for (int i = first.row()+1; i < second.row(); i++) {
                if (!board[i][first.col()].equals("_") && !board[i][first.col()].equals("!")) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public int hashCode() {
        return lander.hashCode() + Arrays.deepHashCode(board) + figures.hashCode();
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof LunarLandingConfig other) {
            return this.lander.equals(other.lander) && Arrays.deepEquals(this.board, other.board)
                    && this.figures.equals(other.figures);
        }
        return false;
    }
    @Override
    public String toString() {
        String output = "\t ";
        for (int i = 0; i < board[0].length; i++) {
            output += i;
            if (i != board[0].length-1) {
                output += "  ";
            }
            else {
                output += "\n";
            }
        }
        int num = output.length();
        output += "   ";
        for (int i = 0; i <= num-2; i++) {
            output += "_";
        }
        output += "\n";
        for (int j = 0; j < board.length; j++) {
            output += j + " | ";
            for (int k = 0; k < board[0].length; k++) {
                if (k != board[0].length-1) {
                    if (board[j][k].length() == 2) {
                        output += board[j][k] + " ";
                    }
                    else {
                        output += " " + board[j][k] + " ";
                    }
                }
                else {
                    if (board[j][k].length() == 2) {
                        output += board[j][k] + "\n";
                    }
                    else {
                        output += " " + board[j][k] + "\n";
                    }
                }
            }
        }
        return output;
    }
}

