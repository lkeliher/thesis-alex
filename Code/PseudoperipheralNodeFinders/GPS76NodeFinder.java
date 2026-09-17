package PseudoperipheralNodeFinders;

import java.util.ArrayList;
import java.util.PriorityQueue;
import LevelStructure.*;

public class GPS76NodeFinder extends PseudoperipheralNodeFinder {
	public GPS76NodeFinder() {}
	
	@Override
	public NodeAndLevelStructure findPseudoperipheral(int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList) {
		// Choose a node r of minimum degree.
		int n = graphMatrix.length;
		int r = 0, rdegree = adjList.get(0).size();
		for(int i = 1; i < n; i++) {
			int curdegree = adjList.get(i).size();
			if (curdegree < rdegree) {r = i; rdegree = curdegree;}
		}

		// Create weighted level structure. The nodes within the levels are already sorted by increasing degree.
		LevelGeneration lg = new LevelGeneration();
		WeightedLevels levelStructure = lg.generateLevels(r, graphMatrix, adjList);

		// For each x in the last level, generate its level structure and determine l(x).
		// If l(x) > l(r), set r = x and continue with the new level structure.
		double lr = levelStructure.getWeight(levelStructure.getNumLevels()-1);
		int i = 0;
		boolean notDone = true;
		while(notDone) {
			int x = levelStructure.getLevel(levelStructure.getNumLevels()-1).get(i++);
			WeightedLevels tempLevels = lg.generateLevels(x, graphMatrix, adjList);
			double lx = tempLevels.getWeight(tempLevels.getNumLevels()-1);
			if (lx > lr) {
				r = x;
				levelStructure = tempLevels;
				lx = lr;
				i = 0;
			}
			if (i >= levelStructure.getLevel(levelStructure.getNumLevels() - 1).size()) {notDone = false;}
		}
		return new NodeAndLevelStructure(r, levelStructure);
	}
}
