package PseudoperipheralNodeFinders;

import java.util.ArrayList;
import LevelStructure.*;
import GL79Strategies.*;

public class GL79NodeFinder extends PseudoperipheralNodeFinder {
	public GL79NodeFinder() {};

	public NodeAndLevelStructure findPseudoperipheral(int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList) {
		// Start with an arbitary node.
		int r = 0; // Why not?
		int n = graphMatrix.length;

		// Generate weighted level structure.
		LevelGeneration lg = new LevelGeneration();
		WeightedLevels levelStructure = lg.generateLevels(r, graphMatrix, adjList);
		
		// Apply one of the three "shrinking strategies" described in George and Liu's 1979 paper.
		// Change the line below to test the other strategies.
		GLStrategy strat = new S1();
		boolean doneYet = false;
		ArrayList<Integer> lastLevel = strat.shrink(levelStructure.getLevel(levelStructure.getNumLevels() - 1), graphMatrix, adjList);

		int i = 0;
		while(!doneYet) {
			int cur = lastLevel.get(i++);
			WeightedLevels curStructure = lg.generateLevels(cur, graphMatrix, adjList);
			if (curStructure.getWeight(curStructure.getNumLevels() - 1) > levelStructure.getWeight(levelStructure.getNumLevels() - 1)) {
				r = cur;
				levelStructure = curStructure;
				lastLevel = strat.shrink(levelStructure.getLevel(levelStructure.getNumLevels() - 1), graphMatrix, adjList);
				i = 0;
			}
			if (i >= lastLevel.size()) {doneYet = true;}
		}
		return new NodeAndLevelStructure(r, levelStructure);
	}
}
