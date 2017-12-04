/** Biologically Inspired Computation. Coursework 1, Part 1B.
 *  Function approximation using a Multiplayer Perceptron (MLP) 
 *  Artificial Neural Network (ANN).
 * 
 * 	Requirements: This program must implement a MLP with 
 *  one or two neurons in the first layer, one hidden layer 
 *  with 5 neurons and one neuron at the output layer. A dataset
 *  should then be constructed to train the ANN.
 *  
 *  I have chosen the 'Unit Step' activation function for this 
 *  neural network. The finite set of tuples of possible inputs and outputs
 *  is as follows, where the left hand side of the tuple is the 
 *  input and the right hand side is the output and the value for x 
 *  can be any double number:
 *  
 *  {(x < 0, 0), (x = 0, 0.5), (x > 0, 1)}
 * 
 *  @author Ronan Smith, H00189534, rs6@hw.ac.uk
 *  @date   16.10.17
 */

import java.io.BufferedReader;
import java.io.FileReader;

public class ANN 
{
	private static double input;
	private static double expected_output;
	private static String DATASET = "ANNTrainer.txt";
	private static int MAX_NO_OF_DATA_ITEMS = 10;
	private static DataItem[] iovalues = new DataItem[MAX_NO_OF_DATA_ITEMS];
	private static int noOfDataItems = 0;
	private static int correct_answers = 0;
	private static int incorrect_answers = 0;
	private static double[] errorRate = new double[MAX_NO_OF_DATA_ITEMS];
	
	/** Calculate the Mean Squared Error (MSE).
	 * 
	 *  The MSE is the average error from all points.
	 * 
	 *  @return
	 */
	public static double calculateMSE()
	{
		double MSE = 0;
		
		for(int i = 0; i < MAX_NO_OF_DATA_ITEMS; i++)
		{
			MSE = MSE + errorRate[i];
		}
		MSE = MSE/MAX_NO_OF_DATA_ITEMS;
		
		return MSE;
	}
	
	/** Read the text file containing the dataset.
	 *  At each line, create a new Data Item instance with an ID
	 *  number, an input value and an expected output.
	 */
	public static void readDataset()
	{
		BufferedReader br = null;
		String line = null;
		
		try
		{
			br = new BufferedReader(new FileReader(DATASET));
			
			while(!((line = br.readLine()).equals("EOF")))
			{
				if(line.contains("#")) 
				{
					continue; //skip comments at top of file
				}
				else
				{
					String[] parts = line.split(" ");
					
					int id 	  = Integer.parseInt(parts[0]);
					double ip = Double.parseDouble(parts[1]);
					double op = Double.parseDouble(parts[2]);
					
					DataItem dI = new DataItem(id, ip, op);
					iovalues[noOfDataItems] = dI;
					noOfDataItems++;
				}
			}
		}
		catch(Exception e)
		{
		    System.err.print("There has been a problem reading the dataset: ");
			System.err.println(e);
			System.exit(0);
		}
	}
	
	/** Main Method.
	 * 
	 *  1. Create the 3 layers.
	 *  2. Read in the dataset of training values.
	 *  3. Create 'DataItem' for each line in the dataset.
	 *  4. Return output of ANN and print if it is the correct answer or not.
	 * 
	 *  @param args
	 */
	public static void main(String[] args)
	{
		int noOfLayers = 0;
		
		System.out.println("Creating the 3 layers:");
		
		// Create the 3 layers.
		Layer i  = new Layer(null,  1, "Input"); // Input layer with 1 neuron
		noOfLayers++;
		Layer hl = new Layer(i, 5, "Hidden");  // Hidden layer with 5 neurons 
		noOfLayers++;
		Layer o  = new Layer(hl, 1, "Output"); // Output layer with 1 neuron
		noOfLayers++;
		
		System.out.println("The neural network has been created with " + noOfLayers + " layers.");
		System.out.println("The layers are : " + i.getType() + " of size " + i.size() + ", "
								+ hl.getType() + " of size " + hl.size() + ", " +
								"and " + o.getType() + " of size " + o.size() + ".");
		
		System.out.println(" -> " + o.getPreviousLayer().getPreviousLayer().getType() + " -> "+ o.getPreviousLayer().getType() + " -> " +  o.getType() + " -> ");
		
		
		readDataset();
		System.out.println();
		System.out.println("TEST: Data items in the list are:");
		for(int j = 0; j < iovalues.length; j++)
		{
			System.out.println("ID: " + iovalues[j].getID() + " INPUT: " + iovalues[j].getInputValue() + " EXPECTED OUTPUT: " + iovalues[j].getExpectedOutput());
		}
		
		System.out.println();
				
		/* Pass multiple double values through the network. */
		for(int j = 0; j < iovalues.length; j++)
		{
			input = iovalues[j].getInputValue();
			expected_output = iovalues[j].getExpectedOutput();
		
			i.setInput(input);
			hl.setInput(i.getOutput());
			o.setInput(hl.getOutput());
			
			double output = o.getOutput(); 
			
			System.out.println();
			System.out.print("Original Input = " + input + ". Expected output = " + expected_output + ". Actual output = " + output + ". ");
			double outputDifference = expected_output - output;
			errorRate[j] = outputDifference;
			if(output == expected_output)
			{
				System.out.println("Correct answer.");
				correct_answers++;
			}
			else
			{
				System.out.println("Incorrect answer.");
				incorrect_answers++;
			}
			
			System.out.println("----------------------------");
		}
		
		System.out.println("" + correct_answers + " out of " + (correct_answers + incorrect_answers) + " outputs were correct.");
		System.out.println("The mean squared error was: " + calculateMSE());
		
		System.out.println();
		System.out.println("Program Terminated.");
	}
}
