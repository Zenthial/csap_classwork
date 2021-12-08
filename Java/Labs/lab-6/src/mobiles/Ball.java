package mobiles;

/**
 * Represents a ball in the mobile tree.
 *
 * @author RIT CS
 * @author Thomas Schollenberger
 */
public class Ball implements Node {
    private String name;
    private int cordLength, radius, weight;

    public Ball(String name, int cordLength, int radius, int weight) {
        this.name = name;
        this.cordLength = cordLength;
        this.radius = radius;
        this.weight = weight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWeight() {
        return this.weight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getImbalance() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBalanced() {
        return true;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Node find(String name) {
        if (this.name.equals(name)) {
            return this;
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String infix() {
        return " " + this.name + " ";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print(int tabs) {
        String builder = "";
        for (int i = 0; i < tabs; i++) {
            builder += "\t";
        }
        builder += this.name;
        System.out.println(builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void height(String nodeName) {
        if (nodeName.equals(this.name)) {
            System.out.println(String.format("%s height? %d", this.name, this.cordLength + (this.radius * 2)));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return this.cordLength + this.getWidth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void width(String nodeName) {
        if (this.name.equals(nodeName)) {
            System.out.println(String.format("%s width? %d\n%s left width? %d\n%s right width? %d", this.name, this.radius*2, this.name, this.radius, this.name, this.radius));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return this.getLeftWidth() + this.getRightWidth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLeftWidth() {
        return this.radius;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRightWidth() {
        return this.radius;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("Ball(name=%s, cord=%d, radius=%d, weight=%d)", this.name, this.cordLength, this.radius,
                this.weight);
    }
}
