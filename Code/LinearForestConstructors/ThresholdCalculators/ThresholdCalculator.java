package ThresholdCalculators;

import java.util.ArrayList;
import LinearForestConstructors.EdgeRepresentation;

public abstract class ThresholdCalculator {
	abstract public int getThreshold(int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList, ArrayList<EdgeRepresentation> edgesSorted);
}
