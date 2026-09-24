package LevelStructure;

import java.util.PriorityQueue;
import java.util.ArrayList;

public class LevelGeneration {
	public LevelGeneration() {}
	
	/**
	 * Generates the weighted levels of graphMatrix rooted at x.
	 *
	 * @return The ordered list of levels, where each level is ordered by node degree, and the list of weight sums corresponding with those levels.
	 */
	public WeightedLevels generateLevels(int x, int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList) {
		int n = graphMatrix.length;
		double[] distance = new double[n];
		boolean[] visited = new boolean[n];

		for (int i = 0; i < n; i++) {
			distance[i] = Double.POSITIVE_INFINITY;
		}

		PriorityQueue<NodeLevelDegree> pq = new PriorityQueue<>();
		distance[x] = 0.0;

		pq.add(new NodeLevelDegree(x, 0.0, adjList.get(x).size()));
		ArrayList<NodeLevelDegree> nodes = new ArrayList<>();

		while (!pq.isEmpty()) {
			NodeLevelDegree current = pq.poll();

			int u = current.getNode();

			if (visited[u]) {
				continue;
			}

			visited[u] = true;
			nodes.add(current);

			for (Integer v : adjList.get(u)) {

				if (visited[v]) {
					continue;
				}

				double edgeCost =
					1.0 / (double) graphMatrix[u][v];

				double newDistance =
					distance[u] + edgeCost;

				if (newDistance < distance[v]) {
					distance[v] = newDistance;

					pq.add(new NodeLevelDegree(
						v,
						newDistance,
						adjList.get(v).size()
					));
				}
			}
		}

		WeightedLevels levels = new WeightedLevels();
		double lastDistance = Double.NaN;
		for (NodeLevelDegree node : nodes) {
			double d = node.getWeightSum();

			if (Double.isNaN(lastDistance) || d != lastDistance) {
				levels.addLevel(d);
				lastDistance = d;
			}

			levels.addNodeToLastLevel(node.getNode());
		}

		return levels;
	}
}
