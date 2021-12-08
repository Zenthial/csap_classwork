public class Coordinate {
    private int x, y;
    private String value;

    public Coordinate(int x, int y, String value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("V: %s, C: (%d, %d)", value, x, y);
    }
}
