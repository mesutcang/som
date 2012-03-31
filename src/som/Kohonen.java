package som;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

import topology.ConstantFunctionalFactor;
import topology.KohonenNeuron;




public class Kohonen {
	 private int colNumber, rowNumber;
	    private int radius = 0;
	    private KohonenNeuron[] neuronList;
	    ArrayList <String[]> dataList = new ArrayList<String[]>();
	    private double constant;
		private int maxIteration;
		private ConstantFunctionalFactor functionalModel;
	    
	    public String topologyToString() {
	        ArrayList tempList = new ArrayList();
	        String    conn     = "";

	        for (int i = 1; i < colNumber * rowNumber + 1; i++) {
	            tempList = topologyGetConnectedNeurons(i);
	            conn += "Neuron number " + i + " is connected with: " + tempList + "\n";
	        }
	        return conn;
	    }
	    
	    
	    
	    public String networkToString(){
	        String text = "";
	        int networkSize = neuronList.length;
	        for (int i=0; i< networkSize; i++ ){
	            text +="Neuron number "+ (i + 1) + ": " +  neuronList[i];
	            if(i < networkSize-1){
	                text += "\n";
	            }
	        }
	        return text;
	    }
	    
	    public KohonenNeuron[] getNeuronList() {
			return this.neuronList;
		}
	    
	    public ArrayList topologyGetConnectedNeurons(int neuronNumber) {
	        ArrayList connectedNeurons = new ArrayList();

	        if ((neuronNumber < colNumber * rowNumber) && (neuronNumber > 0)){
	            if (neuronNumber - colNumber > 0) {
	                connectedNeurons.add(neuronNumber - colNumber);
	            }

	            if ((neuronNumber - 1 > 0) && ((neuronNumber % colNumber) != 1)) {
	                connectedNeurons.add(neuronNumber - 1);
	            }

	            if ((neuronNumber + 1 <= colNumber * rowNumber)
	                    && ((neuronNumber % colNumber) != 0)) {
	                connectedNeurons.add(neuronNumber + 1);
	            }

	            if (neuronNumber + colNumber <= colNumber * rowNumber) {
	                connectedNeurons.add(neuronNumber + colNumber);
	            }
	        }
	      return connectedNeurons;   
	    }
		public void setMap(int i, int j) {
			 this.rowNumber = i;
		     this.colNumber = j;
			
		}
		public void setNetwork(int weightNumber, double[] maxWeight) {
			
		        int numberOfNeurons = topologyGetNumbersOfNeurons();
		        neuronList = new KohonenNeuron[numberOfNeurons];
		        for (int i=0; i<numberOfNeurons; i++){
		            neuronList[i] = new KohonenNeuron(weightNumber,maxWeight,null);
		        }			
		}
		public int topologyGetNumbersOfNeurons() {
	        return colNumber * rowNumber;
	    }
		public void setConstantFunctionalFactor(double d) {
			this.constant = d;
			
		}
		public void  parseData(String fileName){
	        File file = new File(fileName);
	        String[] tempTable;
	        String[] tempList;
	        int rows = 0;
	        try{
	            FileReader fr = new FileReader(file);
	            BufferedReader input = new BufferedReader(fr);
	            String line;
	           
	            while((line = input.readLine()) != null){
	                rows ++;
	                tempTable = line.split("\t");
	                int tableLenght = tempTable.length;
	                tempList = new String[tableLenght];
	                for(int i = 0; i< tableLenght; i++){
	                    tempList[i] = tempTable[i];
	                }
	                dataList.add(tempList);     
	             }
	            fr.close();
	            
	        }catch(IOException e){
	            System.out.println("Error: " + e);
	        }
	    }
		
		public double getDifferenceArr(String[] a, String[] b) {
			double sum =0;
			for (int i = 0; i < b.length; i++) {
				
			
				sum+=getDifference(a[i], b[i]);
			}
			return sum;
		}
		
		
		//Levenshtein Distance
		public int getDifference(String a, String b)
		{
		   
		    if (a.length() > b.length())
		    {
		    	// Swap:
		    	String x = a;
		    	a = b;
		    	b = x;
		    }

		   
		    int[] mat1 = new int[a.length() + 1];
		    int[] mat2 = new int[a.length() + 1];

		    int i;
		    int j;

		    for (i = 1; i <= a.length(); i++)
		    	mat1[i] = i;

		    mat2[0] = 1;

		    for (j = 1; j <= b.length(); j++)
		    {
		    	for (i = 1; i <= a.length(); i++)
		    	{
		    		int c = (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1);

		    		mat2[i] =
		    			Math.min(mat1[i - 1] + c,
		    			Math.min(mat1[i] + 1, mat2[i - 1] + 1));
		    	}

		    	
		    	int[] x = mat1;
		    	mat1 = mat2;
		    	mat2 = x;

		    	mat2[0] = mat1[0] + 1;
		    }

		    return mat1[a.length()];
		}
		
		public double getDistance(double[] firstVector, double[] secondVector) {
	        double distance = 0;
	        double x = 0, w = 0;
	        double sum = 0;
	        int weightSize = firstVector.length;
	        
	        if(weightSize != secondVector.length)
	            return -1;
	        
	        for(int i=0; i< weightSize; i++){
	            w = firstVector[i]; 
	            x = secondVector[i];
	            sum += (x - w) *( x - w);
	        }
	        
	        distance = Math.sqrt(sum);
	        return distance;
	    }
		public void setLearningFunction(int maxIteration,
				ConstantFunctionalFactor constantFactor) {
			this.maxIteration = maxIteration;
			this.functionalModel = constantFactor;
			
		}
		public void learn() {
			int bestNeuron = 0;
	        String[] vector;
	        
	        int dataSize = getDataSize();
	        for (int i=0; i< maxIteration; i++){
	            
	            //    System.out.println("Iteration number: " + (i + 1));
	            
	            for(int j= 0; j<dataSize; j++){
	                vector = getData(j);
	                bestNeuron = getBestNeuron(vector);
	                
	                //    System.out.println("Best neuron number: " + (bestNeuron + 1));
	           
	                changeNeuronWeight(bestNeuron, vector, i);
	            }
	        }
			
		}
		 public int getDataSize(){
		        return dataList.size();
		    }
		 public String[] getData(int index){
		        return dataList.get(index);
		    }
		 protected int getBestNeuron(String[] vector){
			   String [] temp;
		       
		        double distance, bestDistance = -1;
		        int networkSize = topologyGetNumbersOfNeurons();
		        int bestNeuron = 0;
		        for(int i=0; i< networkSize; i++){
		            temp = getData(i);
		          
		            distance = getDifferenceArr(temp, vector);
		            if((distance < bestDistance) || (bestDistance == -1)){
		                bestDistance = distance;
		                bestNeuron = i;
		            }
		            
		        }
		        return bestNeuron;
		    }
		 public KohonenNeuron getNeuron(int neuronNumber) {
		        return neuronList[neuronNumber];
		    }
		 
		 protected void changeNeuronWeight(int neuronNumber, String[] vector, int iteration){
		        double[] weightList = getNeuron(neuronNumber).getWeight();
		        int weightNumber = weightList.length;
		        double weight;
		             
		           
		        
		        for (int i=0; i<weightNumber; i++){
		            weight = weightList[i];
		            weightList[i] += functionalModel.getValue(iteration) * getDifferenceArr(vector, getData(neuronNumber));
		        }
		       
		        
		    }
}
