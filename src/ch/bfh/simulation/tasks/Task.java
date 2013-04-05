package ch.bfh.simulation.tasks;

import ch.bfh.simulation.Vehicle;

public class Task
{
	public Task(String from, String to, double arrival, Vehicle vehicle){
		this._from = from;
		this._to = to;
		this._arrival = arrival;
		this._vehicle = vehicle;
	}
	
	public String getFrom() {
		return _from;
	}
	public void setFrom(String from) {
		this._from = from;
	}
	public String getTo() {
		return _to;
	}
	public void setTo(String to) {
		this._to = to;
	}
	public double getArrival() {
		return _arrival;
	}
	public void setArrival(double arrival) {
		this._arrival = arrival;
	}
	public void setVehicle(Vehicle vehicle){
		this._vehicle = vehicle;
	}
	public Vehicle getVehicle(){
		return _vehicle;
	}

	private String _from;
	private String _to;
	private double _arrival;
	private Vehicle _vehicle;
}
