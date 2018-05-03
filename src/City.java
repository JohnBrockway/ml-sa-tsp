/**
 * A class to represent a city
 * @author John Brockway
 */
public class City {

	/**
	 * The name of the City (should be unique)
	 */
	public String name;
	/**
	 * The x-coordinate of the City on a Euclidean plane
	 */
	public double x;
	/**
	 * The y-coordinate of the City on a Euclidean plane
	 */
	public double y;

	/**
	 * Constructor with all fields specified
	 * @param name The name of the City (should be unique)
	 * @param x The x-coordinate of the City on a Euclidean plane
	 * @param y The y-coordinate of the City on a Euclidean plane
	 */
	public City (String name, double x, double y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
}