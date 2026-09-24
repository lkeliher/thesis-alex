import java.util.ArrayList;
import NodeContractions.*;
import PathOrienters.*;
import LinearForestConstructors.*;
import InnerSolvers.*;
import PseudoperipheralNodeFinders.*;
import LevelStructure.*;
import PseudoperipheralNodeFinders.GL79Strategies.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Bandwidth {
	public static void main(String[] args) throws IOException {
		String file = args[0];
		String line;
		int n = Integer.parseInt(args[1]);
		BufferedReader br = new BufferedReader(new FileReader(file));
		int[][] graphMatrix = new int[n][n];
		int i = 0;
		while((line = br.readLine()) != null) {
			String[] tok = line.split(",");
			int ii = 0;
			for(String s: tok) {
				graphMatrix[i][ii++] = Integer.parseInt(s);
			}
			i++;
		}
		i = 0;
		ArrayList<ArrayList<Integer>> adjList = new ArrayList<ArrayList<Integer>>();
		for(; i < n; i++) {
			ArrayList<Integer> cur = new ArrayList<Integer>();
			for (int ii = 0; ii < n; ii++) {
				if(graphMatrix[i][ii] != 0) {
					cur.add(ii);
				}
			}
			adjList.add(cur);
		}
		i = 0;

		long startTime = System.nanoTime();
		LinearForestConstructor lf = new ThresholdLinearForest();
		ArrayList<ArrayList<Integer>> paths = lf.generateLinearForest(graphMatrix, adjList);

		EdgeWeightContraction ewc = new EdgeWeightMultiplicative();
		CoordinateDescent cd = new CoordinateDescent(ewc);
		int[] labels = cd.expand(graphMatrix, adjList, paths);
		int[][] newMatrix = new int[labels.length][labels.length];

		for(; i < labels.length; i++) {
			for (int ii = 0; ii < labels.length; ii++) {
				newMatrix[labels[i]][labels[ii]] = graphMatrix[i][ii];
			}
		}
		i = 0;
		long endTime = System.nanoTime();
		long durationInNanoseconds = (endTime - startTime);  // Total execution time in nano

		long durationInMilliseconds = durationInNanoseconds / 1000000;
		System.out.println("Execution time: " + durationInMilliseconds + " ms");

		for(; i < labels.length; i++) {
			for(int ii = 0; ii < labels.length; ii++) {
				System.out.print(newMatrix[i][ii] + " ");
			}
			System.out.println();
		}

		System.out.println("Bandwidth: " + cd.calculateBandwidth(graphMatrix, labels));
	}
}
