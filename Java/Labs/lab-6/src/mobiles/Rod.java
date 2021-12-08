package mobiles;

/**
 * Represent a rod in the mobile tree.
 *
 * @author RIT CS
 * @author YOUR NAME HERE
 */
public class Rod implements Node {
    private String name;
    private int cordLength, leftLength, rightLength;
    private Node left, right;

    public Rod(String name, int cordLength, int leftLength, int rightLength, Node left, Node right) throws MobileException {
        this.name = name;
        this.cordLength = cordLength;
        this.leftLength = leftLength;
        this.rightLength = rightLength;
        this.left = left;
        this.right = right;

        if (!this.isBalanced()) {
            throw new MobileException(String.format("%s unbalanced by %d", this.name, this.getImbalance()));
        }
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
        return this.left.getWeight() + this.right.getWeight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getImbalance() {
        return (this.leftLength * this.left.getWeight()) - (this.rightLength * this.right.getWeight());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBalanced() {
        return (this.leftLength * this.left.getWeight()) == (this.rightLength * this.right.getWeight());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Node find(String name) {
        if (this.name.equals(name)) {
            return this;
        } else {
            Node left = this.left.find(name);
            Node right = this.right.find(name);

            if (left != null) {
                return left;
            } else {
                return right;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String infix() {
        String builder = "";
        if (this.left != null) {
            builder += "(" + this.left.infix() + ")";
        }

        builder += " " + this.name + " ";

        if (this.right != null) {
            builder += "(" + this.right.infix() + ")";
        }

        return builder;
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
        if (this.left != null)
            this.left.print(tabs + 1);
        if (this.right != null)
            this.right.print(tabs + 1);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void height(String nodeName) {
        if (nodeName.equals(this.name)) {
            int height = this.cordLength + Math.max(left.getHeight(), right.getHeight());
            System.out.println(String.format("%s height? %d", this.name, height));
        } else {
            if (this.left != null) {
                this.left.height(nodeName);
            }

            if (this.right != null) {
                this.right.height(nodeName);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        int adder = Math.max(left.getHeight(), right.getHeight());
        return this.cordLength + adder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void width(String name) {
        if (this.name.equals(name)) {
            System.out.println(String.format("%s width? %d\n%s left width? %d\n%s right width? %d", this.name, this.getWidth(),
                    this.name, this.getLeftWidth(), this.name, this.getRightWidth()));
        } else {
            if (this.left != null) {
                this.left.width(name);
            }

            if (this.right != null) {
                this.right.width(name);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return getLeftWidth() + getRightWidth();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getLeftWidth() {
        int leftWidth = leftLength + left.getLeftWidth();
        int rightLeftWidth = right.getLeftWidth() - rightLength;

        return Math.max(leftWidth, rightLeftWidth);
    }

    /**
     * {@inheritDoc}
     */
    @Override    
    public int getRightWidth() {
        int rightWidth = rightLength + right.getRightWidth();
        int leftRightWidth = left.getRightWidth() - leftLength;

        return Math.max(rightWidth, leftRightWidth);
    }

    /**
    {@inheritDoc} */
    @Override
    public String toString() {
        return String.format("Rod(name=%s, cord=%d, leftArm=%d, leftChild=%s, rightArm=%d, rightChild=%s)", this.name,
                this.cordLength, this.leftLength, this.left.toString(), this.rightLength, this.right.toString());
    }
}

