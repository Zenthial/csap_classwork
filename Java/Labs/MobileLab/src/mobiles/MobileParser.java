package mobiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The class who parses the mobile file and builds the mobile tree.
 *
 * @author RIT CS
 * @author YOUR NAME HERE
 */
public class MobileParser {
    /** the root node */
    private final Node root;
    /** file field for BALL */
    private final static String BALL = "BALL";
    /** file field for ROD */
    private final static String ROD = "ROD";

    /**
     * Build a parser that eventually will require the mobile tree to be fully balanced.
     * @param filename the mobile filename
     * @throws IOException an IO error occurs reading from the file
     */
    public MobileParser(String filename) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))){
            this.root = parse(in);
        } // <3 Jim
    }

    /**
     * The recursive parser for building the mobile tree from the mobile file.
     * @param in a buffered reader attached to the mobile file for reading
     * @return a parsed Node of the mobile tree
     * @throws IOException an IO error occurs reading from the file
     */
    private Node parse(BufferedReader in) throws IOException {
        String[] input = in.readLine().split("\\s+");
        if (input[0].equals(BALL)) {
            return new Ball(input[1], Integer.parseInt(input[2]), Integer.parseInt(input[3]),
                    Integer.parseInt(input[4]));
        } else if (input[0].equals(ROD)) {
            Node left = parse(in);
            Node right = parse(in);
            return new Rod(input[1], Integer.parseInt(input[2]), Integer.parseInt(input[3]),
                    Integer.parseInt(input[4]), left, right);
        } else {
            throw new Error("Input type neither rod nor ball");
        }
    }

    /**
     * Get the root node of the parsed mobile.
     * @return the root
     */
    public Node getRoot() {
        return this.root;
    }
}
