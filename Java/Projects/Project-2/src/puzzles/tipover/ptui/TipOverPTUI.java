package puzzles.tipover.ptui;

import puzzles.tipover.model.TipOverModel;

import java.util.Scanner;

/**
 * Handles the PTUI creation and rendering
 * @author YOUR NAME HERE
 * November 2021
 */
public class TipOverPTUI {
    private static TipOverModel model;

    /**
     * Main method to parse the inputs from the user
     * @param input raw String input
     * @return boolean - determines if we should end the program or not
     */
    private static boolean parseInput(String input) {
        String[] args = input.split(" ");
        switch (args[0]) {
            case "load" -> {
                return model.load(args[1]);
            }
            case "reload" -> {
                return model.reload();
            }
            case "move" -> {
                return model.move(args[1]);
            }
            case "show" -> {
                model.show();
            }
            case "hint" -> {
                return model.hint();
            }
            case "help" -> {
                System.out.println("load\nreload\nmove\nhint\nshow\nhelp\nquit");
            }
            case "quit" -> { return false; }
            default -> {
            }
        }

        return true;
    }

    /**
     * Main method that runs the program
     */
    public static void main( String[] args ) {
        model = new TipOverModel();

        if (args.length > 0) {
            model.load(args[0]);
        }

        if (!model.loaded()) {
            System.out.println("no config loaded, please supply a config first with load filePath");
        }

            Scanner scannerInput = new Scanner(System.in);
        boolean reading = true;
        while (reading) {
            System.out.println("Ready for a new command sir.");

            String input = scannerInput.nextLine();
            System.out.println("input is '" + input + "'");

            if (!input.isEmpty()) {
                reading = parseInput(input);
            }
        }

        scannerInput.close();
    }
}
