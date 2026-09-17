package GL79Strategies;

import java.util.ArrayList;

public class S3 extends GLStrategy {
	public S3() {}

	/**
	 * Choose an arbitrary node from the passed level. (Yes really, that's it)
	 */
	public ArrayList<Integer> shrink(ArrayList<Integer> level, int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList) {
		ArrayList<Integer> toReturn = new ArrayList<Integer>();

		// I could randomly determine the node, but apparently this is equivalent.
		// It is also faster.
		toReturn.add(level.get(0));
		return toReturn;
	}
}
