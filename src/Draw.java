import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

class Draw {
    private final int SCREEN_WIDTH = 1910;
    private final int SCREEN_HEIGHT = 1020;

    private int cnt = -1;

    private void setNodeID(Node node) {
        if (node.nodeID == -1) node.nodeID = ++cnt;
    }

    public void bfs(Node node, GraphDraw frame) {

        Queue<Node> queue = new LinkedList();
        queue.add(node);

        Map<Node, Boolean> Drawn = new HashMap(); //boolean to check if the node is already drawn or not

        int lvl = 100, prev = SCREEN_WIDTH / 3;
        int relative = SCREEN_WIDTH / 6; // screen width
        while (!queue.isEmpty()) {
            int sz = queue.size();
            while (sz > 0) {
                Node currNode = queue.poll();
                if (!Drawn.containsKey(currNode)) {
                    currNode.initx = prev;
                    frame.addNode(currNode.data, currNode.color, prev, lvl);
                    Drawn.put(currNode, true);
                }
                setNodeID(currNode);

                Node child = currNode.left;
                if (child != null) {
                    frame.addNode(child.data, child.color, child.parent.initx - relative, lvl + 100);
                    child.initx = child.parent.initx - relative;
                    Drawn.put(child, true);
                    setNodeID(child);
                    queue.add(child);
                    frame.addEdge(child.nodeID, child.parent.nodeID);
                }
                child = currNode.right;
                if (child != null) {
                    setNodeID(child);
                    frame.addNode(child.data, child.color, child.parent.initx + relative, lvl + 100);
                    child.initx = child.parent.initx + relative;
                    Drawn.put(child, true);
                    queue.add(child);
                    frame.addEdge(child.nodeID, child.parent.nodeID);
                }
                sz--;
            }
            relative /= 2;
            lvl += 100;
        }
    }
}