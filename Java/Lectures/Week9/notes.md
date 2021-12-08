# Trees

Recursively, a tree is either:
    - Empty
    - A value with a bunch of child trees

In code, our defintion of tree is:
    - null
    - a value and, optionally, left and right trees

Leafs are trees with no child trees
Root is the top most tree
Siblings are trees with the same parent

Height: How far down the tree goes
If the tree is empty, the height is -1

Descendant: All trees beneath a given tree
"The transitive closure of 'child'"

Ancestor: Transitive closure of parent

Imbalance: The different of heights of two subtrees

Search orders:
    Depth First Orders:
        Post-order
            Start at the bottom of each tree and work up
        In-order
            Start at the bottom left, go to it's parent, do the right, then work your way up and go to the right
        Pre-order
            Start at the top and go down

    Breadth-first order
        Go wide, handle one node, then the other, then its two nodes, then the other's two nodes and so on