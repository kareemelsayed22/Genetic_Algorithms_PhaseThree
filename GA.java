import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GA {
	
	public static void Fittest(String graph, int size, String hamil, int size_info, FileWriter myWriter) throws IOException {

		FitnessCalc.setSolution(graph);
        // Create an initial population
        Population myPop = new Population(10000, true);
        
        // Evolve our population until we reach an optimum solution
        int generationCount = 0;
        List<Integer> fit = new ArrayList<Integer>();
        
        while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()) {
            generationCount++;
            fit.add(myPop.getFittest().getFitness());
            
            if(fit.size() > 2) {
            	if(fit.get(fit.size() - 1) == fit.get(fit.size() - 2)) {
            		generationCount--;
            		break;
            	}
            }
            
            myPop = Algorithm.evolvePopulation(myPop);
            
        }
        myWriter.write("Size :" + size_info + "\n");
        myWriter.write("Graph: " + hamil + "\n");
        myWriter.write("Fitness : " + fit.toString() + "\n");
        String fittest = myPop.getFittest().toString();
        myWriter.write("Generation: " + generationCount + "\n");
        myWriter.write("Genes:");

        int str_len = graph.length();
	    double d_str_len = str_len;
	    int size1 = (int)Math.sqrt(d_str_len);

	    ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
		
	    for(int i = 0; i < size1; i++) {
	    	ArrayList<Integer> row = new ArrayList<Integer>();
	    	for(int j = 0; j < size1; j++) {
	    		char ch = graph.charAt(i * size1 + j);
		    	int n = Character.getNumericValue(ch);
		    	row.add(n);
	    	}
	    	list.add(row);
	    }
	    
	    myWriter.write(list.toString());
        myWriter.write("\n");
	}
	
	public static void main(String[] args) throws Exception {
		
		File file1 = new File("input1.txt");
		Scanner sc1 = new Scanner(file1);
		
		List<Integer> size_array = new ArrayList<Integer>();
		
		while(sc1.hasNextLine()) {
			size_array.add(Integer.parseInt(sc1.nextLine()));
		}
		
		Random r = new Random();
		
		String[] graph = new String[3];
		String[] hamil = new String[3];
		HamiltonianCycle my_hamil = new HamiltonianCycle();
		
		for(int i = 0; i < 3; i++ ) {
			String out = "";
			int out_len;
			do {
				
				int p, q, n, x, num;
				String get_array;
				String get_graph = "";
				
				for(int j = 0; j < size_array.get(i); j++) {
					
					p = r.nextInt(100);
					q = r.nextInt(100);
					
					n = p * q;
					
					x = 127;
					num = n % (x * x);
					String bits = "%" + size_array.get(i) + "s";
					
					get_array = String.format(bits, Integer.toBinaryString(num)).replaceAll(" ", "0");
					
					if(get_array.length() > size_array.get(i)) {
						get_array = get_array.substring(0, size_array.get(i));
						get_graph = get_graph + get_array;
					}
					else {
						get_graph = get_graph + get_array;
					}
					
				}
				
				graph[i] = get_graph;
				out = my_hamil.hamilton_func(graph[i]);
				out_len = out.length();
				
			}while(out_len == 0);
			hamil[i] = out;
		}
		
		FileWriter myWriter = new FileWriter("output.txt");
		
		Fittest(graph[0], size_array.get(0), hamil[0], 1, myWriter);
		Fittest(graph[1], size_array.get(1), hamil[1], 2, myWriter);
		Fittest(graph[2], size_array.get(2), hamil[2], 3, myWriter);
		
		myWriter.close();

		System.out.println("end");
    }
}
