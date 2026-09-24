package NodeContractions;

import java.util.ArrayList;

public class GraphRepresentations {
	private int[][] graphMatrix;
	private ArrayList<ArrayList<Integer>> adjList;
	private int[] supernodeLocations;

	public GraphRepresentations(int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList, int[] supernodeLocations) {
		this.graphMatrix =graphMatrix;
		this.adjList = adjList;
		this.supernodeLocations = supernodeLocations;
	}

	public int[][] getMatrix() {
		return graphMatrix;
	}

	public ArrayList<ArrayList<Integer>> getAdjList() {
		return adjList;
	}
}
