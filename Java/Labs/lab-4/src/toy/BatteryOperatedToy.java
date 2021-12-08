package toy;

/**
 * @class BatteryOperatedToy
 * An abstract only class from which all battery toys inherent from
 */
public abstract class BatteryOperatedToy extends Toy {
    private final static int FULLY_CHARGED = 100;
    private final static int DEPLETED = 0;

    private int batteryLevel = FULLY_CHARGED;
    private BatteryType batteryType;
    private int numBatteries;
    
    protected BatteryOperatedToy(int pcode, String name, double msrp, BatteryType type, int batteries,
            int degradationRate) {
        super(pcode, name, msrp, degradationRate);

        this.batteryType = type;
        this.numBatteries = batteries;
    }

    /**
     * @return The toy's battery enum type
     */
    public BatteryType getBatteryType() {
        return this.batteryType;
    }

    /**
     * @return The number of batteries
     */
    public int getNumberOfBatteries() {
        return this.numBatteries;
    }

    /**
     * @return The battery level, with 100 being the highest
     */
    public int getBatteryLevel() {
        return this.batteryLevel;
    }

    /**
     * Protected depletion method, to be called by each subclass
     * @param depletionRate How much the battery should be decremented by
     */
    protected void depleteBatteries(int depletionRate) {
        this.batteryLevel -= depletionRate;
        if (this.batteryLevel < 0) {
            this.batteryLevel = 0;
        }
    }

    /**
     * Checks if the battery level is depleted and replaces the battery if they are depleted
     */
    protected void checkBatteries() {
        if (this.batteryLevel <= DEPLETED) {
            System.out.println("The batteries are dead! Let's replace them...");
            this.batteryLevel = FULLY_CHARGED;
        }
    }

    /**
     * Only exists so the subclass can call the supersuperclass.play method
     */
    public void play() {
        super.play();
    }

    @Override
    public String toString() {
        String superString = super.toString();
        String batteryFormat = batteryLevel + "%";
        return superString + String.format(", battery type=%s, number of batteries=%d, battery level=%s", this.batteryType.toString(),
                this.numBatteries, batteryFormat);
    }
}
