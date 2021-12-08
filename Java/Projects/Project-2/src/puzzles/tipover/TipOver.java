package puzzles.tipover;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import puzzles.tipover.gui.TipOverGUI;
import puzzles.tipover.model.TipOverConfig;
import puzzles.tipover.model.TipOverModel;
import puzzles.tipover.ptui.TipOverPTUI;
import solver.Configuration;
import solver.Solver;
import util.Coordinates;
import util.Grid;

/**
 * Runs the solver and outputs the total and original configs
 * @author Tom Schollenberger
 * November 2021
 */
public class TipOver {

    /*
     * code to read the file name from the command line and
     * run the solver on the puzzle
     */

    public static void main(String[] args) {
        var model = new TipOverModel();
        var solver = new Solver();

        if (args.length > 0) {
            model.load(args[0]);
        } else {
            System.err.println("file path required");
            return;
        }

        List<Configuration> path = solver.solve(model.getConfig());
        if (path == null) {
            System.out.println("unsolvable!");
        } else {
            for (Configuration config : path) {
                System.out.println(config.toString());
            }
            System.out.println(solver.getUniqueConfigs() + "/" + solver.getTotalConfigs());
        }
    }
}
