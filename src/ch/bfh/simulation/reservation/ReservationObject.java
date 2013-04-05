package ch.bfh.simulation.reservation;

import ch.bfh.simulation.Vehicle;

public class ReservationObject {
	
	private double _to;
	private double _from;
	private double _speed;
	private Vehicle _reservator;
	
	/**
	 * Gets the id of the reservation object
	 * @return
	 */
	public String getId() {
		return _from+"-"+_to;
	}
	
	/**
	 * Get vehicle
	 * @return
	 */
	public Vehicle getReservator() {
		return _reservator;
	}
	
	/**
	 * Set vehicle
	 * @param v
	 */
	public void setReservator(Vehicle v) {
		_reservator = v;
	}
	
	/**
	 * Get speed value
	 * @return
	 */
	public double getSpeed() {
		return _speed;
	}
	
	/**
	 * Set speed value
	 * @param value
	 */
	public void setSpeed(double value) {
		_speed = value;
	}
	
	/**
	 * Get to value
	 * @return
	 */
	public double getTo() {
		return _to;
	}

	/**
	 * Set to value
	 * @param value
	 */
	public void setTo(double value) {
		this._to = value;
	}
	
	/**
	 * Get from value
	 * @return
	 */
	public double getFrom() {
		return _from;
	}
	
	/**
	 * Set from value
	 * @param value
	 */
	public void setFrom(double value) {
		this._from = value;
	}

	/**
	 * Constructor
	 * @param v
	 * @param from
	 * @param to
	 */
	public ReservationObject(Vehicle v, double from, double to) {
		_reservator = v;
		_from = from;
		_to = to;
	}
	
	/**
	 * Constructor
	 * @param v
	 * @param from
	 * @param to
	 * @param speed
	 */
	public ReservationObject(Vehicle v, double from, double to, double speed) {
		_reservator = v;
		_from = from;
		_to = to;
		_speed = speed;
	}
}
