package api;



import java.util.*;

public class DWGraph implements DirectedWeightedGraph{

    public HashMap<Integer,NodeData> nodes;
    public HashMap<Integer,HashMap<Integer,EdgeData>> edges;
    public int sizeNode;
    public int sizeEdges;
    public int mc;

    /**
     * Default constructor
     */
    public DWGraph(){
        this.edges = new HashMap<>();
        this.nodes = new HashMap<>();
        this.sizeEdges = 0;
        this.sizeNode = 0;
        this.mc = 0;
    }

    /**
     * Deep copy constructor
     * @param g
     */
    public DWGraph(DirectedWeightedGraph g){
        this.edges = new HashMap<>();
        this.nodes = new HashMap<>();
        nodesCopy(g, this.nodes);
        edgesCopy(g, this.edges);
        this.sizeNode = g.nodeSize();
        this.sizeEdges = g.edgeSize();
    }
    /**
     * A copy constructor fo the edges in th graph.
     * @param  g DirectedWeightedGraph
     * @param edges hashmap of edges
     * @return new data structure of HashMap that have copied all the edges in the graph.
     */
    private HashMap<Integer, HashMap<Integer, EdgeData>> edgesCopy(DirectedWeightedGraph g, HashMap edges) {
        HashMap<Integer, HashMap<Integer, EdgeData>> h = edges;
        Iterator<EdgeData> it = g.edgeIter();
        while (it.hasNext()) {
            EdgeData E = it.next();
            this.edges.get(E.getSrc()).put(E.getDest(), E);
        }
        return h;
    }

    /**
     * A copy constructor fo the nodes in th graph.
     * @param  g DirectedWeightedGraph
     * @param nodes hashmap of nodes
     * @return new data structure of HashMap that have copied all the nodes in the graph.
     */
    private HashMap<Integer, NodeData> nodesCopy(DirectedWeightedGraph g, HashMap nodes) {
        HashMap<Integer, NodeData> h = nodes;
        Iterator<NodeData> it = g.nodeIter();
        while (it.hasNext()) {
            NodeData N = it.next();
            this.addNode(N);
        }
        return h;
    }

    /**
     * returns the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public NodeData getNode(int key) {
        return this.nodes.get(key);
    }

    /**
     * returns the data of the edge (src,dest), null if none.
     * Note: this method should run in O(1) time.
     * @param src
     * @param dest
     * @return
     */
    @Override
    public EdgeData getEdge(int src, int dest) {
        if (this.nodes.containsKey(dest) && this.nodes.containsKey(src)){
            return this.edges.get(src).get(dest);
        }
        return null;
    }
    /**
     * adds a new node to the graph with the given node_data.
     * Note: this method should run in O(1) time.
     * @param n
     */
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
    /**
     * Connects an edge with weight w between node src to node dest.
     * * Note: this method should run in O(1) time.
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
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
    /**
     * This method returns an Iterator for the
     * collection representing all the nodes in the graph.
     * Note: if the graph was changed since the iterator was constructed - a RuntimeException should be thrown.
     * @return Iterator<node_data>
     */
    @Override
    public Iterator<NodeData> nodeIter() {
        int MC = this.mc;
        Collection<NodeData> N = this.nodes.values();
        if(MC != mc){
            throw new RuntimeException();
        }
        return N.iterator();
    }
    /**
     * This method returns an Iterator for all the edges in this graph.
     * Note: if any of the edges going out of this node were changed since the iterator was constructed - a RuntimeException should be thrown.
     * @return Iterator<EdgeData>
     */

    @Override
    public Iterator<EdgeData> edgeIter() {
        int MC= this.mc;
        Collection<EdgeData> E =  new ArrayList<>();
        Iterator<NodeData> n = nodeIter();
        while(n.hasNext()){
            NodeData ne = n.next();
            Iterator<EdgeData> R = this.edgeIter(ne.getKey());
            while (R.hasNext()) {
                E.add(R.next());
            }
        }

        if(MC != mc){
            throw new RuntimeException();
        }
        return E.iterator();
    }
    /**
     * This method returns an Iterator for edges getting out of the given node (all the edges starting (source) at the given node).
     * Note: if the graph was changed since the iterator was constructed - a RuntimeException should be thrown.
     * @return Iterator<EdgeData>
     */
    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        int MC = this.mc;
        if(MC != mc){
            throw new RuntimeException();
        }
        return this.edges.get(node_id).values().iterator();
    }
    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(k), V.degree=k, as all the edges should be removed.
     * @return the data of the removed node (null if none).
     * @param key
     */
    @Override
    public NodeData removeNode(int key) {
        if(this.nodes.containsKey(key)){

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
    /**
     * Deletes the edge from the graph,
     * Note: this method should run in O(1) time.
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     */
    @Override
    public EdgeData removeEdge(int src, int dest) {
            EdgeData E = edges.get(src).remove(dest);

        if(E != null){
            sizeEdges--;
            mc++;
        }
        return E;
    }
    /** Returns the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     * @return
     */
    @Override
    public int nodeSize() {
        return this.sizeNode;
    }
    /**
     * Returns the number of edges (assume directional graph).
     * Note: this method should run in O(1) time.
     * @return
     */
    @Override
    public int edgeSize() {
        return this.sizeEdges;
    }
    /**
     * Returns the Mode Count - for testing changes in the graph.
     * @return
     */
    @Override
    public int getMC() {
        return this.mc;
    }

    /**
     * Returns the string of the graph
     * @return
     */
    public String toString() {
        String nodesVal = "{";
        for (NodeData n : nodes.values()) {
            nodesVal += n;
        }
        nodesVal += "}";
        String edgesVal = "{";
        for (HashMap h : edges.values()) {
            if (!h.values().isEmpty()) {
                edgesVal += "{ ";
                for (Object o : h.values()) {
                    EdgeData e = (EdgeData) o;
                    edgesVal += e + " ";
                }
                edgesVal += "}";
            }
        }
        edgesVal += "}";

        return "Graph{" +
                "nodes=" + nodesVal +
                ", edges=" + edgesVal +
                '}';
    }

}
