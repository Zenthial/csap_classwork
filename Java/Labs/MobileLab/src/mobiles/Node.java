package mobiles;

/**
 * A node in the mobile tree.
 *
 * @author RIT CS
 * @author Thomas Schollenberger
 */
public interface Node {
    public String getName();

    public int getWeight();
    
    public int getImbalance();

    public boolean isBalanced();

    public Node find(String name);

    public String infix();

    public void print(int tabs);

    public void height(String nodeName, int height);

    public int getHeight();

    public void width(String nodeName);

    public int getWidth();
}
