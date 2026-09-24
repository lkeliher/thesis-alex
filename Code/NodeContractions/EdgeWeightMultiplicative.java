package NodeContractions;
import java.util.ArrayList;
import java.util.Arrays;
public class EdgeWeightMultiplicative extends EdgeWeightContraction {
	/** Standard contraction entry point.
	* Defaults every path to its original orientation.
	* The coordinate-descent version should use the overload below.
	*/
	@Override
	public GraphRepresentations contract( int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList, ArrayList<ArrayList<Integer>> paths) {
		boolean[] orientation = new boolean[paths.size()];
		// false = original path orientation
		return contract(graphMatrix, adjList, paths, orientation);
	}

	/** Contract the graph using the supplied orientation for each path.
	*  orientation[i] == false: * paths.get(i) is used in its original direction. 
	* orientation[i] == true: * paths.get(i) is reversed. 
	* The contracted edge weight is: 
	* originalWeight * distanceFromChosenEndpoint
	*/
	public GraphRepresentations contract(int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList, ArrayList<ArrayList<Integer>> paths, boolean[] orientation) {
		int n = graphMatrix.length;
		// oldToNew[v] tells us which contracted node contains v.
		int[] oldToNew = new int[n];
		Arrays.fill(oldToNew, -1);
		// newToOld is only meaningful for nodes that were not contracted into paths.
		int[] newToOld = new int[n];
		Arrays.fill(newToOld, -1);
		int newNodeCount = 0;
		for (ArrayList<Integer> path : paths) {
			if (path == null || path.isEmpty()) {continue;}
			int contractedNode = newNodeCount++;
			for (Integer v : path) {
				oldToNew[v] = contractedNode;
			}
		}
		// Vertices not belonging to a path remain individual nodes.
		for (int v = 0; v < n; v++) {
			if (oldToNew[v] == -1) {
				oldToNew[v] = newNodeCount;
				newToOld[newNodeCount] = v;
				newNodeCount++;
			}
		}
		int[][] newGraphMatrix = new int[newNodeCount][newNodeCount];
		// supernodeLocations contains the original path index for each contracted node. For example: supernodeLocations[0] = 1 means contracted node 0 came from paths.get(0).
		int[] supernodeLocations = new int[newNodeCount];
		Arrays.fill(supernodeLocations, 0);
		int supernodeIndex = 1;
		// Recompute edges incident to each contracted path. Coordinate descent needs to control the orientation globally.
		for (int pathIndex = 0; pathIndex < paths.size(); pathIndex++) {
			ArrayList<Integer> originalPath = paths.get(pathIndex);
			if (originalPath == null || originalPath.isEmpty()) {continue;}
			ArrayList<Integer> path = new ArrayList<>(originalPath);
			if (orientation[pathIndex]) {reverse(path);}
			int contractedNode = oldToNew[path.get(0)];
			supernodeLocations[contractedNode] = supernodeIndex++;
			// Distance from the chosen endpoint is i + 1.
			for (int i = 0; i < path.size(); i++) {
				int a = path.get(i);
				int distance = i + 1;
				for (Integer b : adjList.get(a)) {
					if (b == null) {continue;} 
					// Ignore edges whose other endpoint is in the same contracted path.
					if (oldToNew[a] == oldToNew[b]) {continue;}
					int weight = graphMatrix[a][b] * distance;
					int newA = oldToNew[a];
					int newB = oldToNew[b];
					if (weight > newGraphMatrix[newA][newB]) {
						newGraphMatrix[newA][newB] = weight;
						newGraphMatrix[newB][newA] = weight;
					}
				}
			} 
		}
		// Add all ordinary inter-supernode edges. If multiple original edges collapse into the same pair of contracted nodes, retain the maximum weight.
		for (int u = 0; u < n; u++) {
			for (int v = u + 1; v < n; v++) {
				int weight = graphMatrix[u][v];
				if (weight == 0) {continue;}
				int newU = oldToNew[u];
				int newV = oldToNew[v];
				// Internal path edges disappear after contraction.
				if (newU == newV) {continue;}
				if (weight > newGraphMatrix[newU][newV]) {
					newGraphMatrix[newU][newV] = weight;
					newGraphMatrix[newV][newU] = weight;
				}
			}
		}
		// Construct adjacency list. 
		ArrayList<ArrayList<Integer>> newAdjList = new ArrayList<>();
		for (int i = 0; i < newNodeCount; i++) {
			newAdjList.add(new ArrayList<>());
		}

		for (int u = 0; u < newNodeCount; u++) {
			for (int v = 0; v < newNodeCount; v++) {
				if (newGraphMatrix[u][v] != 0) {
					newAdjList.get(u).add(v);
				}
			}
		}
		return new GraphRepresentations( newGraphMatrix, newAdjList, supernodeLocations, oldToNew, newToOld);
	}

	private void reverse(ArrayList<Integer> list) {
		int left = 0;
		int right = list.size() - 1;
		while (left < right) {
			Integer tmp = list.get(left);
			list.set(left, list.get(right));
			list.set(right, tmp);
			left++;
			right--;
		}
	}
}