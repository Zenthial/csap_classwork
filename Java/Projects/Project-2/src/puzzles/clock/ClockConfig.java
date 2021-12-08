package puzzles.clock;

import java.util.ArrayList;

import solver.Configuration;

public class ClockConfig implements Configuration {
    private int hours, start, end;

    public ClockConfig(int hours, int start, int end) {
        this.hours = hours;
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getHours() {
        return hours;
    }

    @Override
    public boolean isSolution() {
        return getStart() == end;
    }

    @Override
    public ArrayList<Configuration> getSuccessors() {
        ClockConfig actualVal = this;
        ClockConfig leftNeighbor = actualVal.getStart() - 1 > 0 ? new ClockConfig(hours, actualVal.getStart() - 1, end)
                : new ClockConfig(hours, hours, end);
        ClockConfig rightNeighbor = actualVal.getStart() + 1 < hours + 1 ? new ClockConfig(hours, actualVal.getStart() + 1, end)
                : new ClockConfig(hours, 1, end);

        ArrayList<Configuration> returnArr = new ArrayList<Configuration>();
        returnArr.add(leftNeighbor);
        returnArr.add(rightNeighbor);
        
        return returnArr;
    }

    @Override
    public String toString() {
        return String.format("Hours: %d, Start: %d, End: %d", hours, start, end);
    }

    @Override
    public boolean equals(Object o) {
        ClockConfig c = (ClockConfig) o;
        if (c.getStart() == start) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return start * hours * end;
    }
}
