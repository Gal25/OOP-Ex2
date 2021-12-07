package tests;

import api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeData_Test {
    EdgeData edge;
    NodeData node1;
    NodeData node2;

    public void initEdge(){
        GeoLocation_ first = new GeoLocation_(3,3,3);
        GeoLocation_ second = new GeoLocation_(7,10,0);
        node1 = new NodeData_(10, 1.4, first);
        node2 = new NodeData_(11, 1.5, second);
        edge = new EdgeData_(node1.getKey(), node2.getKey(), 5);
    }

    @Test
    void getSrc() {
        initEdge();
        assertEquals(edge.getSrc(),10);
        assertNotEquals(edge.getSrc(), 11);
    }

    @Test
    void getDest() {
        initEdge();
        assertNotEquals(edge.getDest(),10);
        assertEquals(edge.getDest(), 11);
    }

    @Test
    void getWeight() {
        initEdge();
        double weight = edge.getWeight();
        assertNotEquals(weight, 10);
        assertEquals(weight, 5);
    }

    @Test
    void getInfo() {
        initEdge();
        assertNotEquals(edge.getInfo(), "new edge");
        assertEquals(edge.getInfo(), "");
    }

    @Test
    void setInfo() {
        initEdge();
        String info = "Gray";
        edge.setInfo(info);
        assertNotEquals(edge.getInfo(), "");
        assertEquals(edge.getInfo(), info);
    }

    @Test
    void getTag() {
        initEdge();
        assertEquals(edge.getTag(), 0);
        assertNotEquals(edge.getTag(), 15);
    }

    @Test
    void setTag() {
        initEdge();
        int tag = edge.getTag();
        int newTag = 8;
        edge.setTag(newTag);
        assertNotEquals(edge.getTag(), tag);
        assertEquals(edge.getTag(), newTag);
    }
}