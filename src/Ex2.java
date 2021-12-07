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
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraph ans = null;
        // ****** Add your code here ******
        //
        // ********************************
        return ans;
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans = null;
        // ****** Add your code here ******
        //
        // ********************************
        return ans;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        // ****** Add your code here ******
        //
        // ********************************
    }

    public static void main(String[] args){
//        DirectedWeightedGraph g = new DWGraph();
//        DirectedWeightedGraphAlgorithms g_algo = new DWGraph_Algo();
//        g_algo.init(g);
//        g_algo.load("data/G1.json");
//
//        System.out.println(g_algo);
        MyFrame mainFrame = new MyFrame();
        mainFrame.setTitle("Graph");
        mainFrame.setVisible(true);
//        g_algo.save("fd.json");

    }

}