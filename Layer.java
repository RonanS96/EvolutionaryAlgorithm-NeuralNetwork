/** A layer can contain many neurons and can have one of 
 *  3 types, Input, Hidden or Output.
 * 
 *  @author Ronan Smith, H00189534, rs6@hw.ac.uk
 *  @date 16.10.17
 */
import java.util.Random;

public class Layer 
{
	private int noOfNeurons;
	private Layer pL;
	private static Neuron[] neurons; 
	double[] outputs;
	private String t;
	
	/** Layer Constructor.
	 *  
	 *  Create a new layer with a previous layer, a specified number of neurons
	 *  and a type; Input, Hidden or Output
	 * 
	 *  @param previous_layer
	 *  @param no_of_neurons
	 *  @param type
	 */
	public Layer(Layer previous_layer, int no_of_neurons, String type)
	{
		pL = previous_layer;
		noOfNeurons = no_of_neurons;
		t = type;
		neurons = new Neuron[noOfNeurons];
		
		double[] inputs = new double[5];
		
		for(int i = 0; i < neurons.length; i++)
		{
			int weight = randomInt(-5,5);  // initialize a random weight between -5 and 5.
			Neuron n1 = new Neuron(inputs, type, weight);
			neurons[i] = n1;
		}
	}
	
	/** Send the input n to each of the neurons in 
	 *  this layer.
	 * 
	 *  @param n
	 */
	public void setInput(double n)
	{
		for(int i = 0; i < neurons.length; i++)
		{
			neurons[i].activate(n, t);
		}
	}
	
	/** Return the number of neurons in the layer
	 * 
	 *  @return noOfNeurons
	 */
	public int size()
	{
		return noOfNeurons;
	}
	
	/** Return the final output of the layer
	 * 	If there are multiple neurons it will pick the 
	 *  one with the highest weight.
	 * 
	 *  @return output
	 */
	public double getOutput()
	{
		outputs = new double[noOfNeurons];
		int[] weights = new int[noOfNeurons];
		int maxWeight = -10; // initial value lower than threshold
		
		for(int i = 0; i < neurons.length; i++)
		{
			outputs[i] = neurons[i].getOutput();
			weights[i] = neurons[i].getWeight();
		}
		
		// return the output of the neuron with the highest weight
		maxWeight = findMax(weights);
		return outputs[maxWeight];
	}
	
	/** Return the type of the layer:
	 * 	Input, Hidden or Output.
	 * 
	 *  @return n
	 */
	public String getType()
	{
		return t;
	}
	
	public int getNoOfNeurons()
	{
		return noOfNeurons;
	}
	
	
	/** Return the layer that was directly before this one.
	 * 
	 *  @return pL
	 */
	public Layer getPreviousLayer()
	{
		if(pL == null)
		{
			// System.out.println("No Previous layer. This is the input layer.");
			return null;
		}
		else
		{
			return pL;
		}
	}
	
	/** Return a random integer value between 'min' and 'max'.
	 *  Copied and edited from: 
	 *  https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
	 *  [Last accessed 20th October 2017]
	 * 
	 * @param min
	 * @param max
	 * @return rN 
	 */
	public static int randomInt(int min, int max)
	{
		Random rand = new Random();
		
		int rN = rand.nextInt((max - min) + 1) + min;
		//System.out.println("TEST: The random integer generated is: " + rN + ".");
		return rN;
	}
	
	/** findMax
	 *  
	 *  Find and return the largest integer value in the array.
	 *  @param weights - the array to be searched.
	 *  
	 */	
	public int findMax(int[] weights)
	{
		int i;
		int max = weights[0];
		int maxIndex = 0;
		for(i = 1; i < weights.length; i++)
		{
			if(weights[i] > max)
			{
				max = weights[i];
				maxIndex = i;
			}
		}
		// return the index of the maximum weight
		return maxIndex;
	}
}
