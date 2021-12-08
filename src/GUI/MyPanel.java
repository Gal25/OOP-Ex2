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

class MyPanel extends JPanel implements MouseInputListener {

    public int brushSize = 10;
    private int mouseX = -1;
    private int mouseY = -1;
    private boolean mousePressed = false;
    private static DirectedWeightedGraph g = new DWGraph();
    private static DirectedWeightedGraphAlgorithms g_algo = new DWGraph_Algo();

    public MyPanel() {
        super();
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screensize.width;
        int height = screensize.height;
        this.setBackground(Color.white);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setPreferredSize(new Dimension(width, height));

    }


    public void setGraph(DirectedWeightedGraph g1) {
        this.g = g1;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.mousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.mousePressed = true;
        this.mouseX = e.getX();
        this.mouseY = e.getY();
//        this.repaint(this.mouseX, this.mouseY, this.brushSize, this.brushSize);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public static void remove_node(){
        JFrame input = new JFrame();
        String src = JOptionPane.showInputDialog(
                null, "what is the key of the node that you want to remove?");


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
        DirectedWeightedGraphAlgorithms gr = new DWGraph_Algo();
        gr.init(g);
        List<NodeData> cities = new ArrayList<NodeData>();
        String src = "";
        StringBuilder s = new StringBuilder();
        do {
            src = JOptionPane.showInputDialog(
                    null, "get a key if you want to Exit get in Exit");

        }
        while (!src.equals("Exit"));
        ArrayList<NodeData> shortPath = new ArrayList<NodeData>();
        shortPath = (ArrayList<NodeData>) gr.tsp(cities);
        if (shortPath != null) {
            for (int i = 0; i + 1 < shortPath.size(); i++) {
                g.getEdge(shortPath.get(i).getKey(), shortPath.get(i + 1).getKey()).setTag(100);
                s.append(shortPath.get(i).getKey()).append("--> ");
            }
            s.append(shortPath.get(shortPath.size() - 1).getKey());
            repaint();
            JOptionPane.showMessageDialog(input, "the shortest path is: " + s);
        }
        if (shortPath == null) {
            JOptionPane.showMessageDialog(input, "the shortest path is: null ");
        }
    }

//    public static void load(){
//
//    }


    /**
     * This function maintains the proportions between different page sizes.
     *
     * @param arr
     * @return
     */
    public void normal(double[] arr, double scale) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
//        double[] out = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            double v = arr[i];
            if (v < min) {
                min = v;
            }
            if (v > max) {
                max = v;
            }
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = scale * ((arr[i] - min) / (max - min));
        }
        // return out;
    }

//    private double[] getX(NodeData[] arr){
//        double x = 0;
//        double[] ans = new double[g.nodeSize()];
//        Iterator<NodeData> it = g.nodeIter();
//        while(it.hasNext()){
//            NodeData node = it.next();
//            node.getLocation().x();
//
//        }

    private void mapRange(HashMap<Integer, Double> input, double input_start, double input_end, double output_start, double output_end) {
        final double slope = ((output_end - output_start) / (input_end - input_start));
        input.replaceAll((k, v) -> output_start + slope * (input.get(k) - input_start));
    }

    private void mapRange(HashMap<Integer, Double> input, double output_start, double output_end) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
