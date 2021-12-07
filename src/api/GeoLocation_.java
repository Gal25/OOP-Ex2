package api;
import Util.Point3D;

public class GeoLocation_ implements GeoLocation{

    private double x;
    private double y;
    private double z;

    public GeoLocation_(double _x,double _y,double _z){
        this.x = _x;
        this.y = _y;
        this.z = _z;

    }
    public GeoLocation_(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    public GeoLocation_(GeoLocation_ g ){
        this.x = g.x;
        this.y = g.y;
        this.z = g.z;
    }
    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(GeoLocation g) {
        double d1 = Math.pow(this.x - g.x(),2);
        double d2 = Math.pow(this.z - g.z(),2);
        double d3 = Math.pow(this.y - g.y(),2);

        return Math.sqrt(d1+d2+d3);
    }
     public String toString(){
        return ("" + x() + ", "+ y() + ", "+ z());
     }
}
