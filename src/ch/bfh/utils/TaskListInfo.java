package ch.bfh.utils;

/**
 * 
 * @author gigi
 *
 */
public class TaskListInfo {
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public double getArrival() {
		return arrival;
	}
	public void setArrival(double arrival) {
		this.arrival = arrival;
	}

	String from;
	String to;
	double arrival;
}
