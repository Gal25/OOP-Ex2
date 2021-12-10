package GUI;

import api.*;
import org.w3c.dom.Node;

import java.awt.*;

import java.util.*;
import java.util.List;

import javax.swing.*;


class MyPanel extends JPanel {


    private DirectedWeightedGraph g = new DWGraph();


    private final int r = 7;

    private int WIDTH = 0;
    private int HEIGHT = 0;

    private double maxX = Double.MIN_VALUE;
    private double maxY = Double.MIN_VALUE;
    private double minX = Double.MAX_VALUE;
    private double minY = Double.MAX_VALUE;

    private HashMap<Integer, Double> getX = new HashMap<>();
    private HashMap<Integer, Double> getY = new HashMap<>();

    private final int marginX = 50;
    private final int marginY = 50;


    public MyPanel() {
        super();
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screensize.width;
        int height = screensize.height;
        this.setBackground(Color.white);
        this.setPreferredSize(new Dimension(width, height));


    }

    public void reset() {
        maxX = Double.MIN_VALUE;
        maxY = Double.MIN_VALUE;
        minX = Double.MAX_VALUE;
        minY = Double.MAX_VALUE;
        getX = new HashMap<>();
        getY = new HashMap<>();


    }

    /**
     * find the minimum x , y and maximum x,y
     */
    private void setMinMax() {
        Iterator<NodeData> it = g.nodeIter();
        while (it.hasNext()) {
            NodeData node = it.next();
            double X = this.getX.get(node.getKey());
            double Y = this.getY.get(node.getKey());
            if (X < this.minX) {
                this.minX = X;
            }
            if (Y < this.minY) {
                this.minY = Y;
            }
            if (X > this.maxX) {
                this.maxX = X;
            }
            if (Y > this.maxY) {
                this.maxY = Y;
            }
        }
    }

    public void setGraph(DirectedWeightedGraph g1) {
        this.g = g1;
        Iterator<NodeData> it = g.nodeIter();
        while (it.hasNext()) {
            NodeData node = it.next();
            GeoLocation p = node.getLocation();
            getX.put(node.getKey(), p.x());
            getY.put(node.getKey(), p.y());
        }
    }


