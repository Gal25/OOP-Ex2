package tests;

import api.GeoLocation;
import api.GeoLocation_;
import api.NodeData;
import api.NodeData_;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeData_Test {

    private NodeData_ node;
     GeoLocation_ LNode;

    public void newNode1(){
        this.LNode = new GeoLocation_(4,3,2);
        this.node = new NodeData_(LNode);
    }

    public void newNode2(){
        this.LNode = new GeoLocation_(9,8,7);
        this.node = new NodeData_(3, -2.7, LNode);
    }
    public void newNode3(){
        this.LNode = new GeoLocation_(-10,3,7.5);
        this.node = new NodeData_(10, 1.555, LNode);
    }

    @Test
    void getKey() {
        newNode1();
        assertEquals(node.getKey(), 0);
        assertNotEquals(10, node.getKey());
        newNode2();
        assertEquals(node.getKey(), 3);
        newNode3();
        assertEquals(10, node.getKey());
    }

    @Test
    void getLocation() {
        newNode1();
        GeoLocation g = node.getLocation();
        assertEquals(g, LNode);
        newNode2();
        g = node.getLocation();
        assertEquals(g, LNode);
        newNode3();
        g = node.getLocation();
        assertSame(g, LNode);
    }

    @Test
    void setLocation() {
        newNode1();
        GeoLocation geo = node.getLocation();
        GeoLocation geoNew = new GeoLocation_(-10,-20, 100);
        node.setLocation(geoNew);
        assertEquals(node.getLocation(), geoNew);
        newNode3();
        node.setLocation(geoNew);
        assertEquals(node.getLocation(), geoNew);
    }

    @Test
    void getWeight() {
        newNode1();
        double weight = node.getWeight();
        assertEquals(0, weight);
        newNode2();
        weight = node.getWeight();
        assertEquals(-2.7, weight);
        newNode3();
        weight = node.getWeight();
        assertEquals(1.555, weight);
    }

    @Test
    void setWeight() {
        newNode1();
        double newWeight = -2;
        node.setWeight(newWeight);
        assertEquals(node.getWeight(), newWeight);
        newNode2();
        node.setWeight(newWeight);
        assertEquals(node.getWeight(), newWeight);
        newNode3();
        node.setWeight(100000);
        assertNotEquals(node.getWeight(), newWeight);
    }

    @Test
    void getInfo() {
        newNode1();
        assertNotEquals(node.getInfo(), "Gray");
        newNode2();
        assertEquals(node.getInfo(), "");
    }

    @Test
    void setInfo() {
        newNode3();
        node.setInfo("Gray");
        assertNotEquals(node.getInfo(), "");
        node.setInfo("Red");
        assertEquals(node.getInfo(), "Red");
    }

    @Test
    void getTag() {
        newNode1();
        assertEquals(node.getTag(), 0);
        newNode2();
        assertNotEquals(node.getTag(), 10);
    }

    @Test
    void setTag() {
        newNode3();
        node.setTag(34);
        assertNotEquals(node.getTag(), 0);
        newNode2();
        node.setTag(6);
        assertEquals(node.getTag(), 6);
    }
}