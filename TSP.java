/** Biologically Inspired Computation. Coursework 1, Part 1A.
 *  The Travelling Salesman Problem, solved using an Evolutionary
 *  Algorithm. For use on datasets from the following URL:
 *  http://www.math.uwaterloo.ca/tsp/world/countries.html [Last Accessed: 4/10/17].
 *  
 *  To use this code on a different dataset, the DATASET, MAX_NO_OF_CITIES 
 *  and OPTIMAL_TOUR fields have to be changed. The MAX_NO_OF_GENERATIONS
 *  field can also be changed for testing purposes
 *  
 *  @author Ronan Smith, H00189534, rs6@hw.ac.uk
 *  @date   01.11.17
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

public class TSP 
{
	private static String DATASET = "data.txt";
	private static int MAX_NO_OF_GENERATIONS = 2000;
	private static int OPTIMAL_TOUR = 206171;
	private static int noOfCities = 0;
	private static int MAX_NO_OF_CITIES = 28000;
	private static double currentShortest = 0.0;
	private static double totalDistance = 0.0;
	private static Location[] locations = new Location[MAX_NO_OF_CITIES];
	private static int[] orderVisited = new int[MAX_NO_OF_CITIES];
		
	
	/** Great Circle Distance to work out cost.
	 *  Copied and modified from: http://introcs.cs.princeton.edu/java/12types/GreatCircle.java.html
	 * 	[Last accessed: 27/09/2017]
	 * 
	 * @param i - the index of the start location
	 * @param j - the index of the location to be reached 
	 * @param l - the array containing all the locations.
	 */
	public static double TSP_Cost(int i, int j, Location[] l)
	{
		double x1 = Math.toRadians(l[i].getLatitude());
        double y1 = Math.toRadians(l[i].getLongitude());
        double x2 = Math.toRadians(l[j].getLatitude());
        double y2 = Math.toRadians(l[j].getLongitude());

       
       /* Computed using Haverside formula */
        double a = Math.pow(Math.sin((x2-x1)/2), 2)
                 + Math.cos(x1) * Math.cos(x2) * Math.pow(Math.sin((y2-y1)/2), 2);

        // great circle distance in radians
        double angle2 = 2 * Math.asin(Math.min(1, Math.sqrt(a)));

        // convert back to degrees
        angle2 = Math.toDegrees(angle2);

        // each degree on a great circle of Earth is 60 nautical miles
        double distance2 = 60 * angle2;

        return distance2;
	}
	
	/** Swap two elements at indexes a and b in an array arr. 
	 *  
	 *  @param a - the first index to be swapped
	 *  @param b - the index to be swapped with the first.
	 *  @param arr - the array in which these indexes exist.
	 *  @return arr - the array with indexes a and be swapped.
	 */
	public static int[] swap(int a, int b, int[] arr)
	{
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
		
		return arr;	
	}
	
	/** Traverse the cities a second time, only in a different order.
	 *  This is done by randomly swapping cities in the order of traversal.
	 */
	public static void mutateAndTraverse()
	{
		int no_of_swaps = 200;
		double sD = 0.0;
		Random r = new Random();
		int[] n = new int[no_of_swaps];
		int[] m = new int[no_of_swaps];
		
		for(int i = 0; i < no_of_swaps; i++)
		{
			n[i] = r.nextInt(orderVisited.length - 1) + 0;
			m[i] = r.nextInt(orderVisited.length - 1) + 0;
		
			swap(n[i], m[i], orderVisited); //swap two of the cities in the order traversed.
		}
		
		for(int i = 0; i < noOfCities-1; i++)
		{
			sD = (sD + TSP_Cost(orderVisited[i], orderVisited[i+1], locations)/100);		
		}
		
		if(sD < currentShortest)
		{
			currentShortest = sD;
		}
	}
	
	/** Travel through all the locations in the list from a start
	 *  index.
	 * 
	 * @param start - the start index 
	 * @return td - the total distance achieved by traversing the locations.
	 */
	public static double traverseLocations(int start)
	{
		double td = 0; // total distance, initially zero
		
		if(start == 0)
		{
			for(int i = start; i < (noOfCities - 1); i++)
			{
				td = td + TSP_Cost(i, i+1, locations);
				orderVisited[i] = i;
			}
		}
		else
		{
			int initialStart = start;
			for(int i = 0; i != start; i++)
			{
				td = td + TSP_Cost((start % locations.length), ((start % locations.length) + 1), locations);
				if(start == (noOfCities - 2))
				{
					start = 0;
				}
				else
				{
					if(start == (initialStart - 1))
					{
						break;
					}
					else
					{
						orderVisited[i] = start;
						start++;
					}
					
				}
			}
		}	
		return td;
	}
	
	/** Read the text file containing the dataset.
	 *  Add each line to an instance of a location and break it up into 3 parts;
	 *  An id, a latitude and a longitude value.
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
				if(line.contains("#")) //skip comments at top of file
				{
					continue;
				}
				else
				{
					String[] parts = line.split(" ");
					
					int id = Integer.parseInt(parts[0]);
					double la = Double.parseDouble(parts[1]);
					double lo = Double.parseDouble(parts[2]);
					
					locations[noOfCities] = new Location (id, la, lo);
					noOfCities++;
				}
			}
		}
		catch(Exception e)
		{
			System.err.println(e);
			System.exit(0);
		}
	}
	
	/** Main method. Carries out the following steps.
	 *  1. Reads in dataset to create an array of Location objects.
	 *  2. Traverses the locations to create an initial distance totalDistance
	 *     and an initial child - orderVisited.
	 *  3. Mutates the child to change the order the locations are visited in.
	 *  4. Saves the new distance if it is lower.
	 *  5. Repeats 3 and 4 for MAX_NO_OF_MUTATIONS.
	 *  6. Returns some interesting output data and prints it to the console.
	 * 
	 *  @param args
	 */
	public static void main(String[] args)
	{
		double start = System.currentTimeMillis();
		totalDistance = 0;
		currentShortest = 0;
		readDataset();
		
		System.out.println("Finding Distance between all locations:");
		totalDistance = traverseLocations(0);
		currentShortest = totalDistance;
		System.out.println("The total distance between all points is: " + totalDistance/100 + " nautical miles.");
	
		System.out.println();
			
		for(int i = 0; i < MAX_NO_OF_GENERATIONS; i++)
		{
			mutateAndTraverse();
		
			if(currentShortest != 0.0)
			{
				System.out.println(currentShortest + " nautical miles.");
			}
		}
		
		double finish = System.currentTimeMillis();
		double finaltime = finish - start;
		
		if(currentShortest < totalDistance)
		{
			System.out.println("New Shortest Distance: " + currentShortest + " nautical miles.");
		}
			
		System.out.println();
		System.out.println("The program took " + finaltime + "ms to complete. ");
		System.out.println("The number of locations is " + noOfCities + ".");
		
		if((100 - (currentShortest/totalDistance)*100) > 0.0)
		{
			totalDistance = totalDistance/100;
			double percentageChange = ((currentShortest/totalDistance)*100);
			System.out.println("Original Solution: " + totalDistance + ". Final solution: " + currentShortest + ".");
			System.out.println("Solution improved by: " + (100-percentageChange) + "% in " + MAX_NO_OF_GENERATIONS + " generations.");
			if(currentShortest == OPTIMAL_TOUR)
			{
				System.out.println("This algorithm achieved the optimal tour of " + OPTIMAL_TOUR + " nautical miles.");
			}
			else
			{
				System.out.println("This evolution missed the optimal tour value by " + (currentShortest - OPTIMAL_TOUR) + " nautical miles.");
			}
		}
		else
		{
			System.out.println("No better results were found. The solution was not improved."); 
		}
		
		System.out.println();
		System.out.println("Program Terminated.");
	}
}

