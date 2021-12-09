# OOP-Ex2

_By: Dana Cherchenkov and Gal Cohen._
```
Github pages:

https://github.com/DanaCherchenkov 
https://github.com/Gal25
```

This project is an assignment in an Object Oriented Programming course at Ariel University.\
In the project we were asked to design and implement data structures and algorithms of graphs (directed and weighted). 
The project consists of two parts, first the implementation of a weighted and directed graph and the algorithms operate on the graph. The second part is design the graph and use the algorithms we implemented in the first part and make the graph visual by using JAVA SWING and bringing JFrame and JPanel libraries.\
The project include five different interfaces and seven classes. The interfaces are under the API of the graph which include in the content of all the functions for the implementation of the graph. The  five classes the implementation of the interfaces themselves, moreover there is an implementation of tests for each function in the graph (show under the package ‘tests’). Two additional classes involve the implementation of the GUI.



## API PACKAGE:
### __NodeData_Class:__
This class implements the interface NodeData.\
This simple class representing a node (vertex) on a directed weighted graph.\
In the class can find the main variables and their implementation:

•	Key -  A key that is used to as each node’s ID.\
•	Location -  An object that represent the location of the node by using the GeoLocation class.\
•	Wieght – A variable that get the weight of the node, there is an option to update the weight of the vertex,helps in calculating          functions in the DWGraph_Algo.\
•	Info –  A variable that get the information of the node by String, there is an option to update the Info of the vertex, helps in          calculating functions in the DWGraph_Algo.\
•	Tag- A variable that used by default: (-1) if the node is not visited (helps in calculating functions in the DWGraph_Algo).


## __GeoLocation_Class:__
This class implements the interface GeaoLocation.\
This simple class representing a the location of node (vertex) on a directed weighted graph.\
Can find that the location determined by values of X, Y, Z.\
In the class can find the implementation of:

•	X  - One of the value that determine the location of the node.\
•	Y -  One of the value that determine the location of the nide.\
•	Z - One of the value that determine the location of the nide.\
•	Distance – A variable that get the distance between two nodes.


## __EdgeData_Class:__
This class implements the interface EdgeData.\
This simple class representing an edge in a directed weighted graph.\
In the class can find the main variables and their implementation:

•	Source – A variable that points on the node ID, determine the point start of the edge.\
•	Destination – A variable that points on the node ID, determine the point end of the edge.\
•	Weight – A variable that determine the weight of the edge, can not be changed.\
•	Info – A variable that get the information on the edge by String, there is an option to update the Info of the vertex, helps in         calculating functions in the DWGraph_Algo.\
•	Tag- A variable that represent a temporal data (helps in calculating functions in the DWGraph_Algo).



## __DWGraph Class:__
This class implements the interface DirectedWeightedGraph.\
This class representing  a directional weight graph. Implementation of the graph according to the data structures of  Hash map. In the class can find the functions and their implementation:

| __Main Method__ | __Description__ | __Complexity__|
| :---------------- | :---------------- | :--------------: |
| public DWGraph() | default constructor |  |
| DWGraph(DirectedWeightedGraph) | deep copy constructor (of graph) | O(1) |
| getNode(int key) | Returns the node_data by the node_id | O(1) |
| getEdge(int src, int dest) | data of the edge (src,dest) | O(1) |
| addNode(NodeData) | adds a new node to the graph with the given node_data| O(1) |
| connect(int src, int dest, double) | Connects an edge with weight w between node src to node dest | O(1)|
| nodeIter() | returns an Iterator for the collection representing all the nodes in the graph | O(1) |
| edgeIter() | eturns an Iterator for all the edges in this graph | O(k) V.degree=k |
| edgeIter(int node_id) | returns an Iterator for edges getting out of the given node| O(1) |
| removeNode(int key) | Deletes the node (with the given ID) from the graph and removes all edges which starts or ends at this node| O(k), V.degree=k |
| removeEdge(int src, int dest) | Deletes the edge from the graph | O(1) |
| nodeSize() | number of vertices (nodes) in the graph | O(1) |
| edgeSize() | number of edges (assume directional graph) | O(1) |
| getMC() | Mode Count - for testing changes in the graph | O(1) |



__More private functions:__

**_edgesCopy(DirectedWeightedGraph g, HashMap edges):_** Method that get a new data structure and duplicate this HashMap of edges.\
**_nodesCopy(DirectedWeightedGraph g, HashMap nodes):_** Method that get a new data structure and duplicate this HashMap of nodes.



## __DWGraph_Algo Class:__
This class implements the interface DirectedWeightedGraphAlgorithm.\
The class represent an implementation of a Directed (positive) Weighted Graph Theory Algorithms include: colne (copy), init (graph), isConnected (strongly),  double shortestPathDist, list shortestPath, node of center, list of TSP, load a Json file, save a Json file.\
The implementation according to the data structures of Hash map (the value of the Hash map is based on the same data structure).


