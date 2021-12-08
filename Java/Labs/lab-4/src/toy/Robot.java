package toy;

/**
 * @class Robot
 * Extends the abstract BatteryOperatedToy class, adds sound functionality
 * Uses the 7000000 product code
 */
public class Robot extends BatteryOperatedToy {
    private final static int DEGRADATION_RATE = 20;
    private final static int BATTERIES_DEPLETION_RATE = 25;
    private static int PCODE = 7000000;

    private String sound;

    /**
     * Protected Robot Constructor
     * @param name Name of the Robot
     * @param msrp MSRP of the Robot
     * @param type BatteryType enum
     * @param batteries Number of batteries
     * @param sound The sound played whenever the robot is played with
     */
    protected Robot(String name, double msrp, BatteryType type, int batteries, String sound) {
        super(PCODE, name, msrp, type, batteries, DEGRADATION_RATE);
        PCODE++;

        this.sound = sound;
    }

    /**
     * @return The sound played by the robot
     */
    public String getSound() {
        return this.sound;
    }

    /**
     * Plays the sound the robot makes, depletes the battery
     */
    public void play() {
        checkBatteries();
        System.out.println(String.format("%s goes '%s'", this.getName(), this.sound));
        super.play();
        depleteBatteries(BATTERIES_DEPLETION_RATE);
    }

    @Override
    public String toString() {
        String superString = super.toString();
        return superString + String.format(", sound=%s]", this.sound);
    }
}
