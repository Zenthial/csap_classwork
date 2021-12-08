package toy;

/**
 * @class ActionFigure
 * Extends the doll class, adds the boolean KungFuGrip argument
 */
public class ActionFigure extends Doll {
    private static final int DEGRADATION_RATE = 10;
    private static int PCODE = 5000000;

    private boolean hasGrip;
    
    /**
     * Protected Action Figure Constructor, calls the alternate Doll constructor
     * @param name The name of the action figure
     * @param msrp The MSRP of the action figure
     * @param hairColor The hair color of the action figure
     * @param eyeColor The eye color of the action figure
     * @param grip A boolean saying if the action figure has KungFu Grip
     */
    protected ActionFigure(String name, double msrp, String hairColor, String eyeColor, boolean grip) {
        super(PCODE, name, msrp, hairColor, eyeColor, DEGRADATION_RATE);
        PCODE++;

        this.hasGrip = grip;
    }

    /**
     * @return A boolean corresponding to whether the action figure has KungFu grip or not
     */
    public boolean hasKungFuGrip() {
        return this.hasGrip;
    }

    @Override
    public String toString() {
        String superString = super.toString();
        String[] split = superString.split("]");
        superString = split[0];
        return superString + String.format(", kung-fu grip=%s]", this.hasGrip);
    }
}
