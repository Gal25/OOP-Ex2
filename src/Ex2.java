import GUI.MyFrame;
import api.DWGraph;
import api.DWGraph_Algo;
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;

import javax.swing.*;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraph g = new DWGraph();
        DirectedWeightedGraphAlgorithms algo = new DWGraph_Algo();
        algo.init(g);
        algo.load(json_file);
        return algo.getGraph();

    }

    /**
     * This static function will be used to test your implementation
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraph g = new DWGraph();
        DirectedWeightedGraphAlgorithms algo = new DWGraph_Algo();
        algo.init(getGrapg(json_file));
        algo.load(json_file);
        DirectedWeightedGraphAlgorithms ans = algo;
        return ans;
    }

    /**
     * This static function will run your GUI using the json fime.
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     */
    public static void runGUI(String json_file) {
        MyFrame mainFrame = new MyFrame(json_file);
        mainFrame.setTitle("Graph");
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        String file= args[0];

        runGUI(file);
        
    }
}
