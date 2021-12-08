package toy;

/**
 * Used to represent the possible condition of a toy.
 * A toy's condition is determined by its level of degradation.
 * <p>
 * Every possible condition has associated a range of degradation level [min, max). For example,
 * the range for the {@link Condition#MINT mint} condition is between 0 and 10 (exclusive). A toy
 * with 5% level of degradation is considered to be in {@link Condition#MINT mint} condition.
 */
public enum Condition {
    MINT(1.0, 0, 10),
    NEAR_MINT(0.9, 10, 20),
    VERY_GOOD(0.75, 20, 30),
    GOOD(0.7, 30, 50),
    FAIR(0.25, 50, 75),
    POOR(0.1, 75, 90),
    BROKEN(0.0, 90, 100);

    /**
     * Used to determine the real value of a toy; its base price multiplied by
     * its condition.
     */
    private final double multiplier;
    /**
     * Minimum level of degradation (inclusive)
     */
    private final int minDegradation;

    /**
     * Maximum level of degradation (exclusive)
     */
    private final int maxDegradation;

    /**
     * Creates a new condition with the specified multiplier
     * and a degradation range.
     *
     * @param multiplier     The amount by which the base price of a toy should be
     *                       multiplied to determine its value.
     * @param minDegradation The condition's minimum level of degradation (inclusive)
     * @param maxDegradation The condition's maximum level of degradation (exclusive)
     */
    Condition(double multiplier, int minDegradation, int maxDegradation) {
        this.multiplier = multiplier;
        this.minDegradation = minDegradation;
        this.maxDegradation = maxDegradation;
    }

    /**
     * Returns the condition whose degradation range contains the given degradation level. If not found, it will return
     * {@link Condition#BROKEN broken}.
     *
     * @param degradationLevel the toy's degradation level
     * @return the toy's condition
     */
    public static Condition get(int degradationLevel) {
        for (Condition condition : values()) {
            if (condition.minDegradation <= degradationLevel && degradationLevel < condition.maxDegradation) {
                return condition;
            }
        }
        return Condition.BROKEN;
    }

    /**
     * A toy's condition affects its resale value. The multiplier is used to
     * determine by how much the value is affected.
     *
     * @return The multiplier for this condition.
     */
    public double getMultiplier() {
        return multiplier;
    }
}
