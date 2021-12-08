package api;



import java.util.*;

public class DWGraph implements DirectedWeightedGraph{

    public HashMap<Integer,NodeData> nodes;
    public HashMap<Integer,HashMap<Integer,EdgeData>> edges;
    public int sizeNode;
    public int sizeEdges;
    public int mc;

    //defult constructor
    public DWGraph(){
        this.edges = new HashMap<>();
        this.nodes = new HashMap<>();
        this.sizeEdges = 0;
        this.sizeNode = 0;
        this.mc = 0;
    }

    //deep copy constructor
    public DWGraph(DirectedWeightedGraph g){
        this.edges = new HashMap<>();
        this.nodes = new HashMap<>();
        nodesCopy(g, this.nodes);
        edgesCopy(g, this.edges);
        this.sizeNode = g.nodeSize();
        this.sizeEdges = g.edgeSize();
    }

    private HashMap<Integer, HashMap<Integer, EdgeData>> edgesCopy(DirectedWeightedGraph g, HashMap edges) {
        HashMap<Integer, HashMap<Integer, EdgeData>> h = edges;
        Iterator<EdgeData> it = g.edgeIter();
        while ( it.hasNext()) {
            EdgeData E = it.next();
            this.edges.get(E.getSrc()).put(E.getDest(), E);
        }
        return h;
    }


    private HashMap<Integer, NodeData> nodesCopy(DirectedWeightedGraph g, HashMap nodes) {
        HashMap<Integer, NodeData> h = nodes;
        Iterator<NodeData> it = g.nodeIter();
        while (it.hasNext()) {
            NodeData N = it.next();
            this.addNode(N);
        }
        return h;
    }

    @Override
    public NodeData getNode(int key) {
        return this.nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        if (this.nodes.containsKey(dest) && this.nodes.containsKey(src)){
            return this.edges.get(src).get(dest);
        }
        return null;
    }

    @Override
    public void addNode(NodeData n) {
        if(nodes.containsKey(n.getKey())){
            return;
        }
        else{
            nodes.put(n.getKey(),n);
            edges.put(n.getKey(),new HashMap<>());
            sizeNode++;
            mc++;
        }

    }

    @Override
    public void connect(int src, int dest, double w) {
        if (src != dest){
            if (nodes.get(src)!= null && nodes.get(dest) != null && edges.get(src).get(dest) == null){
                EdgeData E = new EdgeData_(src,dest,w);
                edges.get(src).putIfAbsent(dest, E);
                mc++;
                sizeEdges++;
            }

        }

    }

    @Override
    public Iterator<NodeData> nodeIter() {
        int MC = this.mc;
        Collection<NodeData> N = this.nodes.values();
        if(MC != mc){
            throw new RuntimeException();
        }
        return N.iterator();
    }


    @Override
    public Iterator<EdgeData> edgeIter() {
        int MC= this.mc;
        Collection<EdgeData> E =  new ArrayList<>();
        for (int i= 0; i < sizeNode; i++){
            Iterator<EdgeData> R = this.edgeIter(i);
            while (R.hasNext()) {
                E.add(R.next());
            }
        }
        if(MC != mc){
            throw new RuntimeException();
        }
        return E.iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        int MC = this.mc;
        if(MC != mc){
            throw new RuntimeException();
        }
        return this.edges.get(node_id).values().iterator();
    }

    @Override
    public NodeData removeNode(int key) {
        if(this.nodes.containsKey(key)){
//            HashMap<Integer, EdgeData> h = this.edges.remove(key);
//            this.sizeEdges = this.sizeEdges - h.size();
//            this.mc = this.mc + h.size();
            Collection<Integer> E = edges.keySet();
            for (int i : E){
                if(edges.get(i).containsKey(key)){
                    edges.get(i).remove(key);
                    sizeEdges--;
                    mc++;

                }
            }
            NodeData N_remove = nodes.remove(key);
            sizeNode--;
            mc++;

            return N_remove;
        }

        return null;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        EdgeData E = edges.get(src).remove(dest);

        if(E != null){
            sizeEdges--;
            mc++;
        }
        return E;
    }

    @Override
    public int nodeSize() {
        return this.sizeNode;
    }

    @Override
    public int edgeSize() {
        return this.sizeEdges;
    }

    @Override
    public int getMC() {
        return this.mc;
    }

    public String toString() {
        String nodesValue = "{";
        for (NodeData n : nodes.values()) {
            nodesValue += n;
        }
        nodesValue += "}";

        String edgesValue = "{";
        for (HashMap h : edges.values()) {
            if (!h.values().isEmpty()) {
                edgesValue += "{ ";
                for (Object o : h.values()) {
                    EdgeData e = (EdgeData) o;
                    edgesValue += e + " ";

                }
                edgesValue += "}";
            }
        }
        edgesValue += "}";

        return "DWGraph{" +
                "nodes=" + nodesValue +
                ", edges=" + edgesValue +
                '}';
    }

}
