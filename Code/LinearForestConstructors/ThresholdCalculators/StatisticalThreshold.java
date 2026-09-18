package ThresholdCalculators;

import java.util.ArrayList;
import LinearForestConstructors.EdgeRepresentation;

public class StatisticalThreshold extends ThresholdCalculator{
	@Override
	public int getThreshold(int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList, ArrayList<EdgeRepresentation> edgesSorted) {
		double percentile = 70.0; // TODO: test percentile.
		// Note edges have been sorted in decending order.
		double n = (double) graphMatrix.length;
		int i = (int) (((percentile/100.0) * (n + 1.0)) - 1.0);
		// Because of decending order.
		i = (int) n-i;
		return edgesSorted.get(i).getWeight();
	}
}
