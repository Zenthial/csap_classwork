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

    public Rod(String name, int cordLength, int leftLength, int rightLength, Node left, Node right) {
        this.name = name;
        this.cordLength = cordLength;
        this.leftLength = leftLength;
        this.rightLength = rightLength;
        this.left = left;
        this.right = right;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getWeight() {
        return this.left.getWeight() + this.right.getWeight();
    }

    @Override
    public int getImbalance() {
        return (this.leftLength * this.left.getWeight()) - (this.rightLength * this.right.getWeight());
    }

    @Override
    public boolean isBalanced() {
        return (this.leftLength * this.left.getWeight()) == (this.rightLength * this.right.getWeight());
    }

    @Override
    public String toString() {
        return String.format("Rod(name=%s, cord=%d, leftArm=%d, leftChild=%s, rightArm=%d, rightChild=%s)", this.name,
                this.cordLength, this.leftLength, this.left.toString(), this.rightLength, this.right.toString());
    }
    
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
    
    @Override
    public void height(String nodeName, int height) {
        height += this.cordLength + this.leftLength + this.rightLength;
        if (nodeName.equals(this.name)) {
            System.out.println(String.format("%s height? %d", this.name, height));
        } else {
            if (this.left != null) {
                this.left.height(nodeName, height);
            }

            if (this.right != null) {
                this.right.height(nodeName, height);
            }
        }
    }

    @Override
    public int getHeight() {
        return this.cordLength + this.left.getHeight() + this.left.getHeight();
    }

    private int calculateWidth() {
        return calculateLeftWidth() + calculateRightWidth();
    }

    private int calculateLeftWidth() {
        return leftLength + left.getWeight();
    }

    private int calculateRightWidth() {
        return rightLength + right.getWeight();
    }

    @Override
    public void width(String name) {
        if (this.name.equals(name)) {
            System.out.println(String.format("%s width? %d]\n%s left width? %d\n%s right width %d", this.name, this.calculateWidth(),
                    this.name, this.calculateLeftWidth(), this.name, this.calculateRightWidth()));
        }
    }

    @Override
    public int getWidth() {
        return calculateWidth();
    }
}

