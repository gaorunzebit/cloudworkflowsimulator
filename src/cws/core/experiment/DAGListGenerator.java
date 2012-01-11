package cws.core.experiment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import org.cloudbus.cloudsim.distributions.ParetoDistr;

 /**
  * Class for generating list of DAG files for experiments.
  * @author malawski
  *
  */

public class DAGListGenerator {
	
	
	/**
	 * Generates list of DAG names (.dag files) to be used with workflows 
	 * from the workflow generator 
	 * https://confluence.pegasus.isi.edu/display/pegasus/WorkflowGenerator
	 * converted to simplified dag format using dax2dag.rb script
	 * It is better to use DAG files, since XML parser tends to be 10x slower.
	 * 
	 * @param name Name prefix of DAG file (e.g. MONTAGE)
	 * @param sizes array of sizes, e.g. {100, 200}
	 * @param sizeCount number of files of each size (usually 20)
	 * @return array of file names (no path)
	 */
	
	public static String[] generateDAGList(String name, int [] sizes, int sizeCount ) {
		
		String dags[] = new String[sizes.length*sizeCount];
		for (int i=0; i<sizes.length; i++) {
			for (int j=0; j< sizeCount; j++) {
				dags[i*sizeCount + j] = name + ".n." + sizes[i] + "." + j + ".dag";
			}
		}
		return dags;
	}
	
	public static String[] generateDAGListPareto(Random seed, String name, int length) {
		ArrayList<String> dags = new ArrayList<String>(length);

		ParetoDistr pareto = new ParetoDistr(seed, 1, 50);
		HashMap<Integer, Integer> distr = new HashMap<Integer, Integer>();
		for (int i = 0; i < length; i++) {
			double d = pareto.sample();
			int n;
			if (d < 100)
				n = 50;
			else if (d > 1000)
				n = 1000;
			else
				n = (int) Math.floor(d / 100) * 100;
			if (!distr.containsKey(n))
				distr.put(n, 1);
			else
				distr.put(n, distr.get(n) + 1);
			System.out.println(d + "\t" + n + "\t");
		}
		Integer[] sizes = distr.keySet().toArray(new Integer[0]);
		Arrays.sort(sizes);
		for (int size : sizes) {
			int count = distr.get(size);
			for (int i = 0; i < count; i++) {
				String dag = name + ".n." + size + "." + i % 20 + ".dag";
				System.out.println(dag);
				dags.add(dag);
			}
		}
		Collections.reverse(dags);
		return dags.toArray(new String[0]);
	}
	
}