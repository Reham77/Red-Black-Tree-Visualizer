
class RBTree {
    public static final int RED = 0, BLACK = 1, DOUBLEBLAK = 2;

    private Node root;

    Node insert(Node node, int x) {
        //Function to Insert a new node2 and returns the last node2 inserted

        //base case
        if (node.data <= x && node.right == null) {
            return node.right = new Node(x, node);
        }

        if (node.data > x && node.left == null) {
            return node.left = new Node(x, node);
        }

        //insertion
        if (node.data <= x)
            return insert(node.right, x);
        else
            return insert(node.left, x);
    }

    RBTree() {
        this.root = null;
    }

    RBTree(Node node) {
        this.root = node;
    }

    void print(Node node) {
        if (node == null) return;
        System.out.print("Data" + node.data + " " + node.color + " ");

        if (node.left != null)
            System.out.print("Left " + node.left.data + " ");
        if (node.right != null)
            System.out.print("Right " + node.right.data + " ");
        if (node.parent != null)
            System.out.print("Parent" + node.parent.data + " ");
        System.out.print("\n");


        print(node.left);
        print(node.right);

    }

    Node getRoot() {
        return root;
    }

    Node insert(int x) {
        if (root == null)
            return root = new Node(x);
        return insert(root, x);
    }

    boolean consecutiveRedNodes(Node node) {
        return node.parent != null && (node.parent.color == RED);
    }

    boolean isLeftChild(Node node) {
        if (node.parent == null)
            return false;
        return node.parent.left == node;
    }

    boolean uncleIsRed(Node node) {
        if (node.parent == null || node.parent.parent == null)
            return false;
        Node par = node.parent;
        if (isLeftChild(par))
            return (par.parent.right != null && (par.parent.right.color == RED) &&
                    par.parent.left.color == RED);
        else
            return par.parent.left != null && (par.parent.left.color == RED) &&
                    (par.parent.right.color == RED);
    }

    boolean isRoot(Node node) {
        return (node.parent == null);
    }

    void changeColorInsertion(Node node) {
        if (node.parent.parent.right != null)
            node.parent.parent.right.color = BLACK;

        if (node.parent.parent.left != null)
            node.parent.parent.left.color = BLACK;

        if (!isRoot(node.parent.parent)) node.parent.parent.color = RED;
    }


    void rightRotate(Node node) {
        Node temp = node.left;

        //connectTwoNode2s
        temp.parent = node.parent;

        if (isRoot(node))
            this.root = temp;
        else {
            if (isLeftChild(node)) node.parent.left = temp;
            else node.parent.right = temp;
        }

        node.parent = temp;

        if (temp.right != null) {
            node.left = temp.right;
            temp.right.parent = node;
        } else node.left = null;

        temp.right = node;
    }

    void LeftRotate(Node node) {
        Node temp = node.right;

        //conectTwoNodes
        temp.parent = node.parent;

        if (isRoot(node))
            this.root = temp;

        else {
            if (isLeftChild(node)) node.parent.left = temp;
            else node.parent.right = temp;
        }

        node.parent = temp;

        if (temp.left != null) {
            node.right = temp.left;
            temp.left.parent = node;
        } else node.right = null;

        temp.left = node;
    }

    //function to rotate during insertion (all 4 cases )
    public void Rotation(Node node) {

        Node newParent = node.parent;

        if (isLeftChild(node.parent)) {
            if (!isLeftChild(node)) {
                newParent = node;
                node = node.parent;
                LeftRotate(node);
            }
            rightRotate(node.parent.parent);
        }
        else {
            if (isLeftChild(node)) {
                newParent = node;
                node = node.parent;
                rightRotate(node);
            }
            LeftRotate(node.parent.parent);
        }

        newParent.color = BLACK;

        if (newParent.right != null) newParent.right.color = RED;
        if (newParent.left != null) newParent.left.color = RED;

        if (isRoot(newParent))
            root = newParent;

        root.color = BLACK;
    }

    //function to insert and fix nodes at insertion
    void finalInsertion(int x) {
        Node node = insert(x);
        if (isRoot(node)) {
            node.color = BLACK;
            return;
        }
        while (node != null && consecutiveRedNodes(node)) {
            if (uncleIsRed(node)) {
                changeColorInsertion(node);
            } else {
                Rotation(node);
            }
            if (!isRoot(node))
                node = node.parent.parent;
        }
    }

