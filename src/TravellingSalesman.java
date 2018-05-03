import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Provides a solution to the travelling salesman problem using Simulated Annealing
 * @author John Brockway
 */
public class TravellingSalesman {

	/**
	 * The main function which converts the input into a form usable by the search method
	 * @param args Contains the file of the problem instance in a specified form of "#-of-cities\nName x-Coordinate yCoordinate\nName x-Coordinate yCoordinate etc.". 
	 * The second argument is the annealing schedule to use, from 1-3.
	 */
	public static void main(String[] args) {
		City[] cities = new City[0];
		try {
			BufferedReader input = new BufferedReader(new FileReader(args[0]));
			int numberOfCities = Integer.parseInt(input.readLine());
			cities = new City[numberOfCities];
			for (int i = 0 ; i < numberOfCities ; i++) {
				String line = input.readLine();
				String[] lineSplit = line.split(" ");
				cities[i] = new City(lineSplit[0], Double.parseDouble(lineSplit[1]), Double.parseDouble(lineSplit[2]));
			}
			input.close();
		} catch (IOException io) {
			io.printStackTrace();
		}

		// If there is only one city in the input, just output that city. This saves some bounds checking in the search proper
		if (cities.length == 1) {
			System.out.println(cities[0].name);
		}
		else {		
			State result = simulatedAnnealing(cities, Integer.parseInt(args[1]));

			// Output formatted path
			for (int i = 0 ; i < result.citiesInOrder.size() ; i++) {
				System.out.print(result.citiesInOrder.get(i).name);
				System.out.print(":");
				if (i == result.citiesInOrder.size() - 1) {
					System.out.println(result.citiesInOrder.get(0).name);
				}
			}
		}
	}

	/**
	 * The implementation of Simulated Annealing
	 * @param cities A list of City that represents every single city in the problem
	 * @param schedule The annealing schedule to use
	 * @return A complete State, with all nodes included in the path (not necessarily optimal, an estimation based on annealing schedule used)
	 */
	public static State simulatedAnnealing(City[] cities, int schedule)
	{
		// Create an arbitrary start state; here, the path is the order of input
		ArrayList<City> start = new ArrayList<City>();
		for(int i = 0 ; i < cities.length ; i++) {
			start.add(cities[i]);
		}
		State state = new State(start);
		
		// Initialize the T value for the annealing schedule
		double t = 100;
		
		int iteration = 0;
		
		// An arbitrary stopping point where it is extremely unlikely that any regressive moves will be made
		while (t > 0.05) {
			
			iteration++;
			
			// Find two distinct indices representing cities to swap in the tour
			int firstRandomCity = (int)(Math.random() * cities.length);
			int secondRandomCity = (int)(Math.random() * cities.length);
			while (secondRandomCity == firstRandomCity) {
				secondRandomCity = (int)(Math.random() * cities.length);
			}
			
			ArrayList<City> newTour = new ArrayList<City>();
			for (int j = 0 ; j < state.citiesInOrder.size() ; j++) {
				// For cities at the indices, swap them in the tour
				// For example, in the tour {A, B, C, D, E, F, G}, with indices 1 and 4, the values
				// 	B and E would be reversed, so that the new tour is {A, E, C, D, B, F, G}
				if (j == firstRandomCity){
					newTour.add(state.citiesInOrder.get(secondRandomCity));
				}
				else if (j == secondRandomCity){
					newTour.add(state.citiesInOrder.get(firstRandomCity));
				}
				else {
					// For values outside the specified values, keep the same ordering
					newTour.add(state.citiesInOrder.get(j));
				}
			}
			
			// Finds the difference between the current state and the current one; since we want low tour costs,
			// 	if the new tour is less than the current one (better) then the delta is increasingly large, and otherwise
			// 	it is increasingly negative, as desired for the probability function
			double deltaCost = getPathCost(state.citiesInOrder) - getPathCost(newTour);
			
			if (deltaCost > 0) {
				// If this is an improvement, definitely take this to be the new state
				state = new State(newTour);
			}
			else {
				// Otherwise, take this to be the new state with probability p
				double p = Math.pow(Math.E, deltaCost/t);
				if (Math.random() < p) {
					state = new State(newTour);
				}
			}
			
			// Find the new cooled value of t based on some annealing schedule	
			if (schedule == 1) {
				t = annealingSchedule1(t);
			}
			else if (schedule == 2) {
				t = annealingSchedule2(t);
			}
			else {
				t = annealingSchedule3(iteration);
			}
			
		}

		return state;
	}


	/**
	 * Gets the Euclidean distance between two points on a 2D plane represented by City objects
	 * @param a A City
	 * @param b A City
	 * @return The Euclidean distance between a and b
	 */
	public static double getEuclideanDistance(City a, City b) {
		return Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2));
	}

	/**
	 * Gets the total cost for a path, represented by a State object
	 * @param path The current state of the path
	 * @return The total cost of the path contained in state
	 */
	public static double getPathCost(ArrayList<City> path) {
		double cost = 0;

		for (int i = 0 ; i < path.size() ; i++) {
			if (i == path.size() - 1) {
				// For the last element in the path, calculate the distance back to the start of the tour
				cost += getEuclideanDistance(path.get(i), path.get(0));
			}
			else {
				cost += getEuclideanDistance(path.get(i), path.get(i+1));
			}
		}
		
		return cost;
	}
	
	/**
	 * Annealing Schedule 1: T decreases in a linear fashion by 1 each iteration
	 * @param t The current temperature
	 * @return The temperature for the next iteration
	 */
	public static double annealingSchedule1(double t) {
		return t-1;
	}
	
	/**
	 * Annealing Schedule 2: T decreases by a factor of .01% each iteration
	 * @param t The current temperature
	 * @return The temperature for the next iteration
	 */
	public static double annealingSchedule2(double t) {
		return t * 0.9999;
	}
	
	/**
	 * Annealing Schedule 3: T is equal to 100 (the original temperature) divided by the current iteration number
	 * @param iteration The current iteration that has just completed
	 * @return The temperature for the next iteration
	 */
	public static double annealingSchedule3(int iteration) {
		return 100.0 / (iteration + 1);
	}
}