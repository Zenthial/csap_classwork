import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * A class to represent a single configuration in the Nurikabe puzzle.
 *
 * @author RIT CS
 * @author Thomas Schollenberger
 */
public class NurikabeConfig implements Configuration {
    protected ArrayList<ArrayList<String>> board;
    protected int[] size;
    protected int cursorX;
    protected int cursorY;
    protected String placedPoint;
    protected ArrayList<Coordinate> numberedTiles;
    private int numSeaTiles = 0;
    private int totalSeaTiles = 0;
    private int numLandTiles = 0;

    /**
     * Construct the initial configuration from an input file whose contents
     * are, for example:<br>
     * <tt><br>
     * 3 3          # rows columns<br>
     * 1 . #        # row 1, .=empty, 1-9=numbered island, #=island, &#64;=sea<br>
     * &#64; . 3    # row 2<br>
     * 1 . .        # row 3<br>
     * </tt><br>
     *
     * @param filename the name of the file to read from
     * @throws FileNotFoundException if the file is not found
     */
    public NurikabeConfig(String filename) throws FileNotFoundException {
        board = new ArrayList<ArrayList<String>>();
        numberedTiles = new ArrayList<>();
        placedPoint = "";
        cursorX = 0;
        cursorY = 0;
        try (Scanner in = new Scanner(new File(filename))) {
            int[] coords = { 0, 0 };

            String line = in.nextLine();
            String[] lineNumbers = line.split(" ");
            int linesToRead = Integer.parseInt(lineNumbers[0]);
            int columnsToRead = Integer.parseInt(lineNumbers[1]);
            size = new int[] { linesToRead, columnsToRead };
            line = in.nextLine();
            for (int i = 0; i < linesToRead; i++) {
                ArrayList<String> lineList = new ArrayList<String>();
                String[] split = line.split(" ");
                for (String splitLine : split) {
                    lineList.add(coords[0], splitLine);
                    if (!splitLine.equals(".")) {
                        numberedTiles.add(new Coordinate(coords[0], coords[1], splitLine));
                        totalSeaTiles += Integer.parseInt(splitLine);
                    }
                    coords[0]++;
                }

                board.add(coords[1], lineList);
                coords[0] = 0;
                coords[1]++;
                line = in.nextLine();
            }
        }

        totalSeaTiles = (size[0] * size[1]) - totalSeaTiles;
    }

    /**
     * The copy constructor takes a config, other, and makes a full "deep" copy
     * of its instance data.
     *
     * @param other the config to copy
     */
    protected NurikabeConfig(NurikabeConfig other, String point) {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        for (ArrayList<String> innerList : other.board) {
            ArrayList<String> newList = new ArrayList<>();
            for (String str : innerList) {
                newList.add(new String(str));
            }
            list.add(newList);
        }
        board = list;
        size = other.size;
        cursorX = other.cursorX;
        cursorY = other.cursorY;
        numberedTiles = other.numberedTiles;
        numLandTiles = other.numLandTiles;
        numSeaTiles = other.numSeaTiles;
        totalSeaTiles = other.totalSeaTiles;
        placedPoint = point;
        boolean passed = incrementCursor();
        if (!passed && !board.get((size[1] - 1)).get((size[0] - 1)).equals("."))
            return;
        setPoint(point);
    }

    private boolean incrementCursor() {
        // System.out.println(String.format("X: %d, Y: %d, MaxX: %d, MaxY: %d", cursorX, cursorY, size[0], size[1]));
        // System.out.println(cursorX >= size[0]);
        if (cursorY >= size[1])
            return false;
        if (getPoint().equals(".")) return true;
        if (cursorX >= size[0]-1) {
            cursorX = 0;
            cursorY++;
        } else {
            cursorX++;
        }
        if (cursorY >= size[1]-1) {
            return false;
        }
        if (!getPoint().equals(".")) {
            return incrementCursor();
        }

        return true;
    }

    private void setPoint(String point) {
        // System.out.println("Setting point: " + point);
        board.get(cursorY).set(cursorX, point);

        if (point.equals("@")) {
            numSeaTiles++;
        } else if (point.equals("#")) {
            numLandTiles++;
        }
    }
    
    private String getPoint() {
        return board.get(cursorY).get(cursorX);
    }

    private boolean lastPoint() {
        boolean bool = (cursorX >= size[0] - 1 && cursorY >= size[1] - 1) && !getPoint().equals(".");
        return bool;
    }

