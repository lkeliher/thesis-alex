package NodeContractions;

import java.util.ArrayList;

public class GraphRepresentations {
	private int[][] graphMatrix;
	private ArrayList<ArrayList<Integer>> adjList;
	private int[] supernodeLocations;
	private int[] oldToNew;
	private int[] newToOld;

	public GraphRepresentations(int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList, int[] supernodeLocations, int[] oldToNew, int[] newToOld) {
		this.graphMatrix =graphMatrix;
		this.adjList = adjList;
		this.supernodeLocations = supernodeLocations;
		this.oldToNew = oldToNew;
		this.newToOld = newToOld;
	}

	public int[][] getMatrix() {
		return graphMatrix;
	}

	public ArrayList<ArrayList<Integer>> getAdjList() {
		return adjList;
	}

	public int[] getSupernodes() {return supernodeLocations;}

	public int[] getNewNodeLocations() {return oldToNew;}

	public int[] getOldNodeLocations() {return newToOld;}
}
