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

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public int getImbalance() {
        return 0;
    }

    @Override
    public boolean isBalanced() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("Ball(name=%s, cord=%d, radius=%d, weight=%d)", this.name, this.cordLength, this.radius,
                this.weight);
    }
    
    @Override
    public Node find(String name) {
        if (this.name.equals(name)) {
            return this;
        } else {
            return null;
        }
    }

    @Override
    public String infix() {
        return " " + this.name + " ";
    }

    @Override
    public void print(int tabs) {
        String builder = "";
        for (int i = 0; i < tabs; i++) {
            builder += "\t";
        }
        builder += this.name;
        System.out.println(builder);
    }

    @Override
    public void height(String nodeName, int height) {
        if (nodeName.equals(this.name)) {
            System.out.println(String.format("%s height? %d", this.name, height + this.cordLength + (this.radius * 2)));
        }
    }

    @Override
    public int getHeight() {
        return this.cordLength + (this.radius * 2);
    }

    @Override
    public void width(String nodeName) {
        if (this.name.equals(nodeName)) {
            System.out.println(String.format("%s width? %d\n%s left width? %d\n%s right width? %d", this.name, this.radius*2, this.name, this.radius, this.name, this.radius));
        }
    }

    @Override
    public int getWidth() {
        return this.radius * 2;
    }
}
