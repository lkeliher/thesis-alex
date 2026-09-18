package NodeContractions;

import java.util.ArrayList;

public class GraphRepresentations {
	private int[][] graphMatrix;
	private ArrayList<ArrayList<Integer>> adjList;

	public GraphRepresentations(int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList) {
		this.graphMatrix =graphMatrix;
		this.adjList = adjList;
	}

	public int[][] getMatrix() {
		return graphMatrux;
	}

	public ArrayList<ArrayList<Integer>> getAdjList() {
		return adjList;
	}
}
