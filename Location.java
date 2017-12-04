/** A specific location in the dataset.
 *  Made up of an id, latitude and longitude. 
 * 
 * 	@author Ronan Smith
 *  @date 27.09.17
 *
 */
public class Location 
{
	private int id;
	private double latitude;
	private double longitude;
	
	public Location(int id_no, double lat, double lon)
	{
		id = id_no;
		latitude = lat;
		longitude = lon;
	}
	
	public int getID()
	{
		return id;
	}
	
	public double getLatitude()
	{
		return latitude;
	}
	
	public double getLongitude()
	{
		return longitude;
	}
	
	public void printLocation()
	{
		System.out.println(id + " " + latitude + " " + longitude);
	}
}
