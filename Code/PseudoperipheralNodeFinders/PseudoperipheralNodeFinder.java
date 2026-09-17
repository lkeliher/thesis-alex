package PseudoperipheralNodeFinders;

import java.util.ArrayList;

abstract public class PseudoperipheralNodeFinder {
	public abstract NodeAndLevelStructure findPseudoperipheral(int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList);
}
