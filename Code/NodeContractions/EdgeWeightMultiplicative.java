package NodeContractions;

import java.util.ArrayList;
import java.util.Arrays;

public class EdgeWeightMultiplicative extends EdgeWeightContraction {
	@Override
	public GraphRepresentations contract(int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList, ArrayList<ArrayList<Integer>> paths) {
		// For new graph, allocating is O(n^2), but finding the pseudoperipheral nodes is O(n(e)), so this doesn't increase the overall
		// time complexity.
		int n = graphMatrix.length;

		// oldToNew[v] is which node v becomes after contraction
		int[] oldToNew = new int[n];
		Arrays.fill(oldToNew, -1);

		int newNodeCount = 0;

		// Contract each path
		for (ArrayList<Integer> path : paths) {
    		int contractedNode = newNodeCount++;

	    	for (int v : path) {
        		oldToNew[v] = contractedNode;
	    	}
		}

		// Nodes not belonging to a path remain individual
		for (int v = 0; v < n; v++) {
	    	if (oldToNew[v] == -1) {
        		oldToNew[v] = newNodeCount++;
	    	}
		}


        // Build the contracted weighted graph.
        int[][] newGraphMatrix = new int[newNodeCount][newNodeCount];

		// Recompute edges to contracted nodes.
		for(ArrayList<Integer> path : paths) {
			int bforward = 0;
			int bbackward = 0;
			ArrayList<ArrayList<Integer>> forwardEdges = new ArrayList<ArrayList<Integer>>();
			ArrayList<ArrayList<Integer>> backwardEdges = new ArrayList<ArrayList<Integer>>();
			for(int i = 0; i < path.size(); i++) {
				int a = path.get(i);
				for(int ii = 0; ii < adjList.get(a).size(); ii++) {
					int b = adjList.get(a).get(ii);
					// (i, j, w)
					ArrayList<Integer> curEdge = new ArrayList<Integer>();
					curEdge.add(a);
					curEdge.add(b);
					// Recompute edge weight;
					curEdge.add(graphMatrix[a][b]*(i+1));
					forwardEdges.add(curEdge);
					if (graphMatrix[a][b]*(i+1) > bforward) {bforward = graphMatrix[a][b]*(i+1);}
				}
			}
			for(int i = path.size() - 1; i >= 0; i--) {
				int a = path.get(i);
                for(int ii = 0; ii < adjList.get(a).size(); ii++) {
                    int b = adjList.get(a).get(ii);
                    // (i, j, w)
                    ArrayList<Integer> curEdge = new ArrayList<Integer>();
                    curEdge.add(a);
                    curEdge.add(b);
                    // Recompute edge weight;
                    curEdge.add(graphMatrix[a][b]*(i+1));
					backwardEdges.add(curEdge);
                    if (graphMatrix[a][b]*(i+1) > bbackward) {bbackward = graphMatrix[a][b]*(i+1);}
                }
			}

			// Determine which results in the better local bandwidth.
			// TODO: remember this when implementing coordinate gradient descent.
			ArrayList<ArrayList<Integer>> localBest = bforward > bbackward ? backwardEdges : forwardEdges;
			for(ArrayList<Integer> edge: localBest) {
				int a = edge.get(0);
				int b = edge.get(1);
				int w = edge.get(2);
				
				if (w > newGraphMatrix[oldToNew[a]][oldToNew[b]]) {
					newGraphMatrix[oldToNew[a]][oldToNew[b]] = w;
					newGraphMatrix[oldToNew[b]][oldToNew[a]] = w;
				}
			}
		}

		for (int u = 0; u < n; u++) {
    		for (int v = 0; v < n; v++) {
        		int weight = graphMatrix[u][v];

        		// No edge.
		        if (weight == 0) {
        		    continue;
        		}

        		int newU = oldToNew[u];
        		int newV = oldToNew[v];

		        // Ignore edges entirely inside a contracted node.
        		if (newU == newV) {
            		continue;
        		}

				newGraphMatrix[newV][newU] = newGraphMatrix[newV][newU] > weight ? newGraphMatrix[newV][newU] : weight;
        		newGraphMatrix[newU][newV] = newGraphMatrix[newU][newV] > weight ? newGraphMatrix[newU][newV] : weight;
    		}
		}

		// Construct new adjacency list.
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

		return new GraphRepresentations(newGraphMatrix, newAdjList);
	}
}
