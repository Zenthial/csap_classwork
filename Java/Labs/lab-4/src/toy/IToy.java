package toy;

/**
 * An interface that defines the code set of methods that all toys must
 * implement.
 */
public interface IToy {
    /**
     * Returns the toy's product code.
     *
     * @return The toy's product code.
     */
    int getProductCode();

    /**
     * Returns the toy's name.
     *
     * @return The name of the toy.
     */
    String getName();

    /**
     * Returns the toy's MSRP.
     *
     * @return The toy's MSRP.
     */
    double getMSRP();

    /**
     * The toy's resale value, which is affected by its current condition.
     * Only {@link Condition#MINT mint} condition toys are worth full price.
     *
     * @return The resale value of the toy, based on its MSRP and it's current
     * condition.
     */
    double getResaleValue();

    /**
     * Returns the current condition of the toy.
     *
     * @return The toy's current condition.
     */
    Condition getCondition();

    /**
     * Returns the toy's level of degradation.
     *
     * @return the toy's current level of degradation.
     */
    int getDegradationLevel();

    /**
     * Called whenever the toy is played with. This will have the affect of
     * degrading the toy's condition.
     */
    void play();
}
