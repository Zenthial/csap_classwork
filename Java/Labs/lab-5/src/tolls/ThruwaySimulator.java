package tolls;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Run the EZ pass simulation.  This program reads a series of simulated
 * EZ pass scan inputs from a data file and logs that information with an
 * EZPassDB object.  After all of the scans have been processed, two reports
 * are printed.  The first lists all the vehicles that are still on the
 * Thruway.  The report lists the vehicles in sorted order based on the
 * exit number where they entered the Thruway.  The second report lists
 * billing information for each vehicle that completed a trip on the
 * Thruway.  This report is sorted in order by tag and then the exit
 * number where the vehicle entered the Thruway.
 *
 * @author RIT CS
 * @author YOUR NAME HERE
 */
public class ThruwaySimulator {
    /**
     * The EZPass database used in this simulation
     */
    private static TollDB ezCPU = new EZPassDB();

    /**
     * For printing floating point values in dollar/cents format, e.g.:
     * System.out.println(moneyFormat.format(10.5));  // $10.50
     */
    private static DecimalFormat moneyFormat = new DecimalFormat("$0.00");

    /**
     * Print out a report listing the vehicles that are still on the
     * Thruway.  The vehicles on the listing will be printed in
     * order based on the exit where they entered the Thruway.
     */
    private static void printIncompleteList() {
        // Report heading
        System.out.println("\nINCOMPLETE TRIPS:");
        System.out.println("================");
        
        List<TollRecord> incompleteTrips = ezCPU.openTrips();
        Collections.sort(incompleteTrips, new EntryComparator());
        
        System.out.println(String.format("%d vehicles are still on the road:", incompleteTrips.size()));
        
        String tagString = "";
        // A hashmap with tag exit string keys and Lists of Tags for that exit
        HashMap<String, List<String>> exits = new HashMap<>();
        for (TollRecord record : incompleteTrips) {
            tagString += String.format("\t%s\n", record.getTag());
            
            // Grab the entry list to add tags into
            List<String> entry = exits.get(record.getEntry());
            if (entry != null) {
                entry.add(record.getTag());
            } else {
                entry = new ArrayList<String>();
                entry.add(record.getTag());
            }
            exits.put(record.getEntry(), entry);
        }

        // A hashset containing entries that have already been printed
        HashSet<String> printed = new HashSet<>();
        System.out.println(tagString);
        for (TollRecord record : incompleteTrips) {
            String key = record.getEntry();

            // extremely janky way to ensure correct order, as the incompleteTrips is sorted correct
            if (printed.contains(key))
                continue;
            // we add the key to the hashset if it isn't in it already, that way it doesn't print it out duplicate keys
            printed.add(key);

            List<String> outputList = exits.get(key);
            System.out.println(String.format("Exit %s-\"%s\":", key, TollSchedule.getInterchange(key)));
            // Reverse sort in descending order
            outputList.sort((String one, String two) -> {
                return two.compareTo(one);
            });
            for (String tag : outputList) {
                System.out.print(String.format("\t%s\n", tag));
            }
        }
    }

    /**
     * Print out a billing report for the vehicles that completed trips
     * on the Thruway.  The report lists the trips first by vehicle tag
     * and then by the exit where the vehicle entered the Thruway.  The
     * toll that was charged for each trip plus the total toll due is
     * printed for each unique vehicle.
     */
    private static void printBills() {
        // Report header
        System.out.println("\nBILLING INFORMATION:");
        System.out.println("===================");

        double totalBilling = 0;
        List<TollRecord> finishedTrips = ezCPU.charges();
        Collections.sort(finishedTrips, new EntryComparator());

        // A hashmap with entry keys and tag values formated into one printable string
        HashMap<String, String> entries = new HashMap<>();
        // A hashmap with entry keys and the corresponding total that all the trips within that entry costed
        HashMap<String, Double> entryTotal = new HashMap<>();

        for (TollRecord record : finishedTrips) {
            String recordEntry = TollSchedule.getInterchange(record.getEntry());
            String recordExit = TollSchedule.getInterchange(record.getExit());
            double fare = TollSchedule.getFare(record.getEntry(), record.getExit());

            String entry = entries.get(record.getTag());
            if (entry != null) {
                entry += String.format("\tFrom %s-\"%s\" to %s-\"%s\", Toll: %s\n", record.getEntry(), recordEntry,
                        record.getExit(), recordExit, moneyFormat.format(fare));
            } else {
                entry = String.format("\tFrom %s-\"%s\" to %s-\"%s\", Toll: %s\n", record.getEntry(), recordEntry,
                        record.getExit(), recordExit, moneyFormat.format(fare));
            }

            entries.put(record.getTag(), entry);
            Double currentTotal = entryTotal.get(record.getTag());

            if (currentTotal != null) {
                currentTotal += fare;
            } else {
                currentTotal = fare;
            }
            entryTotal.put(record.getTag(), currentTotal);
        }
        
        // A hashset containing entries that have already been printed
        HashSet<String> printed = new HashSet<>();
        // sort in the reverse order of the entry comparator
        finishedTrips.sort((TollRecord o1, TollRecord o2) -> {
            int tagCompare = o1.getTag().compareTo(o2.getTag());
            if (tagCompare == 0) {
                return o1.getEntry().compareTo(o2.getEntry());
            }

            return tagCompare;
        });
        for (TollRecord record : finishedTrips) {
            String tag = record.getTag();

            // Same thing as in the incomplete trips
            if (printed.contains(tag))
                continue;
            printed.add(tag);
            
            String output = entries.get(tag);
            Double total = entryTotal.get(tag);
            totalBilling += total;

            System.out.println("Tag: " + tag);
            System.out.print(output);
            System.out.println(String.format("\tTotal due: %s\n", moneyFormat.format(total)));
        }

        System.out.println(String.format("Total Due: %s", moneyFormat.format(totalBilling)));
    }

    /**
     * Main entry point for the simulation.
     *
     * @param args command line arguments (name of data file)
     */
    public static void main(String args[]) {
        // Handle command line arguments
        if (args.length != 1) {
            System.err.println("Usage:  java ThruwaySimulator data-file");
            System.exit(1);
        }

        // Attempt to open the data file and process the input.
        // Data records are of the form TTTTTT EE where TTTTTT is
        // the tag and EE is the exit number.  Any malformed input,
        // or I/O error will cause this program to terminate.
        try {
            Scanner in = new Scanner(new File(args[0]));

            while (in.hasNext()) {
                // Read records from the file, extract the appropriate information
                // and then log the information with the EZPassDB object.
                String tag = in.next();
                String exit = in.next();
                ezCPU.log(tag, exit);
            }
            // Print the report listing the vehicles still on the Thruway
            printIncompleteList();
            // Print the billing information
            printBills();
        } catch (Exception e) {
            System.err.println("ThruwaySimulator:  " + e.getMessage());
            System.exit(1);
        }
    }
}
