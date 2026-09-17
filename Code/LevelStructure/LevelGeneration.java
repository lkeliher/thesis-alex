package LevelStructure;

import java.util.PriorityQueue;
import java.util.ArrayList;

public class LevelGeneration {
	public LevelGeneration() {}
	
	/**
	 * Generates the weighted levels of graphMatrix rooted at x.
	 *
	 * @return The oregered list of levels, where each level is ordered by node degree. Note that the first (0th) index of each level is reserved for the weight sum of that level.
	 */
	public WeightedLevels generateLevels(int x, int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList) {
		int n = graphMatrix.length;
		double[] prevWeightSum = new double[n];
		PriorityQueue<NodeLevelDegree> pq = new PriorityQueue<NodeLevelDegree>();
		PriorityQueue<NodeLevelDegree> iterationPq = new PriorityQueue<NodeLevelDegree>();

		int cur = x;
		int i = 0;
		while(i < n) {
			ArrayList<Integer> curAdj = adjList.get(cur);
			
			for(Integer ii : curAdj) {
				if (prevWeightSum[ii] == 0 && ii != x) {
					NodeLevelDegree nodeWeightSumPair = new NodeLevelDegree(ii, prevWeightSum[cur] + 1.0 /(double) graphMatrix[cur][ii], adjList.get(ii).size());
					//prevWeightSum[ii] = prevWeightSum[cur] + 1.0/(double)graphMatrix[cur][ii];
					iterationPq.add(nodeWeightSumPair);
				}
			}
			NodeLevelDegree temp = iterationPq.poll();
			while(prevWeightSum[temp.getNode()] != 0 || temp.getNode() == x) {
				temp = iterationPq.poll();
			}
			prevWeightSum[temp.getNode()] = temp.getWeightSum();
			pq.add(temp);
			cur = temp.getNode();
			i++;
		}
		NodeLevelDegree xpair = new NodeLevelDegree(x, 0, adjList.get(x).size());
		pq.add(xpair);

		WeightedLevels levels = new WeightedLevels();
		double curLevel = 0;
		int curNode = x;
		double lastLevel = 0;
		i = 0;
		levels.addLevel(0);
		while(!pq.isEmpty()) {
			NodeLevelDegree curr = pq.poll();
			curLevel = curr.getWeightSum();
			curNode = curr.getNode();
			if (lastLevel != curLevel) {
				i++;
				levels.addLevel(curLevel);
			}
			lastLevel = curLevel;
			levels.addNodeToLastLevel(cur);
		}
		return levels;
	}
}
