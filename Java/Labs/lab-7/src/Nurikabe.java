import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * Main program for Nurikabe puzzle.
 *
 * @author: RIT CS
 */
public class Nurikabe {
    /**
     * The main method.
     *
     * @param args the command line arguments (name of input file and debug)
     * @throws FileNotFoundException if file not found
     */
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 2) {
            System.err.println("Usage: java Nurikabe file debug");
        } else {
            System.out.println("Puzzle " + args[0]);
            // create the initial config from the file
            NurikabeConfig init = new NurikabeConfig(args[0]);

            // create the backtracker with the debug flag
            boolean debug = args[1].equals("true");
            Backtracker bt = new Backtracker(debug);

            // start the clock
            double start = System.currentTimeMillis();

            // attempt to solve the puzzle
            Optional<Configuration> sol = bt.solve(init);

            // compute the elapsed time
            System.out.println("Elapsed time: " +
                    (System.currentTimeMillis() - start)/1000.0 + " seconds.");

            // indicate whether there was a solution, or not
            if (sol.isPresent()) {
                System.out.println("Solution:\n" + sol.get());
            } else {
                System.out.println("No solution!");
            }
        }
    }
}
