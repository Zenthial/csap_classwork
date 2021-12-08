package puzzles.water;

public class Bucket {
    private int max;
    private int current;

    protected Bucket(int max) {
        this.max = max;
        this.current = 0;
    }

    public void empty() {
        current = 0;
    }

    public int transfer(int gallons) {
        if (isFull()) {
            return gallons;
        } else if (current + gallons <= max) {
            current = current + gallons;
            return 0;
        } else if (current + gallons > max) {
            int overflow = gallons - ((current + gallons) - max);
            int oldCurrent = current;
            current += overflow;
            return ((oldCurrent + gallons) - max);
        }


        return 0;
    }

    public void fill() {
        current = max;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int n) {
        current = n;
        if (n < 0) {
            throw new Error("cannot be less than 0");
        }

        if (n > max) {
            throw new Error("cannot be greater than max");
        }
    }
        
    public int getMax() {
        return max;
    }

    public boolean isFull() {
        return current == max;
    }

    public boolean isEmpty() {
        return current == 0;
    }

    @Override
    public String toString() {
        return String.valueOf(current);
    }

    @Override
    public boolean equals(Object o) {
        Bucket actual = (Bucket) o;
        return actual.getCurrent() == current;
    }
}
