package tests;

import api.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {

    private static DirectedWeightedGraph g = new DWGraph();
    private static DirectedWeightedGraphAlgorithms g_algo = new DWGraph_Algo();


    public DWGraph_AlgoTest() {

    private static DirectedWeightedGraph g = new DWGraph();
    private static DirectedWeightedGraphAlgorithms g_algo = new DWGraph_Algo();


    public DWGraph_AlgoTest() {
        for (int i = 0; i < 5; i++) {
            GeoLocation p = new GeoLocation_(i, i+1, i+2);
            NodeData n = new NodeData_(i, i, p);
            g.addNode(n);
        }

        g.connect(0, 1, 0.5);
        g.connect(0, 3, 9);
        g.connect(1, 0, 3);
        g.connect(1, 2, 2);
        g.connect(1, 3, 4);
        g.connect(2, 0, 6);
        g.connect(2, 3, 1);
        g.connect(3, 2, 1);
        g_algo.init(g);
    }
    @Test
    void copy() {
        DirectedWeightedGraph d = new DWGraph();
        d = g_algo.copy();
        NodeData n = new NodeData_(5,5, new GeoLocation_(5,6,7));
        d.addNode(n);
        assertEquals(5, g.nodeSize());
        assertEquals(6, d.nodeSize());
    }

    @Test
    void isConnected() {
        g.connect(0, 1, 5);
        g.connect(1, 2, 4);
        g.connect(2, 3, 3);
        g.connect(3, 4, 2);
        g.connect(4, 0, 1);
        assertTrue(g_algo.isConnected());

        g.removeEdge(0, 1);
        assertFalse(g_algo.isConnected());

        DirectedWeightedGraph graph = new DWGraph();
        DirectedWeightedGraphAlgorithms graphA = new DWGraph_Algo();
        graph.addNode(new NodeData_(0, 1, new GeoLocation_(0,0,0)));
        graphA.init(graph);
        assertTrue(graphA.isConnected());
    }

    @Test
    void shortestPathDist() {
        DWGraph g = new DWGraph();
        DirectedWeightedGraphAlgorithms g_algo = new DWGraph_Algo(g);
        GeoLocation_ l1 = new GeoLocation_(1,2,3);
        GeoLocation_ l2 = new GeoLocation_(3,5,3);
        GeoLocation_ l3 = new GeoLocation_(5,4,8);
        GeoLocation_ l4 = new GeoLocation_(6,1,8);
        GeoLocation_ l5 = new GeoLocation_(2,7,1);
        NodeData_ n1 = new NodeData_(0,0, l1);
        NodeData_ n2 = new NodeData_(1,1, l2);
        NodeData_ n3 = new NodeData_(2,1, l3);
        NodeData_ n4 = new NodeData_(3,1, l4);
        NodeData_ n5 = new NodeData_(4,1, l5);
        g_algo.getGraph().addNode(n1);
        g_algo.getGraph().addNode(n2);
        g_algo.getGraph().addNode(n3);
        g_algo.getGraph().addNode(n4);
        g_algo.getGraph().addNode(n5);
        g_algo.getGraph().connect(0,1,1);
        g_algo.getGraph().connect(3,0,3);
        g_algo.getGraph().connect(0,2,2);
        g_algo.getGraph().connect(1,3,4);
        g_algo.getGraph().connect(2,3,5);
        g_algo.getGraph().connect(4,1,3);
        g_algo.getGraph().connect(3,4,2);
        double a =g_algo.shortestPathDist(0, 3);
        assertEquals(a, 5);
        assertEquals(g_algo.shortestPathDist(0, 2), 2);
        assertEquals(g_algo.shortestPathDist(1, 2), 9);
        assertEquals(g_algo.shortestPathDist(2,3), 5);
        assertEquals(g_algo.shortestPathDist(0,4), 7);
    }
//
//
    @Test
    void shortestPath() {
        DWGraph g = new DWGraph();
        DirectedWeightedGraphAlgorithms g_algo = new DWGraph_Algo(g);
        GeoLocation l1 = new GeoLocation_(1,2,3);
        GeoLocation l2 = new GeoLocation_(3,5,3);
        GeoLocation l3 = new GeoLocation_(5,4,8);
        GeoLocation l4 = new GeoLocation_(6,1,8);
        GeoLocation l5 = new GeoLocation_(2,7,1);
        NodeData_ n1 = new NodeData_(0,0, l1);
        NodeData_ n2 = new NodeData_(1,1, l2);
        NodeData_ n3 = new NodeData_(2,1, l3);
        NodeData_ n4 = new NodeData_(3,1, l4);
        NodeData_ n5 = new NodeData_(4,1, l5);
        g_algo.getGraph().addNode(n1);
        g_algo.getGraph().addNode(n2);
        g_algo.getGraph().addNode(n3);
        g_algo.getGraph().addNode(n4);
        g_algo.getGraph().addNode(n5);
        g_algo.getGraph().connect(0,1,1);
        g_algo.getGraph().connect(3,0,3);
        g_algo.getGraph().connect(0,2,2);
        g_algo.getGraph().connect(1,3,4);
        g_algo.getGraph().connect(2,3,5);
        g_algo.getGraph().connect(4,1,3);
        g_algo.getGraph().connect(3,4,2);
        List<NodeData> list1 = new ArrayList<>();
        list1.add(n1);
        list1.add(n2);
        list1.add(n4);
        list1.add(n5);

        assertEquals(g_algo.shortestPath(0, 4), list1);
    }

    @Test
    void center() {
    DWGraph g = new DWGraph();
    DirectedWeightedGraphAlgorithms g_algo = new DWGraph_Algo(g);
    GeoLocation_ l1 = new GeoLocation_(1,2,3);
    GeoLocation_ l2 = new GeoLocation_(3,5,3);
    GeoLocation_ l3 = new GeoLocation_(5,4,8);
    GeoLocation_ l4 = new GeoLocation_(6,1,8);
    GeoLocation_ l5 = new GeoLocation_(2,7,1);
    NodeData_ n1 = new NodeData_(0,0, l1);
    NodeData_ n2 = new NodeData_(1,1, l2);
    NodeData_ n3 = new NodeData_(2,1, l3);
    NodeData_ n4 = new NodeData_(3,1, l4);
    NodeData_ n5 = new NodeData_(4,1, l5);
        g_algo.getGraph().addNode(n1);
        g_algo.getGraph().addNode(n2);
        g_algo.getGraph().addNode(n3);
        g_algo.getGraph().addNode(n4);
        g_algo.getGraph().addNode(n5);
        g_algo.getGraph().connect(0,1,1);
        g_algo.getGraph().connect(3,0,3);
        g_algo.getGraph().connect(0,2,2);
        g_algo.getGraph().connect(1,3,4);
        g_algo.getGraph().connect(2,3,5);
        g_algo.getGraph().connect(4,1,3);
        g_algo.getGraph().connect(3,4,2);
        g_algo.center();

        NodeData N = g_algo.getGraph().getNode(3);
        assertEquals(N, g_algo.center());

    }

    @Test
    void tsp() {
        GeoLocation p = new GeoLocation_(1,2,3);
        GeoLocation p1 = new GeoLocation_(1,2,3);
        GeoLocation p2 = new GeoLocation_(1,2,3);
        DWGraph d = new DWGraph();
        NodeData n1 = new NodeData_(0,1,p);
        NodeData n2 = new NodeData_(1,1,p1);
        NodeData n3 = new NodeData_(2,1,p2);
        d.addNode(n1);
        d.addNode(n2);
        d.addNode(n3);
        d.connect(0,1,5);
        d.connect(1,2,4);
        d.connect(2,0,6);
        DirectedWeightedGraphAlgorithms g = new DWGraph_Algo();
        g.init(d);

        List<NodeData> l = new LinkedList<>();
        l.add(n1);
        l.add(n2);
        l.add(n3);
        List<NodeData> list = g.tsp(l);
        assertEquals(list.get(0),n1);
        assertEquals(list.get(1),n2);
        assertEquals(list.get(2),n3);
    }


    @Test
    void save() {
        g_algo.save("data/G1.json");
//        DirectedWeightedGraphAlgorithms ga3 = new_ga DWGraph_Algo();

        DirectedWeightedGraphAlgorithms new_ga = new DWGraph_Algo();
        DirectedWeightedGraph g1= g;

            new_ga.load("data/G"+1+"");
            assertNotEquals(g1,new_ga.getGraph());
            System.out.println("after load graph G"+1+" from Json:\n" + new_ga.getGraph());
            g1 = new_ga.getGraph();

//        ga3.load(".txt");
//        System.out.println(g_algo.getGraph());
//        System.out.println(ga3.getGraph());
//        assertEquals(g_algo.toString(),ga3.toString());
    }

}
