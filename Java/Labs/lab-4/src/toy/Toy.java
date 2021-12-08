package toy;

/**
 * @class Toy
 * The highest level abstract class from which all toys inherent
 * 
 * @author Tom Schollenberger
 */
public abstract class Toy implements IToy {
    static final int MAX_DEGRADATION = 100;
    private String name;
    private int productCode;
    private double MSRP;
    private double resaleValue;
    private Condition condition;
    private int degradation;
    private int degradationRate;

    /**
     * Protected toy constructor
     * @param pcode Unique constructed toy level product code
     * @param name Name of the constructed toy
     * @param msrp MSRP of the constructed toy
     * @param degradationLevel The increment to be degraded by each time
     */
    protected Toy(int pcode, String name, double msrp, int degradationLevel) {
        this.productCode = pcode;
        this.name = name;
        this.MSRP = msrp;
        this.resaleValue = msrp;
        this.condition = Condition.MINT;
        this.degradation = 0;
        this.degradationRate = degradationLevel;
    }

    /**
     * @return product code
     */
    public int getProductCode() {
        return this.productCode;
    }

    /**
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return MSRP
     */
    public double getMSRP() {
        return this.MSRP;
    }

    /**
     * @return resale value
     */
    public double getResaleValue() {
        return this.resaleValue;
    }

    /**
     * @return condition
     */
    public Condition getCondition() {
        return this.condition;
    }

    /**
     * @return degradation
     */
    public int getDegradationLevel() {
        return this.degradation;
    }

    /**
     * Private method to increment the degradation level and update the resale value
     */
    private void degrade() {
        this.degradation += this.degradationRate;
        this.condition = Condition.get(this.degradation);
        this.resaleValue = this.MSRP * this.condition.getMultiplier();
        if (this.degradation > 100) {
            this.degradation = 100;
        }
    }

    /**
     * Public play method that all toys either call or extend
     */
    public void play() {
        this.degrade();
        String printFormat = String.format("After play, %s's condition is %s", this.getName(), this.getCondition());
        System.out.println(printFormat);
    }
    
    /**
     * Writes the class to a string
     */
    @Override
    public String toString() {
        String degFormat = this.degradation + "%";
        return String.format("%s [product code=%d, MSRP=%.2f, degradation level=%s, condition=%s, resale value=%.2f", this.name,
                this.productCode, this.MSRP, degFormat, this.condition.toString(), this.resaleValue);
    }
    
}
