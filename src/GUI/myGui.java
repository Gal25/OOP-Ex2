package GUI;

import api.*;


import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import utils.StdDraw;

public class myGui extends JFrame {
    private static DirectedWeightedGraph g = new DWGraph();
    private static DirectedWeightedGraphAlgorithms g_algo = new DWGraph_Algo();
//    private static GeoLocation point = new GeoLocation_();
    public LinkedList<GeoLocation> points = new LinkedList<GeoLocation>();
    public double X_min = Integer.MAX_VALUE;
    public double X_max = Integer.MIN_VALUE;
    public double Y_min = Integer.MAX_VALUE;
    public double Y_max = Integer.MIN_VALUE;
    public double x;
    public double y;
    boolean mDraw_pivot = false;
    boolean mMoving_point = false;
    private int kRADIUS = 5;
    private final int mWin_h = 500;
    private final int mWin_w = 500;



//    public myGui(){
//        this.g =null;
//        initGUI();
//
//    }
    public myGui() {
        super();
        this.setBackground(Color.DARK_GRAY);
        this.points = new LinkedList<>();
        JFrame mainFrame = new JFrame();
        mainFrame.setSize(new Dimension(main.WIDTH, (main.HEIGHT * 3) / 4));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("Graph");
        mainFrame.setVisible(true);
        this.setSize(mWin_h, mWin_w);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (g != null) {
            X_min = Integer.MAX_VALUE;
            X_max = Integer.MIN_VALUE;
            Y_min = Integer.MAX_VALUE;
            Y_max = Integer.MIN_VALUE;
        }
        else {
            X_min =0;
            Y_min = 0;
            X_max = 800;
            Y_max = 600;
        }

        // rescale the coordinate system
        if (g != null) {
            Iterator<NodeData> nodes=g.nodeIter();
            for (Iterator<NodeData> it = nodes; it.hasNext(); ) {
                NodeData node = it.next();
                if(node.getLocation().x()>X_max) {
                    X_max=(node.getLocation().x());
                }
                if(node.getLocation().x()<X_min) {
                    X_min=(node.getLocation().x());
                }
                if(node.getLocation().y()>Y_max) {
                    Y_max=(node.getLocation().y());
                }
                if(node.getLocation().y()<Y_min) {
                    Y_min=(node.getLocation().y());
                }
                repaint();
            }
        }
    }

