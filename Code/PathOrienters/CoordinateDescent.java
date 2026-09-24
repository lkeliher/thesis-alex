package PathOrienters;

import java.util.ArrayList;
import java.util.Collections;

import InnerSolvers.WeightedCuthillMcKee;
import LevelStructure.*;
import NodeContractions.*;
import PseudoperipheralNodeFinders.*;

public class CoordinateDescent {

    private final EdgeWeightContraction contraction;
    private final WeightedCuthillMcKee solver;

    public CoordinateDescent(EdgeWeightContraction contraction) {
        this.contraction = contraction;
        this.solver = new WeightedCuthillMcKee();
    }


    /**
     * Expands the paths using coordinate descent.
     *
     * Returns: labels[v] = final position of original vertex v.
     */
    public int[] expand(int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList, ArrayList<ArrayList<Integer>> paths) {
        int numberOfPaths = paths.size();

        boolean[] orientation = new boolean[numberOfPaths];

        // Start with every path in its original orientation.
        int[] currentLabels = evaluate(graphMatrix, adjList, paths, orientation);

        long currentBandwidth = calculateBandwidth(graphMatrix, currentLabels);

        boolean improved = true;

        while (improved) {
            improved = false;
            for (int p = 0; p < numberOfPaths; p++) {
                // A path of length 0/1 has no meaningful orientation.
                if (paths.get(p).size() <= 1) {
                    continue;
                }

                // Try reversing this coordinate.
                orientation[p] = !orientation[p];

                int[] candidateLabels = evaluate(graphMatrix, adjList, paths, orientation);

                long candidateBandwidth = calculateBandwidth(graphMatrix, candidateLabels);

                if (candidateBandwidth < currentBandwidth) {
                    // Keep the reversal.
                    currentBandwidth = candidateBandwidth;
                    currentLabels = candidateLabels;
                    improved = true;
                } else {
                    // Revert the coordinate.
                    orientation[p] = !orientation[p];
                }
            }
        }

        return currentLabels;
    }


    private int[] evaluate(int[][] graphMatrix, ArrayList<ArrayList<Integer>> adjList, ArrayList<ArrayList<Integer>> paths, boolean[] orientation) {
        // Construct the contracted graph using the current orientation vector.
        GraphRepresentations contracted = contraction.contract(graphMatrix, adjList, paths, orientation);

        int[][] contractedMatrix = contracted.getMatrix();
        ArrayList<ArrayList<Integer>> contractedAdjList = contracted.getAdjList();
        GL79NodeFinder gl = new GL79NodeFinder();

        NodeAndLevelStructure nl = gl.findPseudoperipheral(contractedMatrix, contractedAdjList);

        int[] contractedLabels = solver.getLabeling(nl, contractedMatrix, contractedAdjList);

        ArrayList<Integer> contractedOrder = invertLabels(contractedLabels);
        return expandOrdering(graphMatrix.length, paths, orientation, contractedOrder, contracted.getOldNodeLocations());
    }


    private ArrayList<Integer> invertLabels(int[] labels) {
        ArrayList<Integer> order = new ArrayList<>();

        for (int i = 0; i < labels.length; i++) {
            order.add(i);
        }

        order.sort((a, b) -> Integer.compare(labels[a], labels[b]));

        return order;
    }


    private int[] expandOrdering(int n, ArrayList<ArrayList<Integer>> paths, boolean[] orientation, ArrayList<Integer> contractedOrder, int[] newToOld) {
        int[] labels = new int[n];

        int position = 0;

        for (int contractedNode : contractedOrder) {
            if (contractedNode < paths.size()) {
                ArrayList<Integer> path = new ArrayList<>(paths.get(contractedNode));

                if (orientation[contractedNode]) {
                    Collections.reverse(path);
                }
                for (int v : path) {
                    labels[v] = position++;
                }

            } else {
                int originalVertex = newToOld[contractedNode];
                labels[originalVertex] = position++;
            }
        }
        return labels;
    }


    private long calculateBandwidth(int[][] graphMatrix, int[] labels) {
        long bandwidth = 0;
        int n = graphMatrix.length;

        for (int u = 0; u < n; u++) {
            for (int v = u + 1; v < n; v++) {
                int weight = graphMatrix[u][v];

                if (weight == 0) {
                    continue;
                }

                long value = (long) weight * Math.abs(labels[u] - labels[v]);

                bandwidth = Math.max(bandwidth, value);
            }
        }

        return bandwidth;
    }
}