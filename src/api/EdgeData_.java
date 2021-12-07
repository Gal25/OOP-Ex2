package api;

public class EdgeData_ implements EdgeData{
    private int src;
    private int dest;
    private double weight;
    private int tag;
    private String info;

    public EdgeData_(int src, int dest, double weight){
        this.dest=dest;
        this.src=src;
        this.weight=weight;
        this.tag=0;
        this.info="";

    }



    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.weight;
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
        return  +src + "--" + this.weight + "-->" + dest;
    }
}
