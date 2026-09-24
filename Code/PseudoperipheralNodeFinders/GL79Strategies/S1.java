package PseudoperipheralNodeFinders.GL79Strategies;

import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashSet;

public class S1 extends GLStrategy {
	public S1() {}

	/**
	 * Select a node of minimum degree from each connected component in the subgraph of G created by the nodes in the passed level.
	 */
	public ArrayList<Integer> shrink (ArrayList<Integer> level, int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList) {
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		int numToVisit = level.size();
		int n = numToVisit;

		HashSet<Integer> levelSet = new HashSet<Integer>();
		for(Integer i : level) {
			levelSet.add(i);
		}

		boolean[] visited = new boolean[graphMatrix.length];
		while (numToVisit > 0) {
			Queue<Integer> q = new LinkedList<Integer>();
			int curSmallDegree = graphMatrix.length + 1;
			int smallDegreeNode = -1;
			
			// Find first node not yet visited.
			int i = 0;
			while(visited[level.get(i++)]){}
			i--;
			q.add(level.get(i));

			while(!q.isEmpty()) {
				int cur = q.poll();
				if(!visited[cur]) {
					visited[cur] = true;
					numToVisit--;
					if (adjList.get(cur).size() < curSmallDegree) {
						curSmallDegree = adjList.get(cur).size();
						smallDegreeNode = cur;
					}
					for(Integer ii: adjList.get(cur)) {
						if(!visited[ii] && levelSet.contains(ii)) {
							q.add(ii);
						}
					}
				}
			}
			toReturn.add(smallDegreeNode);
		}
		return toReturn;
	}
}
