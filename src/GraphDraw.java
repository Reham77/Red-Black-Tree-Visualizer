import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

class GraphDraw extends JFrame {
    private final int SCREEN_WIDTH = 1910;
    private final int SCREEN_HEIGHT = 1020;

    int width;
    int height;

    public ArrayList<GUINode> GUINodes;
    public ArrayList<edge> edges;
    RBTree redBlackTree;

    public GraphDraw() {
        super();
        this.setTitle("Red Black Tree");
        this.setLocationRelativeTo(null);

        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.getContentPane().setBackground(Color.lightGray);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GUINodes = new ArrayList<GUINode>();
        edges = new ArrayList<edge>();
        width = 40;
        height = 40;
        setLayout(new BorderLayout());
        Font font = new Font("Helvetica", Font.BOLD, 30);

        redBlackTree = new RBTree();

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        JTextField insertText = new JTextField(10);
        insertText.setFont(font);
        bottomPanel.add(insertText);
        JButton insert = new JButton("Insert");

        insert.setBackground(new Color(117, 29, 120));
        insert.setForeground(Color.white);
        insert.setFont(font);
        this.getRootPane().setDefaultButton(insert);

        insert.addActionListener(itemEvent -> {
            Listenr(insertText, 0);
        });

        bottomPanel.add(insert);


        JTextField deleteText = new JTextField(10);
        deleteText.setFont(font);

        bottomPanel.add(deleteText);
        JButton delete = new JButton("Delete");
        delete.setBackground(new Color(111, 11, 14));

        delete.setFont(font);
        delete.setForeground(Color.white);
        bottomPanel.add(delete);

//        deleteText.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 2));
//        delete.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 2));


        delete.addActionListener(itemEvent -> {
            Listenr(deleteText, 1);
        });

        //clear button
        JButton clear = new JButton("Clear");
        clear.setBackground(new Color(135, 202, 187));
        clear.setFont(font);
        clear.setForeground(Color.white);//font color

        bottomPanel.add(clear);

        clear.addActionListener(itemEvent -> {
            redBlackTree.clear();
            this.edges.clear();
            this.GUINodes.clear();
            this.repaint();
        });

        add(bottomPanel, BorderLayout.SOUTH);
    }

    class GUINode {
        int x, y, data, color;

        public GUINode(int data, int color, int myX, int myY) {
            x = myX;
            y = myY;
            this.color = color;
            this.data = data;
        }
    }

    class edge {
        int i, j;

        public edge(int ii, int jj) {
            i = ii;
            j = jj;
        }
    }

    //add a node at pixel (x,y)
    public void addNode(int data, int color, int x, int y) {
        GUINodes.add(new GUINode(data, color, x, y));
        this.repaint();
    }

    //add edges
    public void addEdge(int i, int j) {
        edges.add(new edge(i, j));
        this.repaint();
    }

    @Override
    public void paint(Graphics g) { // draw the nodes and edges
        super.paint(g);

        Font font = new Font("Helvetica", Font.BOLD, 18);
        g.setFont(font);


        FontMetrics f = g.getFontMetrics();
        int nodeHeight = Math.max(height, f.getHeight());

        for (edge e : edges) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(5));
            g2.draw(new Line2D.Float(GUINodes.get(e.i).x, GUINodes.get(e.i).y, GUINodes.get(e.j).x, GUINodes.get(e.j).y));

        }

        for (GUINode n : GUINodes) {
            String Data = String.valueOf(n.data);
            int nodeWidth = Math.max(width, f.stringWidth(Data) + width / 2);
            if (n.color == 0) g.setColor(Color.red);
            else g.setColor(Color.black);
            g.fillOval(n.x - nodeWidth / 2, n.y - nodeHeight / 2, nodeWidth, nodeHeight);
            g.setColor(Color.white);
            g.drawOval(n.x - nodeWidth / 2, n.y - nodeHeight / 2, nodeWidth, nodeHeight);


            g.drawString(Data, n.x - f.stringWidth(Data) / 2, n.y + f.getHeight() / 2);
        }
    }

    void Listenr(JTextField textField, int operation) {

        String input = textField.getText();
        textField.setText("");
        int x = Integer.parseInt(input);
        textField.setText("");

        this.edges.clear();
        this.GUINodes.clear();

        if (operation == 0) redBlackTree.finalInsertion(x);
        else redBlackTree.Delete(x);

        new Draw().bfs(redBlackTree.getRoot(), this);

        Node node = redBlackTree.getRoot();
        redBlackTree.reset(node);
    }


}