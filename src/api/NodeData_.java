package api;

public class NodeData_ implements NodeData {
    private int key;
    private GeoLocation location;
    private double weight;
    private String info;
    private int tag;


    public NodeData_(int key, double weight, GeoLocation location) {
        this.key = key;
        this.weight = weight;
        this.location = location;
        this.tag = 0;
        this.info = "";
    }

    public NodeData_(GeoLocation_ lNode) {
        this.location = lNode;
    }

    public NodeData_(GeoLocation L, int K) {
        this.key = K;
        this.location = L;
    }

    public NodeData_(int key) {
        this.key = key;
    }


    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.location = p;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    public String toString() {
        return "[" + key + "]";
    }


}


