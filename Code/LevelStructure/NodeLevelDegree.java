package LevelStructure;

/**
 * This class is designed to facilitate the sorting of nodes into the weighted level structure.
 */
public class NodeLevelDegree implements Comparable<NodeLevelDegree> {
    private int node;
    private double weightSum;
	private int degree;

	/**
	 * Creates a new instance of this class.
	 *
	 * @param node, the node this instamce relates to.
	 * @param weightSum, the sum of one over the weights of the edges along some path from another node to the node above.
	 * @param degree, the degree of the node above.
	 */
    public NodeLevelDegree(int node, double weightSum, int degree) {
        this.node = node;
        this.weightSum = weightSum;
		this.degree = degree;
    }

	public int getNode() {return this.node;}

	public double getWeightSum() {return this.weightSum;}

	public int getDegree() {return this.degree;}

	/**
	 * Compares one node to another in order to sort them as required by the weighted level generation.
	 * Nodes with the lower "weight sum" are placed first. In the case two nodes have the same weight sum, the node with the lower degree is placed first.
	 *
	 * @param other, the node this node is being compared against
	 * 
	 * @return -1, 1, or 0 if this node is to be placed before, after, or its placement relative to the other node does not matter.
	 */
    @Override
    public int compareTo(NodeLevelDegree other) {
        return this.weightSum < other.getWeightSum() ? -1 : (this.weightSum > other.getWeightSum() ? 1 : (this.degree < other.getDegree() ? -1 : (this.degree > other.getDegree() ? 1 : 0)));
    }
}

