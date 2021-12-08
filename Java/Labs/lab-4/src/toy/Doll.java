package toy;

/**
 * @class Doll
 * Doll inherits from toy, adds hair and eye color functionality
 * Does not alter the play method at all
 */
public class Doll extends Toy {
    private static final int DEGRADATION_RATE = 17;
    private static int PCODE = 3000000;

    private String hairColor;
    private String eyeColor;

    /**
     * Protected Doll Constructor, called when a Doll is created
     * @param name Name of the doll
     * @param msrp MSRP of the doll
     * @param hairColor Hair Color String of the Doll
     * @param eyeColor Eye Color String of the Doll
     */
    protected Doll(String name, double msrp, String hairColor, String eyeColor) {
        super(PCODE, name, msrp, DEGRADATION_RATE);
        PCODE++;

        this.hairColor = hairColor;
        this.eyeColor = eyeColor;
    }

    /**
     * Alternate Protected Doll Constructor, called when an Action Figure is created
     * @param pcode The subclasses product code to pass to the super class
     * @param name The name of the subclass
     * @param msrp The msrp of the subclass
     * @param hairColor The hair color of the subclass
     * @param eyeColor The eye color of the subclass
     * @param rate The degradation rate of the subclass
     */
    protected Doll(int pcode, String name, double msrp, String hairColor, String eyeColor, int rate) {
        super(pcode, name, msrp, rate);

        this.hairColor = hairColor;
        this.eyeColor = eyeColor;
    }

    /**
     * @return The Doll's Hair Color
     */
    public String getHairColor() {
        return this.hairColor;
    }

    /**
     * @return The Doll's Eye Color
     */
    public String getEyeColor() {
        return this.eyeColor;
    }

    @Override
    public String toString() {
        String superString = super.toString();
        return superString + String.format(", hair color=%s, eye color=%s]", this.hairColor, this.eyeColor);
    }
}