    /**
     * remove node from the graph
     */
    public void RemoveNode() {
        JFrame frame = new JFrame();
        String rem = JOptionPane.showInputDialog(frame, "Node To Remove");
        try {
            int src = Integer.parseInt(rem);
            NodeData n = g.removeNode(src);
            double X = getX.get(n.getKey());
            double Y = getY.get(n.getKey());
            this.getX.remove(n.getKey());
            this.getY.remove(n.getKey());
            if (X >= maxX || Y >= maxY || X <= minX || Y <= minY) {
                this.setMinMax();
                this.mapRange(this.getX, this.minX, this.maxX, this.marginX, this.getWidth() - this.marginX);
                this.mapRange(this.getY, this.minY, this.maxY, this.marginY, this.getHeight() - this.marginY);
            }
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * add a node to the graph
     */
    public void add_node() {
        JFrame input = new JFrame();
        StringBuilder s = new StringBuilder();
        String node = JOptionPane.showInputDialog(
                null, "enter the key of the node that you want to add");
        String locationX = JOptionPane.showInputDialog(
                null, "enter the x location of the node that you want to add: ");
        String locationY = JOptionPane.showInputDialog(
                null, "enter the y location of the node that you want to add: ");
        String locationZ = JOptionPane.showInputDialog(
                null, "enter the z location of the node that you want to add: ");
        NodeData curr = new NodeData_(new GeoLocation_(Integer.parseInt(locationX), Integer.parseInt(locationY), Integer.parseInt(locationZ)), Integer.parseInt(node));
        g.addNode(curr);
        double X = curr.getLocation().x() - r;
        double Y = curr.getLocation().y() - r;
        this.getX.put(curr.getKey(), X);
        this.getY.put(curr.getKey(), Y);
        if (X >= maxX || Y >= maxY || X <= minX || Y <= minY) {
            this.setMinMax();
            this.mapRange(this.getX, this.minX, this.maxX, this.marginX, this.getWidth() - this.marginX);
            this.mapRange(this.getY, this.minY, this.maxY, this.marginY, this.getHeight() - this.marginY);
        }
        double minDist = Double.MAX_VALUE;
        int cKey = Integer.MIN_VALUE;
        Iterator<NodeData> it = g.nodeIter();
        int curKey = curr.getKey();
        while ((it.hasNext())) {
            NodeData n = it.next();
            int k = n.getKey();
            double dist = Math.sqrt(Math.pow(this.getX.get(k) - this.getX.get(curKey), 2) + Math.pow(this.getY.get(k) - this.getY.get(curKey), 2));
            if (dist < minDist && n.getKey() != curr.getKey()) {
                minDist = dist;
                cKey = n.getKey();
            }
        }
        g.connect(cKey, curr.getKey(), minDist);
        repaint();
    }

    /**
     * remove edge from the graph
     */
    public void remove_Edge() {
        JFrame frame = new JFrame();
        String source = JOptionPane.showInputDialog(frame, "Source Node");
        String destination = JOptionPane.showInputDialog(frame, "Destination Node");

        try {
            int src = Integer.parseInt(source);
            int dest = Integer.parseInt(destination);
            System.out.println(g.getEdge(src, dest));
            g.removeEdge(src, dest);
            System.out.println(g.getEdge(src, dest));
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * check if the graph is connected
     */
    public void isConnected() {
        JFrame input = new JFrame();
        DirectedWeightedGraphAlgorithms gr = new DWGraph_Algo();
        gr.init(g);
        boolean ans = gr.isConnected();
        if (ans) {
            JOptionPane.showMessageDialog(input, "this graph is connected");
        } else {
            JOptionPane.showMessageDialog(input, "the graph is not connected");
        }

    }

    /**
     * find the center of the graph
     */
    public void Center() {
        JFrame input = new JFrame();
        DirectedWeightedGraphAlgorithms gr = new DWGraph_Algo();
        gr.init(g);
        NodeData ans = gr.center();
        if (ans != null) {
            JOptionPane.showMessageDialog(input, "the center of this graph is:" + ans);
        } else {
            JOptionPane.showMessageDialog(input, "the graph don't have a center");
        }
    }

    /**
     * fing the shortest path distance from the source to the destination
     */
    public void shortestPathDist() {
        JFrame input = new JFrame();
        String src = JOptionPane.showInputDialog(
                null, "what is the key for src?");
        String dest = JOptionPane.showInputDialog(
                null, "what is the key for dest?");
        try {
            DirectedWeightedGraphAlgorithms gg = new DWGraph_Algo();
            gg.init(g);
            double ans = gg.shortestPathDist(Integer.parseInt(src), Integer.parseInt(dest));
            String s = Double.toString(ans);
            JOptionPane.showMessageDialog(input, "the shortest distance is: " + s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * return the shortest path from the source to destination
     */

    public void shortestPath() {
        JFrame input = new JFrame();
        StringBuilder s = new StringBuilder();
        String src = JOptionPane.showInputDialog(
                null, "what is the key for src?");
        String dest = JOptionPane.showInputDialog(
                null, "what is the key for dest?");
        if (!src.equals(dest)) {
            try {
                DirectedWeightedGraphAlgorithms gg = new DWGraph_Algo();
                gg.init(g);
                ArrayList<NodeData> shortPath = new ArrayList<NodeData>();
                shortPath = (ArrayList<NodeData>)gg.shortestPath(Integer.parseInt(src), Integer.parseInt(dest));
                for (int i = 0; i + 1 < shortPath.size(); i++) {
                    g.getEdge(shortPath.get(i).getKey(), shortPath.get(i + 1).getKey()).setTag(100);
                    s.append(shortPath.get(i).getKey()).append("--> ");
                }
                s.append(shortPath.get(shortPath.size() - 1).getKey());
                repaint();
                JOptionPane.showMessageDialog(input, "the shortest path is: " + s);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(input, "the shortest path is: null");
            }
        }
    }

    /**
     * return the TSP
     */
    public void tsp() {
        JFrame input = new JFrame();
        JOptionPane.showMessageDialog(input, "To get TSP enter all the nodes from start node to end node \n after you went to exit enter EXIT");
        List<NodeData> TSP = new LinkedList<NodeData>();
        String ans;
        do {
            ans = JOptionPane.showInputDialog(input, "Enter node or EXIT when it is the last node");
            if (ans.equalsIgnoreCase("exit")) {
                break;
            }
            try {
                TSP.add(g.getNode(Integer.parseInt(ans)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (!ans.equalsIgnoreCase("exit"));
        DWGraph_Algo ga = new DWGraph_Algo();
        ga.init(g);
        List<NodeData> temp = ga.tsp(TSP);
        for (int j = 0; j < temp.size() - 1; j++) {
            g.getEdge(temp.get(j).getKey(), temp.get(j + 1).getKey()).setTag(100);
        }
        repaint();

    }


    private void mapRange(HashMap<Integer, Double> input, double input_start, double input_end, double output_start, double output_end) {
        final double slope = ((output_end - output_start) / (input_end - input_start));
        input.replaceAll((k, v) -> output_start + slope * (input.get(k) - input_start));
    }


    @Override
    public void paint(Graphics h) {
        Graphics2D h2 = (Graphics2D)h;
        super.paintComponent(h2);
        h2.setStroke(new BasicStroke(2));
        super.paintComponent(h2);

        if (this.WIDTH == 0 || this.HEIGHT == 0) {
            this.reProportions();
        }

        Iterator<NodeData> it = g.nodeIter();
        while (it.hasNext()) {
            NodeData node_data = it.next();
            int key = node_data.getKey();
            GeoLocation p = node_data.getLocation();

            final int x = getX.get(key).intValue();
            final int y = getY.get(key).intValue();


            h2.setColor(Color.BLACK);
            h2.fillOval(x, y, r * 2, r * 2);
            h2.setColor(Color.pink);
            h2.drawString("" + key, x, y);

        }
        Iterator<EdgeData> eIter = g.edgeIter();
        while (eIter.hasNext()) {
            EdgeData e = eIter.next();
            int x1 = getX.get(e.getSrc()).intValue(), y1 = getY.get(e.getSrc()).intValue();
            int x2 = getX.get(e.getDest()).intValue(), y2 = getY.get(e.getDest()).intValue();
            if (e.getTag() == 100) {
                h2.setColor(Color.RED);
                h2.drawLine(getX.get(e.getSrc()).intValue() + 4, getY.get(e.getSrc()).intValue() + 4 ,getX.get(e.getDest()).intValue() +4,getY.get(e.getDest()).intValue() +4);
                e.setTag(0);

            } else{
                h2.setColor(Color.BLUE);
                h2.drawLine(x1 + r, y1 + r, x2 + r, y2 + r);
            }
            h2.setColor(Color.GREEN);
            h2.fillOval(getX.get(e.getDest()).intValue(), getY.get(e.getDest()).intValue(), 5, 5);

        }

    }

    /**
     * do the proportions
     */
    public void reProportions() {
        this.reset();
        this.setGraph(this.g);
        this.setMinMax();
        mapRange(this.getX, this.minX, this.maxX, marginX, this.getWidth() - marginX);
        mapRange(this.getY, this.minY, this.maxY, marginY, this.getHeight() - marginY);
        this.WIDTH = this.getWidth();
        this.HEIGHT = this.getHeight();
    }

}
