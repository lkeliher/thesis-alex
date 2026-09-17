package InnerSolvers;

import java.util.ArrayList;
import PseudoperipheralNodeFinders.NodeAndLevelStructure;

public abstract class InnerSolver {
	/**
	 * @param r, the chosen pseudoperipheral node.
	 */
	public abstract int[] getLabeling(NodeAndLevelStructure nl, int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList);
}
