package puzzles.lunarlanding.ptui;

import puzzles.lunarlanding.model.LunarLandingConfig;
import puzzles.lunarlanding.model.LunarLandingModel;
import util.Coordinates;
import util.Observer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Runs the Lunar Landing puzzle text-based user interface.
 * @author Micah Coldiron
 * November 2021
 */
public class LunarLandingPTUI implements Observer<LunarLandingModel, Object> {
    private LunarLandingModel model;

    public LunarLandingPTUI(String[] args) {
        model = new LunarLandingModel();
        if (args.length == 0) {
            System.out.println("To start the game, load a file! (Use the command 'help' if you don't know how)");
        }
        else {
            model.load(args[0]);
            init();
        }
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        Coordinates currentCoords = null;
        for ( ; ;) {
            System.out.println("Awaiting orders");
            String[] line = in.nextLine().split("\\s+");
            if (line[0].equals("Quit") || line[0].equals("quit") || line[0].equals("q") || line[0].equals("Q")) {
                break;
            }
            else if (line[0].equals("Load") || line[0].equals("load") || line[0].equals("l") || line[0].equals("L")) {
                if (model.getcurrentConfig() == null) {
                    model.load(line[1]);
                    if (model.getcurrentConfig() != null) {
                        init();
                    }
                }
                else {
                    model.load(line[1]);
                }
            }
            else if (line[0].equals("Reload") || line[0].equals("reload") || line[0].equals("r") || line[0].equals("R")) {
                model.reload();
            }
            else if (line[0].equals("Choose") || line[0].equals("choose") || line[0].equals("c") || line[0].equals("C")) {
                Coordinates given = new Coordinates(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                if (!model.coordsCheck(given)) {
                    System.out.println("There was no figure at the given coordinates. Please submit coordinates that " +
                            "correspond to a figure/letter only.");
                }
                else {
                    currentCoords = given;
                }
            }
            else if (line[0].equals("Go") || line[0].equals("go") || line[0].equals("G") || line[0].equals("g")) {
                if (currentCoords != null) {
                    model.movePiece(currentCoords, line[1]);
                }
                else {
                    System.out.println("Please use the 'choose' command to select a piece first.");
                }
            }
            else if (line[0].equals("Hint") || line[0].equals("hint") || line[0].equals("hi") || line[0].equals("Hi")) {
                model.getHint();
            }
            else if (line[0].equals("Show") || line[0].equals("show") || line[0].equals("s") || line[0].equals("S")) {
                update(this.model, null);
            }
            else if (line[0].equals("Help") || line[0].equals("help") || line[0].equals("h") || line[0].equals("H")) {
                System.out.println("Incoming Transmission...");
                System.out.println("Welcome to Lunar Landing, Cadet! This briefing is for your eyes only.\n" +
                        "Mission Control has programmed your console to recognize only the following commands:");
                System.out.println("(Note: Some commands don't need extra arguments. For those commands, anything after\n" +
                        "the keyword will be ignored.)");
                System.out.println("Quit/quit/q/Q: This command shuts down the program.");
                System.out.println("Load/load/l/L {filename}: This command loads up the file with the given name.\n" +
                        "Remember to include the file path and not the curly brackets!");
                System.out.println("Reload/reload/r/R: This command loads the last successfully loaded file.");
                System.out.println("Choose/choose/c/C {row} {col}: This command choose a figure to move via coordinates.\n" +
                        "Remember that only letters can be moved, but if you forget your console will remind you.");
                System.out.println("""
                        Go/go/g/G {direction}: This command choose a direction in which to move the figure you selected.
                        This command can only be run after selecting a figure via the 'choose' command.
                        Again, if you forget your console will remind you.""");
                System.out.println("""
                        Hint/hint/h/H: If your stuck, run this command to have your console make a move for you.
                        If you use this command simply because you're lazy, congratulations! You just cheated at
                        a simple children's game! Good for you.""");
                System.out.println("Show/show/s/S: If you need to see the board again because you can't scroll up for\n" +
                        "some reason, use this command and your console will display it again for you.");
                System.out.println("Help/help/h/H: Use this command to bring up this briefing again, " +
                        "should you ever need it.");
                System.out.println("""
                        That should about cover everyth...what do you mean I had one job, Mission Control?...
                        oh yeah...sorry I will tell them right away. Sorry about that, Cadet. As Mission Control so
                        kindly reminded me, your mission (which you've accepted by having access to this program) is
                        to help our brave explorer ('E') get to the lander ('!'). He can only do this by running until
                        he hits a robot that stops him on the lander. The robots can only move by accelerating in the
                        chose direction until they hit another robot. I don't know why NASA designed them that way, just
                        go with it. That should finally be everything. Good luck, and have fun!""");
                System.out.println("Transmission Complete");
            }
            else {
                System.out.println("Command not recognized. Please try again.");
            }
        }
    }

    public void init() {
        this.model.addObserver(this);
        update(this.model, null);
    }

    public void update(LunarLandingModel o, Object arg) {
        if (arg == null) {
            System.out.println(model.getcurrentConfig());
            if (model.getcurrentConfig().isSolution()) {
                System.out.println("Mission Complete");
                System.out.println("Incoming Transmission...");
                System.out.println("Congratulations Cadet, you saved our brave explorer! Even better, you didn't have to" +
                        " cheat! I always knew you could do it, and definitely not because I just won a bet I promise!");
                System.out.println("Transmission Complete");
            }
        }
        else if (arg.equals("illegal move")) {
            System.out.println("The requested move is not a legal move. Please pick a new move.");
        }
        else if (arg.equals("cheater")) {
            System.out.println("Mission Complete");
            System.out.println("Incoming Transmission...");
            System.out.println("""
                    Cheater. You didn't save the day, your console did! Makes me wonder why Mission
                    Control even bothered to hire you in the first place! Oh well. If you want to do it legit and
                    prove that we shouldn't fire you, just use the reload command. If you want to ignore me, and try to
                    find some other cheeky way to do it, fine be that way. See how much I care.""");
            System.out.println("Transmission Complete");
        }
        else {
            System.out.println("The current puzzle configuration has no solution. Please reload the file or load a new file.");
        }
    }



















    public static void main( String[] args ) {
        LunarLandingPTUI start = new LunarLandingPTUI(args);
        start.run();
    }
}
