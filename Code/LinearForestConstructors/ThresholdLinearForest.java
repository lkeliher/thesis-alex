package LinearForestConstructors;

import java.util.Queue;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.LinkedList;
import ThresholdCalculators.*;

public class ThresholdLinearForest extends LinearForestConstructor {
	public ArrayList<ArrayList<Integer>> generateLinearForest(int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList) {
		int n = graphMatrix.length;
		int index = 0;

		PriorityQueue<EdgeRepresentation> pq = new PriorityQueue<EdgeRepresentation>();

		for(int i = 0; i < adjList.size(); i++) {
			ArrayList<Integer> curAdj = adjList.get(i);
			for(int ii = 0; ii<curAdj.size(); ii++) {
				int node2 = curAdj.get(ii);
				// Avoid duplicating edges.
				if (node2 > i) {
					EdgeRepresentation edge = new EdgeRepresentation(i, node2, graphMatrix[i][node2]);
					pq.add(edge);
				}
			}
		}

		ArrayList<EdgeRepresentation> edgesSorted = new ArrayList<EdgeRepresentation>();
		while(!pq.isEmpty()) {
			edgesSorted.add(pq.poll());
		}

		ThresholdCalculator threshCalc = new StatisticalThreshold();
		int threshold = threshCalc.getThreshold(graphMatrix, adjList, edgesSorted);

		ArrayList<ArrayList<Integer>> paths = new ArrayList<ArrayList<Integer>>();
		HashSet<Integer> seen = new HashSet<Integer>();
		while(edgesSorted.get(index).getWeight() >= threshold) {
			EdgeRepresentation curEdge = edgesSorted.get(index++);
			if(!(seen.contains(curEdge.getNode1()) || seen.contains(curEdge.getNode2()))) {
				seen.add(curEdge.getNode1());
				seen.add(curEdge.getNode2());
				paths.add(makePath(curEdge, threshold, seen, adjList, graphMatrix));
			}
		}
		return paths;
	}

	private ArrayList<Integer> makePath (EdgeRepresentation start, int t, HashSet<Integer> seen, ArrayList<ArrayList<Integer>> adjList, int[][] graphMatrix) {
		boolean loop = true;
		int a = start.getNode1();
		int b = start.getNode2();
		
		// For future simplicity, the returned ArrayList will be in path order.
		// In order to manage this efficiently we need to asseble the side off node a and node b separately.
		Deque<Integer> aside = new ArrayDeque<>();
		aside.push(a);
		while(loop) {
			int large = -1;
			int next = -1;
			for(Integer i : adjList.get(a)) {
				if(graphMatrix[i][a] > large) {
					next = i;
					large = graphMatrix[i][a];
				}
			}
			if (large >= t) {
				a = next;
				aside.push(a);
				seen.add(a);
			} else {
				loop = false;
			}
		}
		loop = true;
		Queue<Integer> bside = new LinkedList<Integer>();
		bside.add(b);
		while(loop) {
			int large = -1;
			int next = -1;
			for(Integer i : adjList.get(b)) {
				if(graphMatrix[i][b] > large) {
					next = i;
					large = graphMatrix[i][b];
				}
			}
			if (large >= t) {
				b = next;
				bside.add(b);
				seen.add(b);
			} else {
				loop = false;
			}
		}

		ArrayList<Integer> path = new ArrayList<Integer>();
		// Assemble into one arraylist
		while(!aside.isEmpty()) {
			path.add(aside.pop());
		}
		while(!bside.isEmpty()) {
			path.add(bside.poll());
		}
		return path;
	}
}
