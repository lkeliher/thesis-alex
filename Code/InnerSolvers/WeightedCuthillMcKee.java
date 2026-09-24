package InnerSolvers;

import java.util.ArrayList;
import LevelStructure.*;
import PseudoperipheralNodeFinders.NodeAndLevelStructure;

public class WeightedCuthillMcKee extends InnerSolver {

	@Override
	public int[] getLabeling(NodeAndLevelStructure nl, int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList) {
		int r = nl.getNode();
		WeightedLevels levels = nl.getLevelStructure();
		ArrayList<Integer> curLevel = levels.getLevel(0);
		int n = graphMatrix.length;

		int levelIndex = 1;
		int[] labels = new int[n];
		int realIndex = 0;
		for(int i = 0; i < n; i++) {
			labels[curLevel.get(realIndex++)] = i;
			if(realIndex >= curLevel.size()) {
				realIndex = 0;
				curLevel = levels.getLevel(levelIndex++);
			}
		}
		return labels;
	}

	public ArrayList<Integer> getOrdering(int[] labels) {
    	ArrayList<Integer> ordering = new ArrayList<>();

    	for (int i = 0; i < labels.length; i++) {
    	    ordering.add(i);
    	}

    	ordering.sort((a, b) ->
    	    Integer.compare(labels[a], labels[b])
    	);

    	return ordering;
	}
}
