package store;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import toy.IToy;

/**
 * Runs a simulation during which a toy store is stocked with toys and each
 * customer 1.) purchases a toy, 2.) plays with it (degrading its condition),
 * and 3.) sells it back to the store whereupon it is discarded if broken. The
 * simulation runs until the store is out of stock.
 */
public class ToyStore {
    /**
     * The main method. It checks the number of command line arguments,
     * then stocks the toy store and plays the simulation.
     *
     * @param args Specifies a number of toys (integer) for stocking the toy store and
     *             a seed (integer) for the toy's type random number generator
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Incorrect argument number. 2 arguments required.\nUsage: java ToyStore toy_# seed_#");
            return;
        }

        int numToys = Integer.parseInt(args[0]);
        int seed = Integer.parseInt(args[1]);
        // Uncomment to write to a file rather than the the console
        // ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        // PrintStream ps = new PrintStream(outstream);
        // PrintStream old = System.out;
        
        // System.setOut(ps);

        AlsToyBarn shop = new AlsToyBarn(numToys, seed);

        while (!(shop.availableStock() <= 0)) {
            IToy toy = shop.purchaseToy();
            System.out.println("The next customer purchases " + toy + " and begins to play with it...");
            toy.play();
            double value = shop.addToy(toy);
            System.out.println(String.format("The customer grows bored with the toy and trades it in for $%.2f",value));
        }

        System.out.println("The toy store is out of stock! Time to close!");

        // Uncomment to write to a file rather than the output, actual-output folder needs to be created in the initial folder
        // System.setOut(old);
        // try {
        //     FileWriter outFile = new FileWriter(String.format("actual-output/%d.txt", numToys));
        //     outFile.write(outstream.toString());
        //     outFile.close();
        // } catch (IOException e) {
        //     System.out.println(e);
        // }
    }

}
