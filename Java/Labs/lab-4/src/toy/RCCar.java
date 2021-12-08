package toy;

/**
 * @class RCCar
 * Extends the abstract BatteryOperatedToy class, adds speed functionality
 * Uses the 6000000 product code
 */
public class RCCar extends BatteryOperatedToy {
    private final static int DEGRADATION_RATE = 15;
    private final static int BATTERIES_DEPLETION_RATE = 30;
    private static int PCODE = 6000000;

    private int speed;

    /**
     * Protected RCCar Constructor
     * @param name Name of the RCCar
     * @param msrp MSRP of the RCCar
     * @param type BatteryType enum
     * @param batteries Number of batteries
     * @param speed Speed of the RCCar
     */
    protected RCCar(String name, double msrp, BatteryType type, int batteries, int speed) {
        super(PCODE, name, msrp, type, batteries, DEGRADATION_RATE);
        PCODE++;

        this.speed = speed;
    }

    /**
     * @return Max Speed of the RCCar
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Checks to see if the batteries need to be replaced, adds the RCCar play text, depletes batteries
     */
    public void play() {
        checkBatteries();
        System.out.println(String.format("%s races in circles at %d mph!", this.getName(), this.speed));
        super.play();
        depleteBatteries(BATTERIES_DEPLETION_RATE);
    }

    @Override
    public String toString() {
        String superString = super.toString();
        return superString + String.format(", speed=%d]", this.speed);
    }
}
