package PseudoperipheralNodeFinders;

import LevelStructure.WeightedLevels;

public class NodeAndLevelStructure {
    private int node;
    private WeightedLevels levels;

    public NodeAndLevelStructure(int node, WeightedLevels levels) {
        this.node = node;
        this.levels = levels;
    }

    public WeightedLevels getLevelStructure() {
        return levels;
    }

    public int getNode() {
        return node;
    }
}