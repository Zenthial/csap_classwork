package puzzles.tipover.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import puzzles.tipover.TipCoordinates;
import solver.Configuration;
import util.Grid;

/**
 * DESCRIPTION
 * @author Tom Schollenberger
 * November 2021
 */
public class TipOverConfig implements Configuration {
    private Grid<TipCoordinates> board;
    private TipCoordinates tipperPosition, goal;

    private int[] cursor, prevCursor;

    /**
     * The only public constructor, used to create the initial config
     * @param board The board to be used in the config
     * @param tipperPosition The position of the tipper
     * @param goal The goal to be reached
     */
    public TipOverConfig(Grid<TipCoordinates> board, TipCoordinates tipperPosition, TipCoordinates goal) {
        this.board = board;
        this.tipperPosition = tipperPosition;
        this.goal = goal;
        this.cursor = new int[] { tipperPosition.row(), tipperPosition.col() };
        this.prevCursor = null;
    }

    /**
     * Only called by the class
     * @param board The board to be used in the config
     * @param tipperPosition The position of the tipper
     * @param goal The goal to be reached
     * @param prevCursor The previous position the config was at
     */
    private TipOverConfig(Grid<TipCoordinates> board, TipCoordinates tipperPosition, TipCoordinates goal, int[] prevCursor) {
        this.board = board;
        this.tipperPosition = tipperPosition;
        this.goal = goal;
        this.cursor = new int[] { tipperPosition.row(), tipperPosition.col() };
        this.prevCursor = prevCursor;
    }

    /**
     * Returns the board
     * @return the board
     */
    public Grid<TipCoordinates> getBoard() {
        return this.board;
    }

    /**
     * Sets the cursor
     * @param cur Cursor position
     */
    public void setCursor(int[] cur) {
        this.cursor = cur;
    }

    /**
     * @return Gets the tipper position
     */
    public TipCoordinates getTipperPosition() {
        return tipperPosition;
    }

    /**
     * Sets the tipper position to the given position
     * @param pos int array of the position
     */
    public void updateTipperPosition(int[] pos) {
        TipCoordinates oldPos = new TipCoordinates(tipperPosition.row(), tipperPosition.col(), tipperPosition.getLetter());
        TipCoordinates newPos = new TipCoordinates(pos[0], pos[1], board.get(pos[0], pos[1]).getLetter());

        oldPos.setLetter(" " + oldPos.getLetter().charAt(1));
        board.set(oldPos, oldPos.row(), oldPos.col());

        newPos.setLetter("*" + newPos.getLetter().charAt(1));
        board.set(newPos, newPos.row(), newPos.col());

        tipperPosition = newPos;

        cursor = new int[] { pos[0], pos[1] };

        setCursor(pos);
    }

    /**
     * @return the goal
     */
    public TipCoordinates getGoal() {
        return goal;
    }

    /**
     * @return The height
     */
    public int getHeight() {
        return Integer.parseInt(""+ tipperPosition.getLetter().charAt(1));
    }

    /**
     * @return boolean if the tower can tip in the given direction
     */
    public boolean canTip(Character dir, int amount) {
        int[] startPos = new int[] { tipperPosition.row(), tipperPosition.col() };
        boolean bool = true;

        // start at one, so you take into account the starting point
        for (int i = 1; i < amount; i++) {
            var pos = getCoordsForTip(board, dir, startPos, 1);

            if (pos == null) {
                bool = false;
                break;
            }
            if (!(""+pos.getLetter().charAt(1)).equals("_")) {
                bool = false;
                break;
            }
        }

        return bool;
    }

    /**
     * Tips the tower
     */
    public static void tip(TipOverConfig config, Character dir, int amount, int[] start) {
        var tipBoard = config.getBoard();

        // start at one, so you take into account the starting point

        tipBoard.get(start[0], start[1]).setLetter(" _");
        for (int i = 1; i <= amount; i++) {
            var pos = getCoordsForTip(tipBoard, dir, start, 1);
            if (pos != null) {
                pos.setLetter(" 1");
                tipBoard.set(pos, pos.row(), pos.col());
                start = new int[] { pos.row(), pos.col() };
            } else {
                break;
            }
        }

        config.board = tipBoard;
    }

    /**
     * @return Gets the coordinates for a tip
     */
    public static TipCoordinates getCoordsForTip(Grid<TipCoordinates> tipBoard, Character dir, int[] start, int amount) {
        int sign = (dir.equals('s') || dir.equals('w')) ? 1: -1;

        int x = start[0];
        int y = start[1];

//        System.out.println("x y getCoordsForTip(" + x + ", " + y + ")");

        if (dir.equals('n') || dir.equals('s')) {
            x += (amount*sign);
        } else {
            y += (amount*sign);
        }

        if (!checkBounds(tipBoard, new int[] { x, y })) return null;

//        System.out.println("updated x y getCoordsForTip(" + x + ", " + y + ")");

        var coords = tipBoard.get(x, y);
        coords.setRow(x);
        coords.setCol(y);
        return coords;
    }

    /**
     * Checks if the given position is still in the board
     * @return boolean
     */
    public static boolean checkBounds(Grid<TipCoordinates> board, int[] pos) {
        if (pos[0] >= board.getNRows() || pos[0] < 0) return false;
        else return pos[1] < board.getNCols() && pos[1] >= 0;
    }

    @Override
    public boolean isSolution() {
        return this.tipperPosition.row() == goal.row() && this.tipperPosition.col() == goal.col();
    }

    @Override
    public List<Configuration> getSuccessors() {
        int height = getHeight();

        int[] pos = new int[] { cursor[0], cursor[1] };

        List<Configuration> successors = new LinkedList<>();

        int possibilities = 4; // can only turn four ways
        // start at one to do even odd multiplication
        for (int i = 1; i <= possibilities; i++) {
            int sign = (int) Math.pow(-1, i);
            int[] newPos = pos.clone();

            if (i == 1 || i == 2) {
                newPos[0] += height*sign;
            } else {
                newPos[1] += height*sign;
            }


            if (prevCursor != null && newPos[0] == prevCursor[0] && newPos[1] == prevCursor[1]) continue;

            char dir = 'n';
            if (i == 2) {
                dir = 's';
            } else if (i == 3) {
                dir = 'e';
            } else if (i == 4) {
                dir = 'w';
            }

            if (height > 1 && checkBounds(board, newPos) && !canTip(dir, height)) {
                if (i == 1 || i == 2) {
                    newPos[0] += sign;
                } else {
                    newPos[1] += sign;
                }
            }

            if (!checkBounds(board, newPos)) continue;

            TipCoordinates newCoords = board.get(newPos[0], newPos[1]);
            String num = (""+newCoords.getLetter().charAt(1));
            TipCoordinates oldCoords = tipperPosition;
            Grid<TipCoordinates> newBoard = new Grid<>(board);

            TipOverConfig newConfig = new TipOverConfig(newBoard, oldCoords, goal, cursor);

            if (height > 1) {
                tip(newConfig, dir, height, pos);
            }

            newConfig.updateTipperPosition(newPos);

            if (newConfig.isSolution()) {
                successors = new LinkedList<>();
                successors.add(newConfig);
            }

//            System.out.println("current pos: (" + pos[0] + ", " + pos[1] + ") new pos: (" + newPos[0] + ", " + newPos[1] + ")");

            if (num.equals("_")) {
                continue;
            }
            successors.add(newConfig);
        }

        return successors;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board.getBoard()) + tipperPosition.hashCode() + goal.hashCode() + cursor[0] + cursor[1];
    }

    @Override
    public String toString() {
        return board.toString();
    }
}
