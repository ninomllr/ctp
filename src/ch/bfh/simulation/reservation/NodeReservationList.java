package ch.bfh.simulation.reservation;

import java.util.ArrayList;
import java.util.List;

import ch.bfh.simulation.IUpdateable;
import ch.bfh.simulation.Simulation;
import ch.bfh.simulation.Vehicle;
import ch.bfh.simulation.graph.MapNode;

public class NodeReservationList extends SortedList<ReservationObject>
		implements IUpdateable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private double _simulationTime;
	private MapNode _node;

	/**
	 * Constructor
	 * @param node
	 */
	public NodeReservationList(MapNode node) {
		super(new ReservationComparator());
		_node = node;
	}

	/**
	 * Clear all reservations for a certain vehicle
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
	 * Reserve between from and to
	 * @param v
	 * @param from
	 * @param to
	 */
	public void reserve(Vehicle v, double from, double to) {
		super.add(new ReservationObject(v, from, to));
	}

	/**
	 * Check if a reservation exists between from to
	 * @param from
	 * @param to
	 * @return
	 */
	public Boolean reservationExists(double from, double to) {
		
		SortedList<ReservationObject> clone = new SortedList<ReservationObject>(
				this);
		ReservationObject obj = clone.poll();

		while (obj != null) {
			if ((obj.getFrom() <= from && from <= obj.getTo())
					|| (obj.getFrom() <= to && to <= obj.getTo())) {
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
		double _simulationTime = Simulation.getInstance().getSimulationTime();

		ReservationObject obj = super.peek();

		if (obj != null) {
			if (!(obj.getFrom() <= _simulationTime && _simulationTime <= obj
					.getTo())) {
				if (obj.getTo() < _simulationTime) {
					_node.setUnblock();
					super.poll();
				}
			}
		} else {
			_node.setUnblock();
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
