package ch.bfh.simulation.reservation;

import java.util.*;

import ch.bfh.simulation.graph.*;
import ch.bfh.simulation.*;

public class EdgeReservationList extends SortedList<ReservationObject> implements IUpdateable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MapEdge _edge1;
	private MapEdge _edge2;
	
	/**
	 * Constructor
	 * @param edge1
	 * @param edge2
	 */
	public EdgeReservationList(MapEdge edge1, MapEdge edge2){
		super(new ReservationComparator());
		_edge1 = edge1;
		_edge2 = edge2;
	}
	
	/**
	 * Removes all reservations for a vehicle
	 * @param v
	 */
	public void clearAllReservations(Vehicle v) {

		List<ReservationObject> list = new ArrayList<ReservationObject>();

		for (ReservationObject r : this) {
			if (r.getReservator().equals(v))
				list.add(r);
		}

		this.removeAll(list);
	}
	
	/**
	 * Reserve from to for a vehicle
	 * @param v
	 * @param from
	 * @param to
	 * @param speed
	 */
	public void reserve(Vehicle v, double from, double to, double speed) {
		super.add(new ReservationObject(v, from, to, speed));
	}
	
	/**
	 * Checks if a reservation exists between from and to
	 * @param from
	 * @param to
	 * @return
	 */
	public Boolean reservationExists(double from, double to) {
		return reservationExists(null, from, to);
	}
	
	/**
	 * Checks if a reservation exists between from and to for a specific vehicle
	 * @param v
	 * @param from
	 * @param to
	 * @return
	 */
	public Boolean reservationExists(Vehicle v, double from, double to) {
		
		SortedList<ReservationObject> clone = new SortedList<ReservationObject>(this);
		ReservationObject obj = clone.poll();
		
		while (obj!=null) {
			if ((obj.getFrom() <= from && from <= obj.getTo()) ||
					(obj.getFrom() <= to && to <= obj.getTo())) {	
				
				// allow reservation cutting if it is the same vehicle
				if (v!=null && v.equals(obj.getReservator()))
					return false;
				
				return true;
			}
			
			obj = clone.poll();
		}
		
		return false;
	}

	@Override
	/**
	 * Update method that cleans all the old reservations
	 */
	public void update(double deltaT) {
		double _simulationTime=Simulation.getInstance().getSimulationTime();
		
		ReservationObject obj = super.peek();
		
		if (obj!=null)
		{
			if (!(obj.getFrom() <= _simulationTime && _simulationTime <= obj.getTo()))
			{
				if (obj.getTo() < _simulationTime) {
					_edge1.setUnblock();
					_edge2.setUnblock();
					super.poll();
				}
			}
			
		} else {
			_edge1.setUnblock();
			_edge2.setUnblock();
		}
	}
	
	/**
	 * Gets the priority of the object. It is
	 * hard coded to 0 so it will always be updated after the vehicles
	 * @return
	 */
	@Override
	public int getPriority() {
		return 0;
	}


}
