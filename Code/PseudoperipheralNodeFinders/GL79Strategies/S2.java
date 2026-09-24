package PseudoperipheralNodeFinders.GL79Strategies;

import java.util.ArrayList;

public class S2 extends GLStrategy {
	public S2() {}

	/**
	 * Choose the node of minimum degree in the passed level.
	 */
	public ArrayList<Integer> shrink(ArrayList<Integer> level, int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList) {
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		int n = level.size();
		int min = graphMatrix.length + 1;
		toReturn.add(-1);

		for(int i = 0; i < n; i++) {
			int cur = level.get(i);
			if (adjList.get(cur).size() < min) {
				min = adjList.get(cur).size();
				toReturn.clear();
				toReturn.add(cur);
			}
		}
		return toReturn;
	}
}