    @Override
    public Collection<Configuration> getSuccessors() {
        if (lastPoint()) {
            return new LinkedList<>();
        }
        Configuration leftSuccessor = new NurikabeConfig(this, "#");
        Configuration rightSuccessor = new NurikabeConfig(this, "@");
        List<Configuration> list = new LinkedList<>();
        list.add(leftSuccessor);
        list.add(rightSuccessor);
        return list;
    }

    private int calculateDistance(Coordinate pointOne, Coordinate pointTwo) {
        // System.out.println(pointOne);
        // System.out.println(pointTwo);
        // System.out.println("X: " + Math.abs(pointOne.getX() - pointTwo.getX()));
        // System.out.println("Y: " + Math.abs(pointOne.getY() - pointTwo.getY()));
        // System.out.println("Get Distance: " + (Math.abs(pointOne.getX() - pointTwo.getX()) + Math.abs(pointOne.getY() - pointTwo.getY())));
        return (Math.abs(pointOne.getX() - pointTwo.getX()) + Math.abs(pointOne.getY() - pointTwo.getY()) + 1);
    }

    // returns true if not a 2x2 sea
    private boolean checkSeas(Coordinate point) {
        if (!(cursorX > 0) || !(cursorY > 1)) {
            return true;
        }
        String north = this.board.get(cursorY - 1).get(cursorX);
        String northWest = this.board.get(cursorY - 1).get(cursorX - 1);
        String west = this.board.get(cursorY).get(cursorX - 1);
        int numSeas = 0;
        if (point.getValue().equals("@")) {numSeas++;}
        if (north.equals("@")) {numSeas++;}
        if (northWest.equals("@")) {numSeas++;}
        if (west.equals("@")) {
            numSeas++;
        }
        
        return !(numSeas == 4);
    }

    @Override
    public boolean isValid() {
        boolean bool = false;
        Coordinate currentPoint = new Coordinate(cursorX, cursorY, placedPoint);
        if (placedPoint.equals("#")) {
            for (Coordinate numberPoint : numberedTiles) {
                int dist = calculateDistance(currentPoint, numberPoint);
                int landVal = Integer.parseInt(numberPoint.getValue());
                if (dist < landVal) {
                    // System.out.println(dist + ", " + landVal);
                    bool = true;
                    break;
                }
            }
        } else if (placedPoint.equals("@")) {
            bool = checkSeas(currentPoint);
        } else {
            bool = false;
        }
        if (bool == false) {
            for (List<String> list : board) {
                System.out.println(list.toString());
            }
        }
        return bool;
    }

    @Override
    public boolean isGoal() {
        // System.out.println("---");
        // System.out.println("(" + (size[0] - 1) + ", " + (size[1] - 1) + ")" + " (" + cursorX + ", " + cursorY + ")");
        if (cursorX == (size[0] - 1) && cursorY == (size[1] - 1)) {
            if (board.get((size[1] - 1)).get((size[0] - 1)).equals(".")) {
                return false;
            }
            boolean bool = isValid();
            // System.out.println(bool);
            if (numSeaTiles != totalSeaTiles) {
                bool = false;
            }
            // System.out.println(bool);

            if (numLandTiles + numberedTiles.size() != ((size[0] * size[1]) - totalSeaTiles)) {
                bool = false;
            }
            // System.out.println(bool);
            // System.out.print(numLandTiles);
            // System.out.print(((size[0] * size[1]) - totalSeaTiles) + "\n");
            // for (List<String> list : board) {
            //     System.out.println(list.toString());
            // }
            return bool;
        } else {
            // System.out.println("Failed: " + cursorX + ", " + cursorY);
            return false;
        }
    }

    /**
     * Returns the string representation of the puzzle, e.g.: <br>
     * <tt><br>
     * 1 . #<br>
     * &#64; . 3<br>
     * 1 . .<br>
     * </tt><br>
     */
    @Override
    public String toString() {
        String returnString = "";
        for (ArrayList<String> list : board) {
            for (int i = 0; i < list.size(); i++) {
                returnString += String.format("%s ", list.get(i));
            }

            returnString += "\n";
        }

        return returnString;
    }

    @Override
    public boolean equals(Object o) {
        NurikabeConfig actualO = (NurikabeConfig) o;
        return this.board.equals(actualO.board);
    }

    @Override
    public int hashCode() {
        String toString = toString();
        int hash = 0;
        for (char chr : toString.toCharArray()) {
            hash += chr;
        }
        return cursorX + cursorY + hash;
    }
}
