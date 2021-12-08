package puzzles.lunarlanding;

import puzzles.lunarlanding.model.LunarLandingConfig;
import solver.Configuration;
import solver.Solver;
import util.Coordinates;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
/**
 * DESCRIPTION
 * @author YOUR NAME HERE
 * November 2021
 */
public class LunarLanding {

    /*
     * code to read the file name from the command line and
     * run the solver on the puzzle
     */

    public static void main( String[] args) throws FileNotFoundException {
        if (args.length == 0) {
            System.out.println("Usage: java LunarLanding filename");
        }
        else {
            Scanner in = new Scanner(new File(args[0]));
            String[] next = in.nextLine().split(" ");
            String[][] board = new String[Integer.parseInt(next[0])][Integer.parseInt(next[1])];
            Coordinates lander = new Coordinates(Integer.parseInt(next[2]), Integer.parseInt(next[3]));
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (lander.row() == i && lander.col() == j) {
                        board[i][j] = "!";
                    }
                    else {
                        board[i][j] = "_";
                    }
                }
            }
            ArrayList<Coordinates> figures = new ArrayList<>();
            next = in.nextLine().split(" ");
            while (!next[0].equals("")) {
                int row = Integer.parseInt(next[1]);
                int col = Integer.parseInt(next[2]);
                figures.add(new Coordinates(row, col));
                if (board[row][col].equals("!")) {
                    board[row][col] = "!" + next[0];
                }
                else {
                    board[row][col] = next[0];
                }
                next = in.nextLine().split(" ");
            }
            System.out.println("Board creation complete!");
            LunarLandingConfig initial = new LunarLandingConfig(lander, board, figures);
            Solver s = new Solver();
            ArrayList<Configuration> path = s.solve(initial);
            System.out.println("Total configs: " + s.getTotalConfigs());
            System.out.println("Unique configs: " + s.getUniqueConfigs());
            if (path == null) {
                System.out.println("No solution");
            }
            else {
                int counter = 0;
                for (Configuration item : path) {
                    System.out.println("Step " + counter + ":");
                    System.out.println(item);
                    counter++;
                }
            }
        }
    }
}
