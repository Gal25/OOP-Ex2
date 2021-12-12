package tests;

import api.*;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class DWGraphTest {
    private static DirectedWeightedGraph graph = new DWGraph();

    public DWGraphTest() {
    		for (int i = 0; i < 10; i++) {
    			NodeData n = new NodeData_(i, i,  new GeoLocation_(i, i+1, i+2));

    			graph.addNode(n);
    		}

    }

    @Test
    void getNode() {
        assertNotNull(graph.getNode(5));;
        assertNull(graph.getNode(11));

    }


    @Test
    void getEdge() {
        EdgeData edge = new EdgeData_(1, 10,1);
        NodeData node1 = new NodeData_(1,1,  new GeoLocation_(1,2,3));
        NodeData node2 = new NodeData_(10,1, new GeoLocation_(3,4,5));
        graph.addNode(node1);
        graph.addNode(node2);
        graph.connect(1, 10, 1);
        assertEquals(graph.getEdge(edge.getSrc(), edge.getDest()).getInfo(), edge.getInfo());

    }


    @Test
    void addNode() {
        DirectedWeightedGraph g2 = new DWGraph(graph);
        g2.addNode(g2.getNode(0));
        assertEquals(g2.toString(),graph.toString());
    }


    @Test
    void connect() {
        DirectedWeightedGraph graph1 = new DWGraph();
        for (int i = 0; i < 10; i++) {
            GeoLocation p = new GeoLocation_(i, i+1, i+2);
            NodeData n = new NodeData_(i, i, p);
            graph1.addNode(n);
        }
        for (int i = 0; i < 100 ; i++) {
            for (int j = 1; j < 5; j++) {
                graph1.connect(i, i+j, 0);
            }
        }
        assertEquals(30, graph1.edgeSize());
    }

    @Test
    void removeNode() {
        DirectedWeightedGraph graph2 = new DWGraph();
        NodeData node1 = new NodeData_(1,1,  new GeoLocation_(1,2,3));
        NodeData node2 = new NodeData_(2,1, new GeoLocation_(3,4,5));
        graph2.addNode(node1);
        graph2.addNode(node2);
        graph2.removeNode(1);
        assertEquals(1, graph2.nodeSize());
   }


    @Test
    void removeEdge() {
        graph.connect(0, 1, 0);
        graph.connect(4, 5, 1);
        graph.connect(6, 7, 1);
        graph.removeEdge(0, 1);
        graph.removeEdge(2, 3);
        graph.removeEdge(4, 5);
        assertEquals(2, graph.edgeSize());
    }

    @Test
    void nodeSize() {
        int size = graph.nodeSize();
        graph.removeNode(1);
        graph.removeNode(1);     //node does not exist
        graph.removeNode(8);
        assertEquals(size-2,graph.nodeSize());
    }

    @Test
    void edgeSize() {

        DirectedWeightedGraph graph1 = new DWGraph();
        for (int i = 0; i < 5; i++) {
            GeoLocation p = new GeoLocation_(i, i+1, i+2);
            NodeData n = new NodeData_(i, i, p);
            graph1.addNode(n);
        }
        for (int i = 0; i < 5 ; i++) {
            for (int j = 1; j < 5; j++) {
                graph1.connect(i, i+j, 0);
            }
        }
        int size = graph1.edgeSize();
        graph1.removeEdge(0,1);
        graph1.removeEdge(1,3);
        assertEquals(size-2,graph1.edgeSize());
    }

    @Test
    void getMC() {
        DirectedWeightedGraph graph2 = new DWGraph();
        for (int i = 0; i < 10; i++) {
            NodeData n = new NodeData_(i, i, new GeoLocation_(i, i+1, i+2));
            graph2.addNode(n);
        }
        assertEquals(10, graph2.getMC());
        graph2.connect(0, 1, 0);
        graph2.connect(1, 2, 0);
        graph2.removeNode(0);
        assertEquals(13, graph2.getMC());
    }
}
