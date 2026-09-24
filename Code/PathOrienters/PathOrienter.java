package PathOrienters;

import java.util.ArrayList;
import NodeContractions.GraphRepresentations;

public abstract class PathOrienter {
	abstract public ArrayList<Integer> expandPaths(int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList, ArrayList<ArrayList<Integer>> paths, ArrayList<Integer> contractedOrder);
}
