import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner; 
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;

public class HamiltonianCycle {

	int path[];
	
	boolean isSafe(int v, int graph[][], int path[], int pos) {
		if(graph[path[pos - 1]][v] == 0) 
			return false;
		for(int i = 0; i<pos; i++)
			if(path[i] == v)
				return false;
		return true;
	}
	
	boolean hamCycleUtil(int graph[][], int path[], int pos, int V) {
		if(pos == V) {
			if(graph[path[pos - 1]][path[0]] == 1)
				return true;
			else 
				return false;
		}
		for(int v = 1; v < V; v++) {
			if(isSafe(v, graph, path, pos)) {
				path[pos] = v;
				if(hamCycleUtil(graph, path, pos + 1, V) == true)
					return true;
				path[pos] = -1;
			}
		}
		return false;
	}
	
	String hamCycle(int graph[][], int V) {
		path = new int[V];
		for(int i = 0; i < V; i++)
			path[i] = -1;
		path[0] = 0;
		if(hamCycleUtil(graph, path, 1, V) == false) {
//			System.out.println("\nSolution does not exist");
			return "";
		}
//		printSolution(path, V);
		return Arrays.toString(path);
	}
	
	void printSolution(int path[], int V) {
		System.out.println("Solution Exists: Following is one Hamiltonian Cycle");
		
		for(int i = 0; i < V; i++)
			System.out.print(" " + path[i] + " ");
		System.out.print(" " + path[0] + "\n");
	}
	
	public String hamilton_func(String str_graph){
		
		String output;	    
	    
	    int str_len = str_graph.length();
	    double d_str_len = str_len;
	    int size = (int)Math.sqrt(d_str_len);
	    
	    int [][] graph = new int[size][size];
	    
	    for(int i = 0; i < str_len; i++) {
	    	char ch = str_graph.charAt(i);
	    	int n = Character.getNumericValue(ch);
	    	int rows = i / size;
	    	int cols = i % size;
	    	
	    	graph[rows][cols] = n;
	    }
	    
		HamiltonianCycle hamiltonian = new HamiltonianCycle();
		
		output =  hamiltonian.hamCycle(graph, size);
		
		return output;
	}
}
