package Ex2;

import Ex1.Location;

public class LocSig {
	//variables
	private Location lla;
	private int signal;
	//constructor
	public LocSig() {
		this.lla=new Location();
		this.signal=-120;
	}
	/**
	 * Getters and Setters location
	 */
	public Location getLla() {
		return lla;
	}
	public void setLla(Location lla) {
		this.lla = lla;
	}
	/**
	 * Getters and Setters signal
	 */
	public int getSignal() {
		return signal;
	}
	public void setSignal(int signal) {
		this.signal = signal;
	}
}
