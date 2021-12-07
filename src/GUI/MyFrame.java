package GUI;

import api.DWGraph;
import api.DWGraph_Algo;
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
//import MyPanel;

public class MyFrame extends JFrame implements ActionListener {


    private MenuItem isConnected;
    private MenuItem Center;
    private MenuItem shortestPathDist;
    private MenuItem shortestPath;
    private MenuItem tsp;
    private MenuItem Graph1;
    private MenuItem Graph2;
    private MenuItem Graph3;


    private static DirectedWeightedGraph g = new DWGraph();
    MyPanel myPanel= new MyPanel();



    public MyFrame(){
        initFrame();
        addMenu();
        initPanel();




    }

    private void initFrame(){
        this.setSize(500,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addMenu(){
        MenuBar menuBar = new MenuBar();
        Menu select = new Menu("Select file");
        Menu menu = new Menu("Functions");
        menuBar.add(menu);
        menuBar.add(select);
        this.setMenuBar(menuBar);

        Graph1 = new MenuItem("Graph1");
        Graph1.addActionListener(this);

        Graph2 = new MenuItem("Graph2");
        Graph2.addActionListener(this);

        Graph3 = new MenuItem("Graph3");
        Graph3.addActionListener(this);

        isConnected = new MenuItem("isConnected");
        isConnected.addActionListener(this);

        Center = new MenuItem("Center");
        Center.addActionListener(this);

        shortestPathDist = new MenuItem("shortestPathDist");
        shortestPathDist.addActionListener(this);

        shortestPath = new MenuItem("shortestPath");
        shortestPath.addActionListener(this);

        tsp = new MenuItem("tsp");
        tsp.addActionListener(this);

        menu.add(isConnected);
        menu.add(Center);
        menu.add(shortestPathDist);
        menu.add(shortestPath);
        menu.add(tsp);
        select.add(Graph1);
        select.add(Graph2);
        select.add(Graph3);



    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == Graph1 ){
            DirectedWeightedGraphAlgorithms gg= new DWGraph_Algo();
            JFrame parentFrame = new JFrame();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to load");
            int userSelection = fileChooser.showOpenDialog(parentFrame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = fileChooser.getSelectedFile();
                String file= fileToLoad.getAbsolutePath();

                gg.load(file);
                g = gg.getGraph();
                myPanel.setGraph(g);
                repaint();
                System.out.println("Load from file: " + fileToLoad.getAbsolutePath());
            }
        }

        if(e.getSource() == Graph2 ){
            DirectedWeightedGraphAlgorithms gg= new DWGraph_Algo();
            JFrame parentFrame = new JFrame();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to load");
            int userSelection = fileChooser.showOpenDialog(parentFrame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = fileChooser.getSelectedFile();
                String file= fileToLoad.getAbsolutePath();
                gg.load(file);
                g = gg.getGraph();
                myPanel.setGraph(g);
                repaint();
                System.out.println("Load from file: " + fileToLoad.getAbsolutePath());
            }
        }

        if(e.getSource() == Graph3 ){
            DirectedWeightedGraphAlgorithms gg= new DWGraph_Algo();
            JFrame parentFrame = new JFrame();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to load");
            int userSelection = fileChooser.showOpenDialog(parentFrame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = fileChooser.getSelectedFile();
                String file= fileToLoad.getAbsolutePath();
                gg.load(file);
                g = gg.getGraph();
                myPanel.setGraph(g);
                repaint();
                System.out.println("Load from file: " + fileToLoad.getAbsolutePath());
            }
        }


        if (e.getSource() == isConnected){
             myPanel.isConnected();
        }
        if (e.getSource() == Center){
            myPanel.Center();
        }
        if (e.getSource() == shortestPathDist){
            myPanel.shortestPathDist();
        }
        if (e.getSource() == shortestPath){
            myPanel.shortestPath();
        }
        if (e.getSource() == tsp){
            myPanel.tsp();
        }


    }

    private void initPanel(){
        this.add(myPanel);
    }

//    @Override
//    public void paintComponents(Graphics g) {
////        System.out.println(2);
//        super.paintComponents(g);
//    }

}
