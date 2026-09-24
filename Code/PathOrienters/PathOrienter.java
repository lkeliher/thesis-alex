package PathOrienters;

import java.util.ArrayList;

public abstract class PathOrienter {
	abstract public int[][] orientPathsAndGetLayout(int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList, int[] labels, ArrayList<ArrayList<Integer>> paths, int[] supernode);
}
