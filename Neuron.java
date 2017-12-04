import java.util.Random;

/** A neuron object. One of many in a neural network.
 * 
 *  @author Ronan Smith, H00189534, rs6@hw.ac.uk
 *	@date 16.10.17
 */

public class Neuron 
{
	private double[] possible_outputs = {0, 0.5, 1}; // output can be 0, 0.5 or 1
	private String t;
	private double output;
	private int w;
	
	public Neuron(double[] inputs, String type, int weight)
	{
		t = type;
		w = weight;
	}
	
	/** Activate or fire the neuron and return the output.
	 * 
	 * @param input
	 * @return output
	 */
	public double activate(double input, String type)
	{
		output = 0;
		
		if(type.equals("Input")) //simply return the input
		{
			output = input;
			//System.out.println("TEST: Input = " + input + ", recieved at the Input Layer, neuron activated. Output = " + output + ".");
			return output;
		}
		else if(type.equals("Hidden")) 
		{
			int randomInt;
			
			// Bias is added here.  If the input is low it will  
			// output 0 or 0.5 and if it is above 0 it will output either 
			// 0.5 or 1, making it less random.
			if(input <= 0)
			{
				randomInt = randomInt(0,1); // generate a random int between 0 and 1.
			}
			else 
			{
				randomInt = randomInt(1,2); // generate a random int between 1 and 2
			}
			
			output = possible_outputs[randomInt];
			//System.out.println("TEST: Input = " + input + ", recieved at the Hidden Layer, neuron activated. Output = " + output + ".");
			return output;
		}
		else if(type.equals("Output")) // return the output
		{
			output = input;
			//System.out.println("TEST: Input = " + input + ", recieved at the Output Layer, neuron activated. Output = " + output + ".");
			return output;
		}
		else
		{
			System.err.print("Layer name unknown: " + t + ".");
			return -1;
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
		return rN;
	}
	
	/** Set a new weight for this particular neuron.
	 * 
	 *  @param newWeight
	 */
	public void setWeight(int newWeight)
	{
		w = newWeight;
	}
	
	/** Return the current weight of this neuron.
	 * 
	 *  @return
	 */
	public int getWeight()
	{
		return w;
	}	
	
	/** Return the current output value for this neuron.
	 * 
	 *  @return
	 */
	public double getOutput()
	{
		return output;
	}
}
