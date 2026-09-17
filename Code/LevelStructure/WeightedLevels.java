package LevelStructure;

import java.util.ArrayList;

public class WeightedLevels {
	private ArrayList<Double> weights;
	private ArrayList<ArrayList<Integer>> levels;
	private int n;

	public WeightedLevels() {
		n = 0;
		weights = new ArrayList<Double>();
		levels = new ArrayList<ArrayList<Integer>>();
	}

	public int getNumLevels() {
		return n;
	}

	public double getWeight(int i) {
		return weights.get(i);
	}

	public ArrayList<Integer> getLevel(int i) {
		return levels.get(i);
	}

	public void addNodeToLastLevel(int node) {
		levels.get(levels.size()-1).add(node);
	}

	public void addLevel(double weight) {
		weights.add(weight);
		n++;
		levels.add(new ArrayList<Integer>());
	}
}
