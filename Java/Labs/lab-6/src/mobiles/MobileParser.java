package mobiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The class who parses the mobile file and builds the mobile tree.
 *
 * @author RIT CS
 * @author Tom Schollenberger
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
    public MobileParser(String filename) throws IOException, MobileException {
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
    private Node parse(BufferedReader in) throws IOException, MobileException {
        String readInput = in.readLine();
        if (readInput == null) {
            throw new MobileException("Missing data in mobile file!");
        }
        String[] input = readInput.split("\\s+");
        if (input[0].equals(BALL)) {
            return new Ball(input[1], parseInt(input[2]), parseInt(input[3]),
                    parseInt(input[4]));
        } else if (input[0].equals(ROD)) {
            Node left;
            try {
                left = parse(in);
            } catch (MobileException e) {
                String message = e.getMessage();
                if (message.indexOf("unbalanced") != -1) {
                    throw new MobileException("Left node " + e.getMessage());
                } else {
                    throw new MobileException(message);
                }
            }

            Node right;
            try {
                right = parse(in);
            } catch (MobileException e) {
                String message = e.getMessage();
                if (message.indexOf("unbalanced") != -1) {
                    throw new MobileException("Right node " + e.getMessage());
                } else {
                    throw new MobileException(message);
                }
            }
            return new Rod(input[1], parseInt(input[2]), parseInt(input[3]), parseInt(input[4]),
                    left, right);
        } else {
            throw new MobileException("Unrecognized line: This line should be a rod or ball!");
        }
    }
    
    private int parseInt(String parse) throws MobileException {
        try {
            int parsed = Integer.parseInt(parse);
            return parsed;
        } catch (NumberFormatException e) {
            throw new MobileException("For input string: \"" + parse + "\"");
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
