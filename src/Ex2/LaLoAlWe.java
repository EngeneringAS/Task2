package Ex2;
/**
 * class for question 2
 * @author Alexey Titov &   Shalom Weinberger
 * @version 2.0
 */
import Ex1.Location;

public class LaLoAlWe {

	private double weight;
	private Location LLA;
	//constructor
	public LaLoAlWe(double weight, Location lLA) {
		super();
		this.weight = weight;
		this.LLA = lLA;
	}
	/**
	 * Getters for weight and lla
	 */
	public double getWeight() {
		return weight;
	}
	public Location getLLA() {
		return LLA;
	}
	@Override
	public String toString() {
		return this.weight + "," + this.LLA.toString();
	}
	
}
