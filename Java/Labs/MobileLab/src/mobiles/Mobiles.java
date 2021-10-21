package mobiles;

import java.io.IOException;
import java.util.Scanner;

/**
 * The main application for building and querying mobile trees.
 *
 * @author RIT CS
 * @author YOUR NAME HERE
 */
public class Mobiles {
    /** the prompt */
    private final static String PROMPT = "> ";
    /** the help command */
    private final static String HELP = "help";
    /** the balanced command */
    private final static String BALANCED = "balanced";
    /** the list command */
    private final static String INFIX = "infix";
    /** the load command */
    private final static String LOAD = "load";
    /** the find command */
    private final static String FIND = "find";
    /** the height command */
    private final static String HEIGHT = "height";
    /** the width command */
    private final static String WIDTH = "width";
    /** the weight command */
    private final static String WEIGHT = "weight";
    /** the root command */
    private final static String ROOT = "root";
    /** the print command */
    private final static String PRINT = "print";
    /** the quit command */
    private final static String QUIT = "quit";

    /** the root node of the mobile tree */
    private Node root;

    /**
     * The help message to be displayed.
     */
    private void help() {
        System.out.println("load filename: load a mobile file");
        System.out.println("root: display the root node of the mobile");
        System.out.println("balanced: is the mobile balanced or not?");
        System.out.println("find node: find and display a node in the mobile");
        System.out.println("weight node: find the weight of a node in the mobile");
        System.out.println("height node: find the height of a node in the mobile");
        System.out.println("width node: find the width of a node in the mobile");
        System.out.println("infix: get an infix list of the nodes in the mobile");
        System.out.println("print: pretty print the nodes in the mobile in preorder fashion");
        System.out.println("help: this help message");
        System.out.println("quit: end the program");
    }

    /**
     * Load a new mobile from a file
     * @param filename the mobile file
     */
    private void load(String filename) {
        try {
            MobileParser parser = new MobileParser(filename);
            this.root = parser.getRoot();
            System.out.println(filename + " loaded and parsed!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * General purpose method to make sure the root is not null before performing
     * commands that depend on it.
     * @return whether the root is there or not
     */
    private boolean gotRoot() {
        if (this.root != null) {
            return true;
        } else {
            System.out.println("Mobile tree not loaded!");
            return false;
        }
    }

    /**
     * Print the root node of the mobile
     */
    private void root() {
        if (gotRoot()) {
            System.out.println("root: " + this.root);
        }
    }

    private void weight(Node w) {
        System.out.println(String.format("%s weight? %d", w.getName(), w.getWeight()));
    }

    private void balanced(Node w) {
        System.out.println(String.format("%s balanced? %s", w.getName(), w.isBalanced()));
        System.out.println(String.format("Imbalance amount: %d", w.getImbalance()));
    }

    private Node find(String nodeName) {
        return this.root.find(nodeName);
    }

    private void infix() {
        System.out.println("(" + this.root.infix() + ")");
    }

    private void print() {
        this.root.print(0);
    }

    private void height(String nodeName, Node w) {
        w.height(nodeName, 0);
    }

    /**
     * The main user input loop for processing commands.
     */
    public void mainLoop() {
        Scanner in = new Scanner(System.in);
        boolean done = false;
        while (!done) {
            System.out.print(PROMPT);
            String line = in.nextLine();
            if (line.equals("")) continue;
            String[] fields = line.split("\\s+");
            switch (fields[0]) {
                case HELP: {
                    help();
                    break;
                }
                case LOAD: {
                    if (fields.length == 2) {
                        load(fields[1]);
                    } else {
                        System.out.println("Must specify file name to load");
                    }
                    break;
                }
                case FIND: {
                    System.out.print("Found: ");
                    System.out.println(find(fields[1]));
                    break;
                }
                case INFIX: {
                    infix();
                    break;
                }
                case PRINT: {
                    print();
                    break;
                }
                case HEIGHT: {
                    height(fields[1], find(fields[1]));
                    break;
                }
                case BALANCED: {
                    balanced(this.root);
                    break;
                }
                case WEIGHT: {
                    weight(find(fields[1]));
                    break;
                }
                case ROOT: {
                    root();
                    break;
                }
                case QUIT: {
                    done = true;
                    break;
                }
                default: System.out.println("Unrecognized command: " + fields[0]);
            }
        }
        in.close();  // <3 Jim
    }

    /**
     * Main method runs the program.
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            System.out.println("Usage: java Mobiles");
        } else {
            Mobiles mobiles = new Mobiles();
            mobiles.mainLoop();
        }
    }
}
