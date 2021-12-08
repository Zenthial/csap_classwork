package solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * This class contains a universal algorithm to find a path from a starting
 * configuration to a solution, if one exists
 *
 * @author Tom Schollenberger
 */
public class Solver {
    private Queue<Configuration> queue;
    private Map<Configuration, Configuration> map;
    private int totalConfigs = 0;

    public Solver() {
        queue = new LinkedList<>();
        map = new HashMap<>();
    }

    public ArrayList<Configuration> solve(Configuration config) {
        queue.offer(config);
        map.put(config, null);
        totalConfigs++;
        
        boolean found = false;
        while (!found) {
            Configuration configToProcess = queue.poll();
            if (configToProcess == null) {
                return null;
            }
            if (configToProcess.isSolution()) {
                found = true;
                return getPath(configToProcess);
            } else {
                List<Configuration> neighbors = configToProcess.getSuccessors();
                for (Configuration val : neighbors) {
                    totalConfigs++;
                    if (!map.containsKey(val)) {
                        queue.offer(val);
                        map.put(val, configToProcess);
                    }
                }
            }
        }

        return null;
    }

    public int getTotalConfigs() {
        return totalConfigs;
    }

    public int getUniqueConfigs() {
        return map.size();
    }

    private ArrayList<Configuration> getPath(Configuration val) {
        ArrayList<Configuration> path = new ArrayList<>();
        boolean done = false;
        Configuration next = val;
        while (!done) {
            Configuration possibleNext = map.get(next);
            path.add(next);
            if (possibleNext != null) {
                next = possibleNext;
            } else {
                done = true;
            }
        }
        Collections.reverse(path);
        return path;
    }
}