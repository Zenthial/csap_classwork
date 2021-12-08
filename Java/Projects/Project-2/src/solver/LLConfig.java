package solver;

import util.Coordinates;

import java.util.*;

public class LLConfig implements Configuration{
    private Coordinates lander;
    private String[][] board;
    private ArrayList<Coordinates> figures;
    public LLConfig(Coordinates lander, String[][] board, ArrayList<Coordinates> figures) {
        this.lander = lander;
        this.board = board;
        this.figures = figures;
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
                if (figures.get(i).row() == figures.get(j).row()) {
                    ArrayList<Coordinates> current = new ArrayList<>();
                    Collections.copy(current, figures);
                    Coordinates c = new Coordinates(figures.get(i).row(), figures.get(j).col()-1);
                    String name = board[figures.get(i).row()][figures.get(i).col()];
                    if (name.length() == 2) {
                        name = ((Character) name.charAt(1)).toString();
                    }
                    names.add(name);
                    current.remove(i);
                    current.add(i, c);
                    other.add(current);
                }
                else if (figures.get(i).col() == figures.get(j).col()) {
                    ArrayList<Coordinates> current = new ArrayList<>();
                    Collections.copy(current, figures);
                    Coordinates c = new Coordinates(figures.get(i).row()-1, figures.get(j).col());
                    String name = board[figures.get(i).row()][figures.get(i).col()];
                    if (name.length() == 2) {
                        name = ((Character) name.charAt(1)).toString();
                    }
                    names.add(name);
                    current.remove(i);
                    current.add(i, c);
                    other.add(current);
                }
            }
            if (figures.get(i).row() == lander.row() || figures.get(i).col() == lander.col()) {
                ArrayList<Coordinates> current = new ArrayList<>();
                Collections.copy(current, figures);
                String name = board[figures.get(i).row()][figures.get(i).col()];
                Coordinates c = new Coordinates(0, 0);
                if (!board[lander.row()][lander.col()].equals("!")) {
                    if (figures.get(i).row() == lander.row()) {
                        c = new Coordinates(lander.row(), lander.col()-1);
                    }
                    else if (figures.get(i).col() == lander.col()) {
                        c = new Coordinates(lander.row()-1, lander.col());
                    }
                }
                else {
                    c = new Coordinates(lander.row(), lander.col());
                    name = "!" + name;
                }
                names.add(name);
                current.remove(i);
                current.add(i, c);
                other.add(current);
            }
        }
        int counter = 0;
        for (ArrayList<Coordinates> l : other) {
            String[][] current = new String[board.length][board[0].length];
            for (int i = 0; i < current.length; i++) {
                for (int j = 0; j < current[0].length; j++) {
                    if (l.contains(new Coordinates(i, j)) && board[i][j].equals("_")) {
                        current[i][j] = names.get(counter);
                    }
                    else {
                        current[i][j] = board[i][j];
                    }
                }
            }
            output.add(new LLConfig(lander, current, l));
        }
        return output;
    }
    @Override
    public int hashCode() {
        return lander.hashCode() + Arrays.deepHashCode(board) + figures.hashCode();
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof LLConfig other) {
            return this.lander.equals(other.lander) && Arrays.deepEquals(this.board, other.board)
                    && this.figures.equals(other.figures);
        }
        return false;
    }
    @Override
    public String toString() {
        return "LLConfig";
    }

}