| __Main Method__ | __Description__ |
| :---------------- | :---------------- |
| DWGraph_Algo() | Default constructor |
| DWGraph_Algo(DirectedWeightedGraph| Constructor |
| init(DirectedWeightedGraph g) | init the graph on which this set of algorithms operates on |
| getGraph() | underlying graph of which specific class works|
| copy() | Computes a deep copy of this weighted graph |
| isConnected() | true if there is a valid path from each node to each other node |
| shortestPathDist(int src, int dest) | representing the shortest distance between first node(source) and second node (destination), us the Dijkstra algorithm (return double of the distance) |
| shortestPath(int src, int dest) | representing the shortest distance between first node(source) and second node (destination), us the Dijkstra algorithm (return list of the nodes)|
| center() | finds the NodeData which minimizes the max distance to all the other nodes |
| tsp(List<NodeData>) | computes a list of consecutive nodes which go over all the nodes in cities |
| save(String file) | saves the json file that is a directed weighted graph to the given |
| load(String file) | this function implement the load of a graph to this graph algorithm |

  
__More private functions:__
  
•	**_algorithm_of_Dijkstra:_** The function receive a NodeData. The function based on the Dijkstra's algorithm.
    Solves the problem of finding the easiest route from point in graph to destination in weighted graph. It is possible to find using       this algorithm, at this time, the fast paths to all the points in the graph. The algorithm calculates the weights of the nodes with     the desired edges each time and compares them. According to the algorithm we get the path with the lowest weight.\
    Link: https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm \
    _Complexity: (O(V+E)), |V|=number of nodes, |E|=number of edges._\
Returns an array of doubles that representing the shortest path to each node (the indexs on the array representing the nodes ID).\
•	**_toString():_** The function receive a graph and by the library in java represent the graph as a string.\
•	**_reset_weight():_** This function re – update the weight of the all nodes in the graph.\
• **_findTheLongestPathInDijkstra(NodeDate):_** This function receive a NodeData (vertex). Its realization is carried out according to the      result obtained from the Dijksrta algorithm, depending on the result we will go over the array and choose the longest path from all      the short ones and return the destination (node) that occurred with the longest path. This function helps to find the center of the      graph.\
•	**_upSideDownGraph():_** This function creates a new graph that will be the graph in the opposite direction of the existing graph.\
• **_BFS(DirectedWeightedGraph, NodeData):_** This function checks if there is a path that passes through all the vertices.\
• **_BFS_isConnected(DirectedWeightedGraph graph, NodeData N):_** This function checks whether the graph is connected ,uses the BFS           algorithm.\
__Link to the algorithm BSF: https://en.wikipedia.org/wiki/Breadth-first_search.__ \
• **_changeTags():_** This function re-update all the tags of the nodes in the graph to be -1 (NOT VISITED).\
• **_graphJsonDeserializer implements JsonDeserializer<DWGraph>:_** This class implements the Json Deserializer to allow you to load the        Jason file. 

  
## GUI PACKAGE
In this part of the project can see the graphical implementation of all the interfaces from the first part. Using JAVA SWING which is a part of Java Foundation Classes (JFC) that is used to create window-based applications.\
There is use of the JFrame and JPanel class. With the help of the functions given within the libreries and the corresponding implementation for them, we will get an overall picture of the graph.\
There is a menuBar where we can choose which JSON file is selected to load or to save, and in addition there is the use of the functions of the class DWGrapg_Algo and once we select the implementation of one of them is seen directly on the graph.\
The JSON files contain different graphs and range from G1 to G3.
In addition, we will see from the following table the runtimes for multi-vertices and edges (which are not in these classes):

For the demo we will select the _center()_ function to see the running time:
  
| __Json File__ | __Running Time__|
| :-------------| :---------------: |
| 1000.json | 1 sec 556 ms |
| 100000.json |6 minutes 1 sec 334 ms |


  
  
## _Algorithmic classes:_
### __MyFrame Class:__
  This class is extends of JFrame and have implements of ActionListener.\
  This class creates the window where the graph will appear.\
  The options:
  
  ```
  Select file:
  • load
  • save
  ```
  ```
  Functions of the Algorithms:
  • isConnected
  • shortestPathDist
  • shortestPath
  • center
  • TSP
  ```
  ```
  Functions on the graph:
  • Remove_node
  • Remove_edge
  ```

  
###  __MyPanel Class:__
This class is extends of JPanel and have implements of MouseInputListener.\
This class implements the full explanation of the actions and how they are actually performed on the graph.\
For example, different response to mouse uses, setting the graph size in the window, maintaining appropriate proportions of the graph when opening the file, and more.\
By select one of the functions under the "Functions of the Algorithms" in the menuBar, can see the implementation of the functions:
  
  __select isConnected:__ A window will pop up stating whether the graph is connected or not.\
  __select shortestPathDist:__  A window will open, in which it will be possible to write down the choice of destination and the source of the nodes. Between them we will get the shortest path (return a numerical answer).\
  __select shortestPath:__  A window will open, in which it will be possible to write down the choice of destination and the source of the nodes. Between them we will get the shortest path (return a string of nodes).\
  __select center:__ A window will pop up stating the center the specific graph.\
  __select TSP:__ A window will open, in which it will be possible to write down the path that you want after clicking `OK`. In every time that you select a node, needs to click `OK`. After finishing selecting the nodes write `EXIT` and then can see the path marked on the graph.
  
  
By select one of the functions under the "Functions on the graph" in the menuBar, can see the implementation of the functions:\
__select Remove_node__ - A window will open, in which it will be possible to write down the choice of which node would you like to delete.\
  __select Remove_edge__ - A window will open, in which it will be possible to write down the choice of which edge would you like to delete (select a src and dest).

  
  
  
  ## _How to run a Graph:_
  By the Ex2 class functions and a jar file, can run the Graph.
  
__Running by the commend line__:\
  At first, place the files where we know its location on the computer. The desired files are: one of the JSON files (no matter which one) and the JAR file that we created specifically for the Ex2 main class.\
Example of registering : 
  ```
  c:\'name'\'name'\'name'>java -jar Ex2.jar G1.json
  ```
 At second, inside the window that opens, by select the function load it brings the option to select any JSON file from your comuter, and then do any function the you want.
  
  
 ```
  External Information:
  
  In the src package can find the all tests for all function in this project.
 ```
  
  

  
  


