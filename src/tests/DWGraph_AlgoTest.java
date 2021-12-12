package tests;

import api.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {

    private static DirectedWeightedGraph g = new DWGraph();
    private static DirectedWeightedGraphAlgorithms g_algo = new DWGraph_Algo();


    public DWGraph_AlgoTest() {
        for (int i = 0; i < 5; i++) {
            NodeData n = new NodeData_(i, i, new GeoLocation_(i, i+1, i+2));
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
        g.connect(0, 1, 1);
        g.connect(1, 2, 2);
        g.connect(2, 3, 2);
        g.connect(3, 4, 3);
        g.connect(4, 0, 4);

        assertTrue(g_algo.isConnected());

        g.removeEdge(0, 1);
        assertFalse(g_algo.isConnected());

        DirectedWeightedGraph graph = new DWGraph();
        DirectedWeightedGraphAlgorithms graph_a = new DWGraph_Algo();
        graph.addNode(new NodeData_(0, 1, new GeoLocation_(0,0,0)));
        graph_a.init(graph);
        assertTrue(graph_a.isConnected());
    }

    @Test
    void shortestPathDist() {
        DWGraph graph = new DWGraph();
        DirectedWeightedGraphAlgorithms g_algo = new DWGraph_Algo(graph);
        NodeData_ n0 = new NodeData_(0,0,new GeoLocation_(0,1,0));
        NodeData_ n1 = new NodeData_(1,1,new GeoLocation_(2,3,0));
        NodeData_ n2 = new NodeData_(2,1, new GeoLocation_(4,5,0));
        NodeData_ n3 = new NodeData_(3,1,  new GeoLocation_(6,1,0));
        NodeData_ n4 = new NodeData_(4,1,new GeoLocation_(3,4,0));
        g_algo.getGraph().addNode(n0);
        g_algo.getGraph().addNode(n1);
        g_algo.getGraph().addNode(n2);
        g_algo.getGraph().addNode(n3);
        g_algo.getGraph().addNode(n4);
        g_algo.getGraph().connect(0,1,1);
        g_algo.getGraph().connect(3,0,2);
        g_algo.getGraph().connect(0,2,1);
        g_algo.getGraph().connect(1,3,3);
        g_algo.getGraph().connect(2,3,4);
        g_algo.getGraph().connect(4,1,2);
        g_algo.getGraph().connect(3,4,1);

        assertEquals(g_algo.shortestPathDist(0, 3), 4);
        assertEquals(g_algo.shortestPathDist(0, 2), 1);
        assertEquals(g_algo.shortestPathDist(1, 2), 6);
        assertEquals(g_algo.shortestPathDist(2,3), 4);
        assertEquals(g_algo.shortestPathDist(0,4), 5);
    }

    @Test
    void shortestPath() {
        g.connect(0,1,3);
        g.connect(1,4,5);
        g.connect(1,2,2);
        g.connect(4,3,2);

        DirectedWeightedGraphAlgorithms graph_2 = new DWGraph_Algo();
        graph_2.init(g);
        List<NodeData> temp = graph_2.shortestPath(0, 3);
        String ans = "";

        for (int i = 0; i < temp.size(); i++) {
            ans += ""+temp.get(i).getKey();
        }
        assertEquals(ans, "0123");
    }

    @Test
    void center() {
        DWGraph graph = new DWGraph();
        DirectedWeightedGraphAlgorithms graph_algo = new DWGraph_Algo(graph);
        NodeData_ n0 = new NodeData_(0,0,new GeoLocation_(0,1,0));
        NodeData_ n1 = new NodeData_(1,1,new GeoLocation_(2,3,0));
        NodeData_ n2 = new NodeData_(2,1, new GeoLocation_(4,5,0));
        NodeData_ n3 = new NodeData_(3,1, new GeoLocation_(6,1,0));
        NodeData_ n4 = new NodeData_(4,1,new GeoLocation_(3,4,0));
        graph_algo.getGraph().addNode(n0);
        graph_algo.getGraph().addNode(n1);
        graph_algo.getGraph().addNode(n2);
        graph_algo.getGraph().addNode(n3);
        graph_algo.getGraph().addNode(n4);
        graph_algo.getGraph().connect(0,1,1);
        graph_algo.getGraph().connect(3,0,2);
        graph_algo.getGraph().connect(0,2,1);
        graph_algo.getGraph().connect(1,3,3);
        graph_algo.getGraph().connect(2,3,4);
        graph_algo.getGraph().connect(4,1,2);
        graph_algo.getGraph().connect(3,4,1);
        graph_algo.center();

        NodeData N = graph_algo.getGraph().getNode(3);
        assertEquals(N, graph_algo.center());

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
        g_algo.save("data/G1_test.json");
        DirectedWeightedGraphAlgorithms new_ga = new DWGraph_Algo();
        DirectedWeightedGraph g1= g;

            new_ga.load("data/G1_test.json");
            assertNotEquals(g1,new_ga.getGraph());

    }

}
