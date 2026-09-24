package PathOrienters;

import java.util.ArrayList;

public CoordinateGradientDescent extends PathOrienter {
	@Override
	public int[][] orientPathsAndGetLayout(int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList, int[] labels, ArrayList<ArrayList<Integer>> paths, int[] supernode, int[][] ogMatrix) {
		int totaln = ogMatrix.length;
		int[][] newGraphMatrix = new int[totaln][totaln];
		ArrayList<ArrayList<Integer>> newAdjList = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < adjList.size(); i++) {
			newAdjList = new ArrayList<Integer>();
		}
		
		for(int i = 0; i < adjList.size(); i++) {
			newAdjList.set(labels[i], adjList.get(i));
		}
	}
}
