package ch.bfh.simulation.reservation;

import java.util.Comparator;

public class ReservationComparator implements Comparator<ReservationObject> {

	/**
	 * Compares the reservation by from date
	 */
	public int compare(ReservationObject first, ReservationObject second) {
		if (first.getFrom() < second.getFrom()) {
			return -1;
		} else if (first.getFrom() > second.getFrom()) {
			return 1;
		} else {
			return 0;
		}
	}

}