    //check if the given node is a leaf node
    boolean isLeaf(Node node) {
        return (node.left == null && node.right == null);
    }

    Node getSuccessor(Node node) {
        Node curr = node.right;
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr;
    }

    Node getPredecessor(Node node) {
        Node curr = node.left;
        while (curr.right != null) {
            curr = curr.right;
        }
        return curr;
    }

    Node Search(int x) {
        Node curr = root;
        while (curr.data != x) {
            if (curr.data <= x)
                curr = curr.right;
            else curr = curr.left;
        }
        return curr;
    }

    boolean IsBlack(Node node) {
        return (node == null || node.color == BLACK);
    }

    Node getSibling(Node node) {
        if (isRoot(node))
            return root;
        if (isLeftChild(node)) {
            return node.parent.right;
        }
        return node.parent.left;
    }

    Node swaping(Node node) {
        Node pre = null;
        while (!isLeaf(node)) {
            if (node.left != null && node.right != null) pre = getPredecessor(node); //has two childreen

            else if (node.left != null) {
                pre = node.left;
            } // has one left child

            else if (node.right != null) {
                pre = node.right;
            } //has one right child

            int x = node.data;
            node.data = pre.data;
            pre.data = x;


            node = pre;
        }
        return node;
    }

    void deleteNode(Node ToBeDeleted) {
        if (isLeftChild(ToBeDeleted))
            ToBeDeleted.parent.left = null;
        else ToBeDeleted.parent.right = null;
    }

    void FixDoubleBlack(Node ToBeDeleted) {

        if (ToBeDeleted == root || ToBeDeleted.color != DOUBLEBLAK) {
            root.color = BLACK;
            return;
        }

        Node sibling = getSibling(ToBeDeleted);
        if (IsBlack(sibling)) {

            if (IsBlack(sibling.left) && IsBlack(sibling.right)) {

                ToBeDeleted.color = BLACK;

                if (sibling != null)
                    sibling.color = RED;

                //if the parent is red then just swap colors
                if (!IsBlack(ToBeDeleted.parent)) {
                    ToBeDeleted.parent.color = BLACK;
                }
                //if parent is black then make it double black
                else {
                    ToBeDeleted.parent.color = DOUBLEBLAK;
                }
                FixDoubleBlack(ToBeDeleted.parent);
            } else {
                if (!isLeftChild(sibling)) {

                    //if far child is red (RR case )
                    if (!IsBlack(sibling.right)) {
                        sibling.right.color = sibling.color;
                        sibling.color = sibling.parent.color;
                        LeftRotate(ToBeDeleted.parent);
                    } else {
                        //if far child is black
                        //RL Case
                        sibling.left.color = sibling.parent.color;
                        rightRotate(sibling);
                        LeftRotate(ToBeDeleted.parent);
                    }
                } else {
                    // if sibling is on the left side
                    // if sibling left child is red (LL case) (far child)
                    if (!IsBlack(sibling.left)) {

                        sibling.left.color = sibling.color;
                        sibling.color = sibling.parent.color;
                        rightRotate(ToBeDeleted.parent);

                    } else {
                        //LR Case
                        sibling.right.color = sibling.parent.color;
                        LeftRotate(sibling);
                        rightRotate(ToBeDeleted.parent);
                    }
                }
            }
            ToBeDeleted.parent.color = BLACK;
        } else {

            //sibling is red
            ToBeDeleted.parent.color = RED;
            sibling.color = BLACK;

            if (isLeftChild(ToBeDeleted)) {
                LeftRotate(ToBeDeleted.parent);
            }
            else rightRotate(ToBeDeleted.parent);

            FixDoubleBlack(ToBeDeleted);
        }
    }

    void Delete(int x) {
        //search to get the node
        Node temp = Search(x);
        //then  keep swaping with the successor or predecessor
        Node ToBeDeleted = swaping(temp);

        if (isRoot(ToBeDeleted)) {
            root = null;
            return;
        }
        if (ToBeDeleted.color == RED) {
            deleteNode(ToBeDeleted);
        } else {
            ToBeDeleted.color = DOUBLEBLAK;
            FixDoubleBlack(ToBeDeleted);
            deleteNode(ToBeDeleted);
        }
    }

    void clear() {

        while (root != null) {
            Delete(root.data);
        }
    }
    void reset(Node node) {
        if (node == null) return;
        node.nodeID = -1;
        reset(node.left);
        reset(node.right);
    }
};