package api;
import com.google.gson.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;


import java.util.List;

public class DWGraph_Algo implements DirectedWeightedGraphAlgorithms{

    private DirectedWeightedGraph graph;
    HashMap<Integer, Integer> Hash = new LinkedHashMap<>();


    /**
     * Defult constructor
     */
    public DWGraph_Algo(){
        this.graph = new DWGraph();
    }

    /**
     * constructor
     * @param graph2
     */
    public DWGraph_Algo(DirectedWeightedGraph graph2){
        this.graph = graph2;

    }

    /**
     * Inits the graph on which this set of algorithms operates on.
     * @param g
     */
    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = g;
    }

    /**
     * Returns the underlying graph of which this class works.
     * @return
     */
    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    /**
     * Computes a deep copy of this weighted graph.
     * @return
     */
    @Override
    public DirectedWeightedGraph copy() {
        return new DWGraph(graph);
    }

    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
     * The algorithm : send twice to check if the graph is connected,
     * once an inverted graph and once with the original graph. To check that the graph is a strong connected
     * @return true is the graph is connected , false if the graph is not connected
     */
    @Override
    public boolean isConnected() {
        if(graph.nodeSize() == 0 || graph.nodeSize() == 1){ //if there is a 1 of 0 nodes in the graph its mean the graph is connected
            return true;
        }
        NodeData N = graph.nodeIter().next();
        return (BFS_isConnected(graph, N)) && (BFS_isConnected(UpsideDownGraph(), N));

    }


    /**
     * This function representing the shortest distance between first node(source) and second node (destination).
     * The function will be us the Dijkstra algorithm to a weighted directional graph.
     * @param src  - start node
     * @param dest - end (target) node
     * @return length of the shortest path between src to dest
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if( graph.getNode(src)!= null && graph.getNode(dest) != null){ //check if the keys does not exist in the graph
            if((this.graph.nodeIter().equals(graph.getNode(src))) || (this.graph.nodeIter().equals(graph.getNode(dest))))
                return -1;

            if (src == dest)
                return 0;

            changeTags();
            double[] ans = algorithm_of_Dijkstra(graph.getNode(src));

            int len = ans.length;
            if(dest <= len){
                return ans[dest];
            }
        }
        return -1;
    }



    /**
     *This function representing the shortest distance between first node(source) and second node (destination).
     *The function will be us the Dijkstra algorithm to a weighted directional graph.
     * @param src - start node
     * @param dest - end (destination) node
     * @return shortest path between src to dest - as an ordered List of nodes
     */
    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        List<NodeData> path = new ArrayList<>();
        if (graph.getNode(src) == null || graph.getNode(dest) == null){
            return null;
        }

        if (src == dest) {
            path.add(graph.getNode(src));
            return path;
        }

        shortestPathDist(src,dest);
        if(!Hash.containsKey(dest)){ //there isn't a path between the src the dest
            return null;
        }

        while (dest != -1) { // add the nodes that we pass over them in the shortestPathDist in the path list
            path.add(graph.getNode(dest));
            dest = Hash.get(dest);
        }
        Collections.reverse(path); //reverses the order of elements in a list
        return path;
    }

    /**
     * Finds the NodeData which minimizes the max distance to all the other nodes.
     * Assuming the graph isConnected, elese return null. See: https://en.wikipedia.org/wiki/Graph_center
     * @return the Node data to which the max shortest path to all the other nodes is minimized.
     */
    @Override
    public NodeData center() {
        if(graph.nodeSize() != 0){
            double f = Integer.MAX_VALUE;
            Iterator<NodeData> iterator = graph.nodeIter();
            NodeData ans = null;
            while (iterator.hasNext()) {
                NodeData curr = iterator.next();
                double path = findTheLongestPathInDijkstra(curr);
                if(f >= path){
                    f = path;
                    ans = curr;
                }
            }
            return ans;
        }
        return null;
    }

    /**
     * Computes a list of consecutive nodes which go over all the nodes in cities.
     * the sum of the weights of all the consecutive (pairs) of nodes (directed) is the "cost" of the solution -the lower the better.
     * @param cities - the nodes that we need to pass over them
     * @return the list of consecutive nodes which go over all the nodes in cities.
     */
    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        List<NodeData> TSP = new ArrayList<NodeData>();
        List<NodeData> temp = new ArrayList<NodeData>();

        if( cities.size() == 0){
            return null;
        }
        try {
            if (cities.size() == 1) {
                NodeData N = graph.getNode(cities.get(0).getKey());
                TSP.add(N);
                return TSP;
            }
            for (int i = 0; i + 1 < cities.size(); i++) {
                if (cities.get(i).getKey() == cities.get(i + 1).getKey()) {
                    cities.remove(i);
                    i--;
                } else {
                    temp =  shortestPath(cities.get(i).getKey(), cities.get(i+1).getKey()); // take the shortest path go to the closer node
                    if (i > 0) { // put just the list with the minimum path and delete the other
                        temp.remove(0);
                    }

                }
                TSP.addAll(temp);
            }
            return TSP;
        }
        catch (Exception e){ // if there isn't a path between the edges - isn't connected
            return null;
        }
    }

    /**
     * Saves the json file that is a directed weighted graph to the given
     * file name - in JSON format
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        JsonObject jsonObject = new JsonObject();
        JsonArray  Ed = new JsonArray ();
        JsonArray  No = new JsonArray ();
        jsonObject.add("Edges",Ed);
        jsonObject.add("Nodes",No);

        for (Iterator<NodeData> it = this.graph.nodeIter(); it.hasNext(); ) {
            NodeData N = it.next();
            JsonObject B = new JsonObject();
            B.addProperty("pos", "" + N.getLocation().x() + "," + N.getLocation().y() + "," + N.getLocation().z());
            B.addProperty("id",  + N.getKey());
            No.add(B);
        }
        for (Iterator<EdgeData> z = this.graph.edgeIter(); z.hasNext(); ) {
            EdgeData E = z.next();
            JsonObject A = new JsonObject();
            A.addProperty("src", + E.getSrc());
            A.addProperty("w", + E.getWeight());
            A.addProperty("dest", + E.getDest());
            Ed.add(A);
        }
        try{
            Gson gson= new GsonBuilder().setPrettyPrinting().create();;
            FileWriter fw=new FileWriter(file);
            fw.write(gson.toJson(jsonObject));
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * This function implement the load of a graph to this graph algorithm.
     * @param file - file name of JSON file
     * @return true - The load was successful
     */
    @Override
    public boolean load(String file) {
        try{
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(DWGraph.class, new graphJsonDeserializer());
            Gson gson = builder.setPrettyPrinting().create();

            FileReader reader = new FileReader(file);
            DirectedWeightedGraph graph = gson.fromJson(reader, DWGraph.class);
            this.init(graph);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    /**
     * This function representing the Dijkstra's algorithm.
     * Solves the problem of finding the easiest route from point in graph to destination in weighted graph.
     * It is possible to find using this algorithm, at this time, the fast paths to all the points in the graph.
     * The algorithm calculates the weights of the nodes with the desired edges each time and compares them.
     * According to the algorithm we get the path with the lowest weight.
     * Link: https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
     * Complexity: (O(V+E)), |V|=number of nodes, |E|=number of edges.
     * @param src the source node
     */
    private double[] algorithm_of_Dijkstra(NodeData src) {
        double[] weightList = new double[graph.nodeSize()];

        for (int i = 0; i < graph.nodeSize(); i++) {
            graph.getNode(i).setWeight(Double.MAX_VALUE);
        }
        Hash.put(src.getKey(), -1);
        src.setTag(0);
        src.setWeight(0);
        if(src != null){
            src.setWeight(0);
            PriorityQueue<NodeData> list = new PriorityQueue<NodeData>(graph.nodeSize(), new Comparator<NodeData>() {

                @Override
                public int compare(NodeData first, NodeData second) {
                    return Double.compare(first.getWeight(), second.getWeight());
                }
            });

            Arrays.fill(weightList, Integer.MAX_VALUE);
            weightList[src.getKey()] = 0;
            int count = 0;
            list.add(src);
            int countIter = 0;
            while (!list.isEmpty()) {
                countIter++;
                NodeData node_src = list.poll();
                if (node_src.getTag() != 1) {
                    Iterator<EdgeData> iterator = graph.edgeIter(node_src.getKey());
                    while (iterator.hasNext()) {
                        EdgeData edge = iterator.next();
                        NodeData node_Dest = graph.getNode(edge.getDest()); //init the dest of a specific edge
                        double weight = edge.getWeight() + node_src.getWeight();
                        if (node_Dest.getWeight() > weight) {
                            node_Dest.setWeight(weight);
                            weightList[node_Dest.getKey()] = node_Dest.getWeight();
                            count++;
                            Hash.put(node_Dest.getKey(), node_src.getKey());
                        }

                        list.add(node_Dest);
                    }
                    node_src.setTag(1);
                } else {
                    continue;
                }

            }
            double max = Double.MIN_VALUE;
            int index = -1;
            for (int i = 0; i < weightList.length; i++) {
                if (max < weightList[i]) {
                    max = weightList[i];
                    index = i;
                }
            }
            for (int i = 0; i < graph.nodeSize(); i++) {
                graph.getNode(i).setTag(0);
            }
        }
        return weightList;
    }


    /**
     * This function representing the graph as a string
     * @return string
     */
    public String toString() {
       return this.graph.toString();
    }

    private void reset_weight(){
        for (int i = 0; i < graph.nodeSize(); i++) {
            graph.getNode(i).setWeight(Integer.MAX_VALUE);
        }
    }
    /**
     * This function gets a node. Its realization is carried out according to the result obtained
     * from the Dijkstra algorithm, depending on the result we will go over the array and choose the
     * longest route from all the short ones and return the same node. This function helps to find the
     * center of the graph.
     * @param src - source node
     * @return - the node with the longest path from the src node.
     */
    private double findTheLongestPathInDijkstra(NodeData src){
        double[] dis =  algorithm_of_Dijkstra(src); //we get the graph with the all shortest paths to all nodes on the graph
        double curr = dis[0];

        for (int d = 1; d < dis.length; d++){
            if(curr < dis[d]){
                curr = dis[d];

            }
        }
        return curr;
    }

    /**
     * This function creates a new graph that will be the graph in the opposite direction of the existing graph
     * @return new_graph - the up-side-down graph
     */

    private DirectedWeightedGraph UpsideDownGraph(){
        DirectedWeightedGraph new_graph = new DWGraph();
        Iterator<NodeData> nodes = graph.nodeIter();
        while(nodes.hasNext()) {
            NodeData N = nodes.next();
            new_graph.addNode(new NodeData_(N.getKey())); //put all the element in the new up-side-down graph
        }
        Iterator<NodeData> N_nodes = new_graph.nodeIter();
        while(N_nodes.hasNext()) {
            NodeData No = N_nodes.next();
            Iterator<EdgeData> edges = graph.edgeIter(No.getKey());
            while (edges.hasNext()){
                EdgeData E = edges.next();
                new_graph.connect(E.getDest(),No.getKey(),E.getWeight()); // connect between the edges but in the opposite direction
            }

        }
        return new_graph;
    }

    /**
     * This function checks whether the graph is connected ,uses the BFS algorithm
     * @param graph - the directed weighted graph
     * @param N - the node
     * @return true - if connected , false - if not connected
     */

    private boolean BFS_isConnected(DirectedWeightedGraph graph, NodeData N){
        Iterator<NodeData> nodes = graph.nodeIter();
        changeTags(); // put all the tags that not visit
        BFS(graph,N);
        while(nodes.hasNext()){
            N = nodes.next();
            if(N.getTag() == -1){ //if there is one that not visit return false
                return false;
            }
        }
        return true;
    }

    /**
     * This function checks if there is a path that passes through all the vertices
     * @param graph - the directed weighted graph
     * @param n - node in the graph
     */

    private void BFS(DirectedWeightedGraph graph, NodeData n) {
        Queue<NodeData> queue= new LinkedList<>();
        n.setTag(1); // 1 = visit -1 = not visit
        queue.add(n);
        while (queue.size() != 0){
            n = queue.poll();
            Iterator<EdgeData> E = graph.edgeIter(n.getKey());
            while (E.hasNext()){
                NodeData node= graph.getNode(E.next().getDest());
                if(node.getTag() == -1){
                    queue.add(node);
                    node.setTag(1); //visit
                }
            }
        }
    }

    /**
     * this function change all the tags of the nodes to be -1 (NOT VISITED)
     */
    private void changeTags() {
        Iterator<NodeData> nodes = this.graph.nodeIter();
        while (nodes.hasNext()) {
            NodeData N = nodes.next();
            N.setTag(-1);
        }
    }




    /**
      * This class implements the Json Deserializer to allow you to load the Jason file
     */
    private static class graphJsonDeserializer implements JsonDeserializer<DWGraph> {

        @Override
        public DWGraph deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            DWGraph g = new DWGraph();
            JsonObject jsonObject = json.getAsJsonObject();
            JsonArray E = jsonObject.get("Edges").getAsJsonArray();
            JsonArray N = jsonObject.get("Nodes").getAsJsonArray();
            for (JsonElement node : N) {
                String pos = ((JsonObject)node).get("pos").getAsString();
                String[] p =pos.split(",");
                GeoLocation geo=new GeoLocation_(Double.parseDouble(p[0]),Double.parseDouble(p[1]),Double.parseDouble(p[2])) ;

                int key = ((JsonObject)node).get("id").getAsInt();
                NodeData NO=new NodeData_(geo,key);
                g.addNode(NO);
            }
            for (JsonElement edges : E) {
                int src = ((JsonObject)edges).get("src").getAsInt();
                double w = ((JsonObject)edges).get("w").getAsDouble();
                int dest = ((JsonObject)edges).get("dest").getAsInt();
                g.connect(src,dest,w);
            }
            return g;
        }
    }


}

