package GUI;

import api.*;
import org.w3c.dom.Node;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

class MyPanel extends JPanel  {

    public int brushSize = 10;
    private int mouseX = -1;
    private int mouseY = -1;
    private boolean mousePressed = false;
    private List<NodeData_> nodes;
    private NodeData_ waitingNode;
    String message;
    private static DirectedWeightedGraph g = new DWGraph();
    private static DirectedWeightedGraph g_paint = new DWGraph();
    private static DirectedWeightedGraphAlgorithms g_algo = new DWGraph_Algo();
    private int counter = 0;
    private int r = 7;
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
//        this.addMouseListener(this);
//        this.addMouseMotionListener(this);

    }

    private void setMinimum() {
        Iterator<NodeData> it = g.nodeIter();
        while (it.hasNext()) {
            NodeData node = it.next();
            if (node.getLocation().x() < minX) {
                minX = node.getLocation().x();
            }
            if (node.getLocation().y() < minY) {
                minY = node.getLocation().y();
            }
        }
    }

    private void setMaximum() {
        Iterator<NodeData> it = g.nodeIter();
        while (it.hasNext()) {
            NodeData node = it.next();
            if (node.getLocation().x() > maxX) {
                maxX = node.getLocation().x();
            }
            if (node.getLocation().y() > maxY) {
                maxY = node.getLocation().y();
            }
        }
    }

    //    private void setMinMax(){
