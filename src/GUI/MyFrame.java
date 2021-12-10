package GUI;

import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class MyFrame extends JFrame implements ActionListener {


    private MenuItem isConnected;
    private MenuItem Center;
    private MenuItem shortestPathDist;
    private MenuItem shortestPath;
    private MenuItem tsp;
    private MenuItem LoadGraph;
    private MenuItem SaveGraph;
    private MenuItem remove_node;
    private MenuItem remove_edge;
    private MenuItem add_node;



    private static DirectedWeightedGraph g = new DWGraph();
    MyPanel myPanel= new MyPanel();

    public MyFrame(String json_file){
        initFrame();
        addMenu();
        initPanel();
        DirectedWeightedGraphAlgorithms gg= new DWGraph_Algo();
        gg.load(json_file);
        g = gg.getGraph();
        myPanel.reset();
        myPanel.setGraph(g);
        repaint();

    }

    public MyFrame(){
        initFrame();
        addMenu();
        initPanel();
    }

    private void initFrame(){
        this.setSize(new Dimension(900,500));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addMenu(){
        MenuBar menuBar = new MenuBar();
        Menu select = new Menu("Select file");
        Menu menu = new Menu("Functions of the algorithms");
        Menu graph = new Menu("Functions on the graph");


        menuBar.add(menu);
        menuBar.add(select);
        menuBar.add(graph);
        this.setMenuBar(menuBar);


        remove_node = new MenuItem("remove node");
        remove_node.addActionListener(this);
        remove_edge = new MenuItem("remove edge");
        remove_edge.addActionListener(this);
        add_node = new MenuItem("add node");
        add_node.addActionListener(this);


        LoadGraph = new MenuItem("Load Graph");
        LoadGraph.addActionListener(this);

        SaveGraph = new MenuItem("Save Graph");
        SaveGraph.addActionListener(this);

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

        select.add(LoadGraph);
        select.add(SaveGraph);
        graph.add(remove_edge);
        graph.add(remove_node);
        graph.add(add_node);
        menu.add(isConnected);
        menu.add(Center);
        menu.add(shortestPathDist);
        menu.add(shortestPath);
        menu.add(tsp);

//        select.add(Graph3);




    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == LoadGraph ){
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
                myPanel.reset();
                myPanel.setGraph(g);
                repaint();
                System.out.println("Load from file: " + fileToLoad.getAbsolutePath());
            }
        }

        if(e.getSource() == SaveGraph ){
            DirectedWeightedGraphAlgorithms gg= new DWGraph_Algo();
            JFrame parentFrame = new JFrame();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to load");
            int userSelection = fileChooser.showOpenDialog(parentFrame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String file= fileToSave.getAbsolutePath();
                gg.save(file);
                g = gg.getGraph();
                myPanel.setGraph(g);
                repaint();
                System.out.println("Load from file: " + fileToSave.getAbsolutePath());
            }
        }

//        if(e.getSource() == Graph3 ){
//            DirectedWeightedGraphAlgorithms gg= new DWGraph_Algo();
//            JFrame parentFrame = new JFrame();
//            JFileChooser fileChooser = new JFileChooser();
//            fileChooser.setDialogTitle("Specify a file to load");
//            int userSelection = fileChooser.showOpenDialog(parentFrame);
//
//            if (userSelection == JFileChooser.APPROVE_OPTION) {
//                File fileToLoad = fileChooser.getSelectedFile();
//                String file= fileToLoad.getAbsolutePath();
//                gg.load(file);
//                g = gg.getGraph();
//                myPanel.setGraph(g);
//                repaint();
//                System.out.println("Load from file: " + fileToLoad.getAbsolutePath());
//            }



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

        if(e.getSource() == remove_node){
            myPanel.RemoveNode();
        }

        if(e.getSource() == remove_edge){
            myPanel.remove_Edge();
        }

        if(e.getSource() == add_node){
            myPanel.add_node();
        }


    }

    private void initPanel(){
        this.add(myPanel);
    }


}
