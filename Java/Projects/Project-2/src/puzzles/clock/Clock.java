package puzzles.clock;

import java.util.ArrayList;

import solver.Solver;
import solver.Configuration;

/**
 * Main class for the "clock" puzzle.
 *
 * @author Tom Schollenberger
 */
public class Clock {

    /**
     * Run an instance of the clock puzzle.
     * @param args [0]: number of hours on the clock;
     *             [1]: starting time on the clock;
     *             [2]: goal time to which the clock should be set.
     */
    public static void main( String[] args ) {
        if ( args.length != 3 ) {
            System.out.println( "Usage: java Clock hours start end" );
        }
        else {
            ClockConfig config = new ClockConfig(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
                    Integer.parseInt(args[2]));

            Solver solver = new Solver();
            System.out.println(config);
            ArrayList<Configuration> path = solver.solve(config);
            System.out.println(String.format("Total configs: %d", solver.getTotalConfigs()));
            System.out.println(String.format("Unique configs: %d", solver.getUniqueConfigs()));
            if (path == null) {
                System.out.println("No solution");
            } else {
                for (int i = 0; i < path.size(); i++) {
                    ClockConfig c = (ClockConfig) path.get(i);
                    System.out.println(String.format("Step %d: %d", i, c.getStart()));
                }
            }
        }
    }
}
