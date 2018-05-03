import java.util.ArrayList;

/**
 * A class to represent a state in the search tree, with a field for the path
 * @author John Brockway
 */
public class State {

	/**
	 * The cities visited so far in this path, in order
	 */
	public ArrayList<City> citiesInOrder;

	/**
	 * Constructor specifying the currently visited cities
	 * @param citiesInOrder The cities visited so far in this path, in order
	 */
	public State(ArrayList<City> citiesInOrder) {
		this.citiesInOrder = citiesInOrder;
	}

}