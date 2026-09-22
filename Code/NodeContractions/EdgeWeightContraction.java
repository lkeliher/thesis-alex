package NodeContractions;

import java.util.ArrayList;

public abstract class EdgeWeightContraction {
	abstract public GraphRepresentations contract(int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList, ArrayList<ArrayList<Integer>> paths);
}
