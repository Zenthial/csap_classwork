package toy;

/**
 * @class Scooter
 * Scooter extends Toy and implements basic speed functionality into it's play method
 * Product code starts at 9000000
 */
public class Scooter extends Toy {
    private static final int DEGRADATION_RATE = 5;
    private static int PCODE = 9000000;

    private String color;
    private int wheels;
    // All scooters are going 0 MPH when first bought
    private int odometer = 0;

    /**
     * Protected Scooter Constructor, creates a new scooter and increments the product code
     * @param name Name of the scooter
     * @param msrp MSRP of the scooter
     * @param color Color of the scooter
     * @param wheels Number of wheels the scooter has
     */
    protected Scooter(String name, double msrp, String color, int wheels) {
        super(PCODE, name, msrp, DEGRADATION_RATE);
        PCODE++;

        this.color = color;
        this.wheels = wheels;
    }

    /**
     * @return Scooter Color
     */
    public String getColor() {
        return this.color;
    }

    /**
     * @return Odometer speed
     */
    public int getOdometer() {
        return this.odometer;
    }

    /**
     * @return Number of wheels
     */
    public int getWheels() {
        return this.wheels;
    }

    public void play() {
        super.play();
        this.odometer += 2;
    }

    @Override
    public String toString() {
        String superString = super.toString();
        return superString
                + String.format(", color=%s, wheels=%d, odometer=%d]", this.color, this.wheels, this.odometer);
    }
}
