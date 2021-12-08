package mobiles;

/**
 * A node in the mobile tree.
 *
 * @author RIT CS
 * @author Thomas Schollenberger
 */
public interface Node {
    /**
     * What's the Node's name?
     * @return Node's name
     */
    public String getName();

    /**
     * What's then nodes weight?
     * @return Node's weight
     */
    public int getWeight();
    
    /**
     * What's the node's imbalance?
     * Calculated by multiplying the length times the weight of each side, then seeing if they equal each other
     * @return An integer, with 0 being equal, negative being the left is less, positive being the right is less
     */
    public int getImbalance();

    /**
     * Is the node balanced?
     * Refer to getImbalance for information on how it works
     * @return True if balanced, false if imbalanced
     */
    public boolean isBalanced();

    /**
     * Performs the find command, by travesing the tree. Is recursive until it finds a node
     * @param name The name of the Node to look for
     * @return The Node found
     */
    public Node find(String name);

    /**
     * Performs the infix command, is recursive, should only be called on the root, with the root calling it's children
     * @return A String of the infix output
     */
    public String infix();

    /**
     * Performs the formatted print operation. It is recursive
     * @param tabs An integer relating to how many tabs should be added to the output. Should only ever be 0 when initially called
     */
    public void print(int tabs);

    /**
     * Searches for a Node, then prints the height of the given node
     * @param nodeName The name of the Node to look for
     */
    public void height(String nodeName);

    /**
     * A helper method to get children's heights
     * @return An integer of the Node's height
     */
    public int getHeight();

    /**
     * Searches for a Node, then prints the width of the given node
     * @param nodeName The name of the Node to look for
     */
    public void width(String nodeName);

    /**
     * A helper method to get children's widths
     * @return An integer of the Node's width
     */
    public int getWidth();

    /**
     * A helper method to get only the right width
     * @return An integer of the Node's right width
     */
    public int getRightWidth();

    /**
     * A helper method to get only the left width
     * @return An integer of the Node's left width
     */
    public int getLeftWidth();

}