    private void initGUI() {
        this.setSize(mWin_h, mWin_w);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (g != null) {
            X_min = Integer.MAX_VALUE;
            X_max = Integer.MIN_VALUE;
            Y_min = Integer.MAX_VALUE;
            Y_max = Integer.MIN_VALUE;
        }
        else {
            X_min =0;
            Y_min = 0;
            X_max = 800;
            Y_max = 600;
        }

        // rescale the coordinate system
        if (g != null) {
            Iterator<NodeData> nodes=g.nodeIter();
            for (Iterator<NodeData> it = nodes; it.hasNext(); ) {
                NodeData node = it.next();
                if(node.getLocation().x()>X_max) {
                    X_max=(node.getLocation().x());
                }
                if(node.getLocation().x()<X_min) {
                    X_min=(node.getLocation().x());
                }
                if(node.getLocation().y()>Y_max) {
                    Y_max=(node.getLocation().y());
                }
                if(node.getLocation().y()<Y_min) {
                    Y_min=(node.getLocation().y());
                }
                repaint();
            }
        }
    }
    public void isConnected() {
        JFrame input = new JFrame();
        DirectedWeightedGraphAlgorithms gr = new DWGraph_Algo();
        gr.init(g);
        boolean ans = gr.isConnected();
        if (ans) {
            JOptionPane.showMessageDialog(input, "this graph is connected");
        }
        else {
            JOptionPane.showMessageDialog(input, "the graph is not connected");
        }

    }

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
        }
        catch (Exception e) {
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
                for (int i =0 ; i+1 < shortPath.size() ; i++) {
                    g.getEdge(shortPath.get(i).getKey(), shortPath.get(i+1).getKey()).setTag(100);
                    s.append(shortPath.get(i).getKey()).append("--> ");
                }
                s.append(shortPath.get(shortPath.size() - 1).getKey());
                repaint();
                JOptionPane.showMessageDialog(input, "the shortest path is: " +s);

            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(input, "the shortest path is: null" );
            }
        }
    }
    public void TSP() {
        JFrame input = new JFrame();
        DirectedWeightedGraphAlgorithms gr = new DWGraph_Algo();
        gr.init(g);
        List<NodeData> targets = new ArrayList<NodeData>();
        String src = "";
        StringBuilder s = new StringBuilder();
        do {
            src = JOptionPane.showInputDialog(
                    null, "get a key if you want to Exit get in Exit");

        }while(!src.equals("Exit"));
        ArrayList<NodeData> shortPath = new ArrayList<NodeData>();
        shortPath = (ArrayList<NodeData>) gr.tsp(targets);
        if (shortPath != null ) {
            for (int i =0 ; i+1 < shortPath.size() ; i++) {
                g.getEdge(shortPath.get(i).getKey(), shortPath.get(i+1).getKey()).setTag(100);
                s.append(shortPath.get(i).getKey()).append("--> ");
            }
            s.append(shortPath.get(shortPath.size() - 1).getKey());
            repaint();
            JOptionPane.showMessageDialog(input, "the shortest path is: " +s);
        }
        if(shortPath==null) {
            JOptionPane.showMessageDialog(input, "the shortest path is: null ");
        }
    }

    public void Savegraph() {
        DirectedWeightedGraphAlgorithms gg = new DWGraph_Algo();
        gg.init(g);
        //parent component of the dialog
        JFrame parentFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String file= fileToSave.getAbsolutePath();
            gg.save(file);
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());
        }

    }
    private static double[] leftTop(int r, GeoLocation p){
        double[] res = {p.x()-r, p.y()-r};
        return res;
    }


    public void paintComponent(Graphics h) {
        super.paint(h);
        int r = 10;
        GeoLocation prev = null;
        Iterator<NodeData> Nodes = g.nodeIter();
        for (Iterator<NodeData> it = Nodes; it.hasNext(); ) {
            NodeData node_data = it.next();
            GeoLocation p = node_data.getLocation();
            h.setColor(Color.BLACK);
            double[] posP = leftTop(r, p);
            h.fillOval((int) posP[0], (int) posP[1], r * 2, r * 2);
            h.setColor(Color.WHITE);

            Iterator<EdgeData> Edge = g.edgeIter(node_data.getKey());
            for (Iterator<EdgeData> iter = Edge; iter.hasNext(); ) {
                EdgeData edge_data = iter.next();
                if (edge_data.getTag() == 100) {
                    edge_data.setTag(0);
                    h.setColor(Color.red);
                } else {
                    h.setColor(Color.blue);

                }
                NodeData dest = myGui.g.getNode(edge_data.getDest());
                GeoLocation p2 = dest.getLocation();
                if (prev != null) {

                    Double dist = p.distance(prev);
                    String distStr = String.format("%.2f", dist);
                    h.setFont(new Font("name", Font.PLAIN, 15));
                    h.setColor(Color.ORANGE);
                    double x_place = ((((((p.x() + p2.x()) / 2) + p2.x()) / 2) + p2.x()) / 2);
                    double y_place = ((((((p.y() + p2.y()) / 2) + p2.y()) / 2) + p2.y()) / 2);
                    h.drawString(distStr, (int) x_place, (int) y_place);
                    int i = 0;
                    while (i < distStr.length()) {
                        if (distStr.charAt(i) == '.') {
                            distStr = distStr.substring(0, i + 2);
                        }
                        i++;
                    }

                    System.out.println(distStr);

                }
                prev = p;
            }

        }
    }

    public void Loadgraph() {
        DirectedWeightedGraphAlgorithms gg = new DWGraph_Algo();
        JFrame parentFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to load");
        int userSelection = fileChooser.showOpenDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            String file= fileToLoad.getAbsolutePath();
//            gg.init(file);
            repaint();
            System.out.println("Load from file: " + fileToLoad.getAbsolutePath());
        }
    }


    public static void main(String[] args) {
        new myGui();
    }


}
