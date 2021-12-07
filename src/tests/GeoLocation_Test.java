package tests;

import api.GeoLocation;
import api.GeoLocation_;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeoLocation_Test {
    public static GeoLocation LNode[];


    @BeforeAll
    public static void pointCreator() {
        LNode = new GeoLocation[3];
        LNode[0] = new GeoLocation_(1,1,1);
        LNode[1] = new GeoLocation_(10,10,0);
        LNode[2] = new GeoLocation_(-3, -6,2);
    }

    @Test
    public void distanceTest() {
        double d1 = LNode[0].distance(LNode[1]);
        double d2 = LNode[1].distance(LNode[2]);
        double d3 = LNode[2].distance(LNode[0]);
        assertEquals(d1, Math.sqrt(163));
        assertEquals(d2, Math.sqrt(429));
        assertEquals(d3, Math.sqrt(66));
    }
}