//        double[] out = new double[arr.length];
        for (Integer key : input.keySet()) {
            double v = input.get(key);
            if (v < min) {
                min = v;
            }
            if (v > max) {
                max = v;
            }
        }

        mapRange(input, min, max, output_start, output_end);
    }

    @Override
    protected void paintComponent(Graphics h) {
        HashMap<Integer, Double> getX = new HashMap<>(g.nodeSize());
        HashMap<Integer, Double> getY = new HashMap<>(g.nodeSize());
        Graphics2D h2 = (Graphics2D) h;
        super.paintComponent(h2);
        h2.setStroke(new BasicStroke(2));
        super.paintComponent(h2);
        int r = 7;
        GeoLocation prev = null;
        Iterator<NodeData> Nodes = g.nodeIter();
        while (Nodes.hasNext()) {
            NodeData node_data = Nodes.next();
            GeoLocation p = node_data.getLocation();
            System.out.println(p.x());
            getX.put(node_data.getKey(), p.x());
            getY.put(node_data.getKey(), p.y());
        }
        int marginX = 50;
        int marginY = 50;
        mapRange(getX, marginX, this.getWidth() - marginX);
        mapRange(getY, marginY, this.getHeight() - marginY);
        Iterator<NodeData> it = g.nodeIter();
        while (it.hasNext()) {
            NodeData node_data = it.next();
            int key = node_data.getKey();
            GeoLocation p = node_data.getLocation();

            double[] posP = leftTop(r, p);
            final int x = getX.get(key).intValue();
            final int y = getY.get(key).intValue();

//            System.out.println(getX[i] + ", " + getY[i]);
            h2.setColor(Color.BLACK);
            h2.fillOval(x, y, r * 2, r * 2);
            h2.setColor(Color.pink);
            h2.drawString("" + key, x, y);

        }
//        Iterator<EdgeData> Edge = g.edgeIter();
//        while (Edge.hasNext()) {
//            EdgeData edge_data = Edge.next();
//
//            GeoLocation s = g.getNode(edge_data.getSrc()).getLocation();
//            GeoLocation d = g.getNode(edge_data.getDest()).getLocation();
//            h2.setColor(Color.BLACK);
//            h2.drawLine((int) s.x(), (int) s.y(), (int) d.x(), (int) d.y());
//            if (edge_data.getTag() == 100) {
//                h2.setColor(Color.RED);
//                h2.fillOval(getX.get(edge_data.getDest()).intValue(), getY.get(edge_data.getDest()).intValue(), r*2, r*2);
//                edge_data.setTag(0);
//            } else {
//                h2.setColor(Color.BLUE);
//                h2.drawLine((int) s.x(), (int) s.y(), (int) d.x(), (int) d.y());
//                h2.setColor(Color.GREEN);
//                h2.fillOval(getX.get(edge_data.getDest()).intValue(), getY.get(edge_data.getDest()).intValue(),5, 5);
//
//
//            }
//                NodeData dest = g.getNode(edge_data.getDest());
//                GeoLocation p2 = dest.getLocation();
//                if (prev != null) {
//
//                    Double dist = p.distance(prev);
//                    String distStr = String.format("%.2f", dist);
//                    h2.setFont(new Font("name", Font.PLAIN, 15));
//                    h2.setColor(Color.ORANGE);
//                    double x_place = ((((((p.x() + p2.x()) / 2) + p2.x()) / 2) + p2.x()) / 2);
//                    double y_place = ((((((p.y() + p2.y()) / 2) + p2.y()) / 2) + p2.y()) / 2);
//                    h2.drawString(distStr, (int) x_place, (int) y_place);
//                    int j = 0;
//                    while (j < distStr.length()) {
//                        if (distStr.charAt(j) == '.') {
//                            distStr = distStr.substring(0, j + 2);
//                        }
//                        j++;
//                    }
//
//                    System.out.println(distStr);
//
//                }
//                prev = p;
//            }
//        }
//        }

        Iterator<EdgeData> eIter = g.edgeIter();
        while (eIter.hasNext()) {
            EdgeData e = eIter.next();
            int x1 = getX.get(e.getSrc()).intValue(), y1 = getY.get(e.getSrc()).intValue();
            int x2 = getX.get(e.getDest()).intValue(), y2 = getY.get(e.getDest()).intValue();
            if (e.getTag() == 100) {
                h2.setColor(Color.RED);
                h2.fillOval(getX.get(e.getDest()).intValue(), getY.get(e.getDest()).intValue(), r*2, r*2);
                e.setTag(0);

            } else {
                h2.setColor(Color.BLUE);
                h2.drawLine(x1 + r, y1 + r, x2 + r, y2 + r);
            }

            h2.setColor(Color.GREEN);
            h2.fillOval(getX.get(e.getDest()).intValue(), getY.get(e.getDest()).intValue(),5, 5);
        }
    }


    private static double[] leftTop(int r, GeoLocation p) {
        double[] res = {p.x() - r, p.y() - r};
        return res;
    }

}
