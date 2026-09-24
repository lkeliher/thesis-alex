package PseudoperipheralNodeFinders.GL79Strategies;

import java.util.ArrayList;

abstract public class GLStrategy {
	abstract public ArrayList<Integer> shrink(ArrayList<Integer> level, int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList);
}
