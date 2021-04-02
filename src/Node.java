public class Node {
    public static final int RED = 0, BLACK = 1, DOUBLEBLAK = 2;

    Node left, right, parent;
    int data;
    int color;
    int initx = 0, nodeID = -1;


    Node() {
        left = right = parent = null;
        this.color = RED;

    }

    Node(int data) {
        left = right = parent = null;
        this.data = data;
        this.color = RED;
    }

    Node(int data, Node parent) {
        left = right = null;
        this.parent = parent;
        this.data = data;
        this.color = RED;
    }

}
