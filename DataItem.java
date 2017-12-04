/** This class represents a single record from the dataset being 
 *  used to train the Artificial Nerural Network (ANN.java) class.
 *  A Data Item is made up of three attributes, an integer ID,
 *  a double input and a double expected_output.  The neural network
 *  will guess the output and compare it to the expected output later.
 * 
 *  @author Ronan Smith, H00189534, rs6@hw.ac.uk
 *
 */
public class DataItem 
{
	private int iD;
	private double iP;
	private double eO;
	
	public DataItem(int id, double input, double expected_output)
	{
		iD = id;
		iP = input;
		eO = expected_output;
	}

	/** Return the ID of the data item.
	 *  
	 *  @return iD
	 */
	public int getID()
	{
		return iD;
	}
	
	/** Return the input value for this data item.
	 * 
	 *  @return iP
	 */
	public double getInputValue()
	{
		return iP;
	}
	
	/** Return what the expected output was for this 
	 *  data item.
	 * 
	 *  @return
	 */
	public double getExpectedOutput()
	{
		return eO;
	}
}
