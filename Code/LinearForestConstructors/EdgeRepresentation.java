package LinearForestConstructors;

public class EdgeRepresentation implements Comparable<EdgeRepresentation> {
	private int n1;
	private int n2;
	private int weight;

	public EdgeRepresentation(int node1, int node2, int weight){
		this.n1 = node1;
		this.n2 = node2;
		this.weight = weight;
	}

	public int getNode1() {return n1;}
	public int getNode2() {return n2;}
	public int getWeight() {return weight;}

	@Override
	public int compareTo(EdgeRepresentation other) {
		// Larger first
		return other.getWeight() - this.weight;
	}
}