//        Iterator<NodeData> it = g.nodeIter();
//        while (it.hasNext()) {
//            NodeData node = it.next();
//            GeoLocation p = node.getLocation();
//            if (p.x() < minX) {
//                minX = p.x();
//            }
//            if (p.y() < minY) {
//                minY = p.y();
//            }
//            if (p.x() > maxX) {
//                maxX = p.x();
//            }
//            if (p.y() > maxY) {
//                maxY = p.y();
//            }
//        }
//
//    }

    public void reset(){
        maxX = Double.MIN_VALUE;
        maxY = Double.MIN_VALUE;
        minX = Double.MAX_VALUE;
        minY = Double.MAX_VALUE;
        getX = new HashMap<>();
        getY = new HashMap<>();


    }


    private void setMinMax(){
        Iterator<NodeData> it = g.nodeIter();
        while (it.hasNext()) {
            NodeData node = it.next();
            double X = this.getX.get(node.getKey());
            double Y = this.getY.get(node.getKey());
            if (X < minX) {
                minX = X;
            }
            if (Y < minY) {
                minY = Y;
            }
            if (X > maxX) {
                maxX = X;
            }
            if (Y > maxY) {
                maxY = Y;
            }
        }
    }

    public void setGraph(DirectedWeightedGraph g1) {
        this.g = g1;

        for (Iterator<NodeData> it = g.nodeIter(); it.hasNext(); ) {
            NodeData node = it.next();
            GeoLocation p = node.getLocation();
            getX.put(node.getKey(), p.x());
            getY.put(node.getKey(), p.y());
        }
        this.setMinMax();
        this.mapRange(this.getX, this.minX, this.maxX, this.marginX, this.getWidth() - this.marginX);
        this.mapRange(this.getY, this.minY, this.maxY, this.marginY, this.getHeight() - this.marginY);
    }


    public void RemoveNode() {

        JFrame frame = new JFrame();
        String source = JOptionPane.showInputDialog(frame,"Node To Remove");
//        String source = "0";
        try {
            int src = Integer.parseInt(source);
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
            //g_algo.init(g);
            repaint();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add_node(){
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
        NodeData curr = new NodeData_(new GeoLocation_(Integer.parseInt(locationX),Integer.parseInt(locationY), Integer.parseInt(locationZ)), Integer.parseInt(node));
        g.addNode(curr);
        double X = curr.getLocation().x() -r;
        double Y = curr.getLocation().y() -r;
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
        while ((it.hasNext())){
            NodeData n = it.next();
            int k = n.getKey();
//            double dist = n.getLocation().distance(new GeoLocation_(this.getX.get(curr.getKey()), this.getY.get(curr.getKey()),0));
            double dist = Math.sqrt(Math.pow(this.getX.get(k) - this.getX.get(curKey),2) + Math.pow(this.getY.get(k) - this.getY.get(curKey),2));
            if(dist < minDist && n.getKey() != curr.getKey()){
                minDist = dist;
                cKey = n.getKey();
            }
        }
        g.connect(cKey, curr.getKey(), minDist);
        repaint();
    }




    private void removeEdgeWithNode(NodeData N) {
        Iterator<EdgeData> it = g.edgeIter(N.getKey());
        while(it.hasNext()){
            EdgeData edge = it.next();
            if(edge.getSrc() == N.getKey()){
                g.removeEdge(edge.getSrc(), edge.getDest());
            }
        }
    }


    public void remove_Edge(){
        JFrame frame = new JFrame();
        String source = JOptionPane.showInputDialog(frame,"Source Node");
        String dest = JOptionPane.showInputDialog(frame,"Destination Node");
//        String source = "0";
//        String dest = "16";

        try {
            int src = Integer.parseInt(source);
            int des = Integer.parseInt(dest);
            System.out.println(g.getEdge(src,des));
            g.removeEdge(src, des);
            System.out.println(g.getEdge(src,des));
//            g_algo.init(g);
            repaint();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void isConnected() {
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

    public static void Center() {
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

    public static void shortestPathDist() {
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
                shortPath = (ArrayList<NodeData>) gg.shortestPath(Integer.parseInt(src), Integer.parseInt(dest));
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

    public void tsp() {
        JFrame input = new JFrame();
        JOptionPane.showMessageDialog(input, "To get TSP enter all the nodes from start node to end node \n after you went to exit enter EXIT");
        List<NodeData> TSP = new LinkedList<NodeData>();
        String ans;
        do {
            ans = JOptionPane.showInputDialog(input, "Enter node or EXIT when it is the last node");
            if(ans.equalsIgnoreCase("exit")) {
                break;
            }
            try {
                TSP.add(g.getNode(Integer.parseInt(ans)));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } while (!ans.equalsIgnoreCase("exit"));
        DWGraph_Algo ga = new DWGraph_Algo();
        ga.init(g);
        List<NodeData> temp = ga.tsp(TSP);
        for (int j = 0;j < temp.size()-1; j++) {
            g.getEdge(temp.get(j).getKey(), temp.get(j+1).getKey()).setTag(100);
        }
        repaint();

    }


    private void mapRange(HashMap<Integer, Double> input, double input_start, double input_end, double output_start, double output_end) {
        final double slope = ((output_end - output_start) / (input_end - input_start));
        input.replaceAll((k, v) -> output_start + slope * (input.get(k) - input_start));
    }

    private void mapRange(HashMap<Integer, Double> input, double output_start, double output_end) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
//        double[] out = new double[arr.length];
        for (double v : input.values()) {
            if (v < min) {
                min = v;
            }
            if (v > max) {
                max = v;
            }
        }

        mapRange(input, min, max, output_start, output_end);
        //return new double[] {min, max};
    }

    @Override
    public void paint(Graphics h) {
//        RemoveNode();
        System.out.println("Repainted");
        Graphics2D h2 = (Graphics2D) h;
        super.paintComponent(h2);
        h2.setStroke(new BasicStroke(2));
        super.paintComponent(h2);



//        System.out.println(this.getWidth() + " ---- " + this.getHeight());
//        System.out.println(getX);
//        double[] rangeX = mapRange(getX, marginX, this.getWidth() - marginX);
//        double[] rangeY = mapRange(getY, marginY, this.getHeight() - marginY);
//        mapRange(getX, marginX, this.getWidth() - marginX);
//        mapRange(getY, marginY, this.getHeight() - marginY);

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
            System.out.println(1);
            int x1 = getX.get(e.getSrc()).intValue(), y1 = getY.get(e.getSrc()).intValue();
            int x2 = getX.get(e.getDest()).intValue(), y2 = getY.get(e.getDest()).intValue();
            if (e.getTag() == 100) {
                h2.setColor(Color.RED);
                h2.fillOval(getX.get(e.getDest()).intValue(), getY.get(e.getDest()).intValue(), r * 2, r * 2);
                e.setTag(0);

            } else {
                h2.setColor(Color.BLUE);

            }
            h2.drawLine(x1 + r, y1 + r, x2 + r, y2 + r);
            h2.setColor(Color.GREEN);
            h2.fillOval(getX.get(e.getDest()).intValue(), getY.get(e.getDest()).intValue(), 5, 5);

        }

    }


}
