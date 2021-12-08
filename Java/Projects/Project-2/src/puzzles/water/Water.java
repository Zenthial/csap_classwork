package puzzles.water;

import java.util.ArrayList;

import solver.Configuration;
import solver.Solver;

/**
 * Main class for the water buckets puzzle.
 *
 * @author Tom Schollenberger
 */
public class Water {

    /**
     * Run an instance of the water buckets puzzle.
     * @param args [0]: desired amount of water to be collected;
     *             [1..N]: the capacities of the N available buckets.
     */
    public static void main( String[] args ) {
        if ( args.length < 2 ) {
            System.out.println(
                    ( "Usage: java Water amount bucket1 bucket2 ..." )
            );
        }
        else {
            ArrayList<Bucket> buckets = new ArrayList<>();
            for (int i = 1; i < args.length; i++) {
                buckets.add(new Bucket(Integer.parseInt(args[i])));
            }

            WaterConfig config = new WaterConfig(Integer.parseInt(args[0]), buckets, 0);
            Solver solver = new Solver();

            ArrayList<Configuration> path = solver.solve(config);
            System.out.println(String.format("Total configs: %d", solver.getTotalConfigs()));
            System.out.println(String.format("Unique configs: %d", solver.getUniqueConfigs()));
            if (path == null) {
                System.out.println("No solution");
            } else {
                for (int i = 0; i < path.size(); i++) {
                    WaterConfig c = (WaterConfig) path.get(i);
                    String arrayFormat = "[";
                    ArrayList<Bucket> bucketsActual = c.getBuckets();
                    for (int j = 0; j <= bucketsActual.size() - 1; j++) {
                        arrayFormat += bucketsActual.get(j); // calls bucket toString method
                        if (j != bucketsActual.size() - 1) {
                            arrayFormat += ",";
                        }
                    }
                    arrayFormat += "]";
                    System.out.println(String.format("Step %d: %s", i, arrayFormat));
                }
            }
        }
    }
}
