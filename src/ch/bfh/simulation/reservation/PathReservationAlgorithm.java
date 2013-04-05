package ch.bfh.simulation.reservation;

import java.util.*;

import ch.bfh.simulation.*;
import ch.bfh.simulation.graph.*;
import ch.bfh.utils.Ref;
import ch.bfh.utils.Settings;

public class PathReservationAlgorithm {

	private static Map<MapEdge, SortedList<ReservationObject>> _tempReservations;
	private static Map<MapEdge, ReservationObject> _realReservations;

	/**
	 * Finds a valid reservation list for a provided route in the
	 * defined search horizon
	 * 
	 * @param vehicle
	 * @param route
	 * @return
	 */
	public static Map<MapEdge, ReservationObject> search(Vehicle vehicle,
			List<MapNode> route) {

		double start = Simulation.getInstance().getSimulationTime();
		double relSearchHorizon = Simulation.getInstance().getSimulationTime()
				+ Settings.SEARCH_HORIZON;

		findTimeWindows(vehicle, route, start, relSearchHorizon);

		PathReservationNode endNode = generatePathReservationNode(route);

		// minimum travel time if the vehicle could drive full speed
		double minTravelTime = calculateTravelTime(route, vehicle.getSpeed());

		_realReservations = new HashMap<MapEdge, ReservationObject>();

		findReservations(vehicle, start, relSearchHorizon, endNode,
				minTravelTime);

		if (_realReservations.size() > 0)
			return _realReservations;

		return null;
	}

	/**
	 * Find a possible reservation
	 * 
	 * @param vehicle
	 * @param start
	 * @param relSearchHorizon
	 * @param endNode
	 * @param minTravelTime
	 */
	private static void findReservations(Vehicle vehicle, double start,
			double relSearchHorizon, PathReservationNode endNode,
			double minTravelTime) {
		// d is equivalent to the arrival time of the reservation
		for (double d = start + minTravelTime; d <= relSearchHorizon; d += 1.0) {

			if (calculateReservations(vehicle, endNode, d, start)) {
				/*System.out.println("(" + vehicle.getId()
						+ ") Legal reservation");*/
				break;
			}

			_realReservations = new HashMap<MapEdge, ReservationObject>();
		}
	}

	/**
	 * Generates a node chain for easier handling of the algorithm
	 * 
	 * @param route
	 * @return
	 */
	private static PathReservationNode generatePathReservationNode(
			List<MapNode> route) {

		PathReservationNode endNode = null;

		for (MapNode node : route) {
			MapNode parent = null;
			if (endNode != null)
				parent = endNode.getNode();
			MapEdge edge = MapGraph.getInstance().getEdge(parent, node);
			PathReservationNode pNode = new PathReservationNode(node, endNode,
					edge, _tempReservations.get(edge));

			endNode = pNode;
		}
		return endNode;
	}

	/**
	 * Finds all time windows between a certain start time and the search horizon
	 * considering reservations on edges and nodes
	 * 
	 * @param vehicle
	 * @param route
	 * @param start
	 * @param relSearchHorizon
	 */
	private static void findTimeWindows(Vehicle vehicle, List<MapNode> route,
			double start, double relSearchHorizon) {

		_tempReservations = new HashMap<MapEdge, SortedList<ReservationObject>>();

		// find all open time windows in the specified search horizon
		for (int i = 0; i < route.size() - 1; i++) {

			// Load next edge
			MapEdge edge = MapGraph.getInstance().getEdge(route.get(i),
					route.get(i + 1));

			SortedList<ReservationObject> tempReservations = new SortedList<ReservationObject>(
					new ReservationComparator());

			if (edge != null) {

				Ref<Double> lastReservationTime = new Ref<Double>(start);

				EdgeReservationList reservations = Simulation.getInstance()
						.getModel().getReservationObjects(edge);
				NodeReservationList fromNodeReservations = Simulation
						.getInstance().getModel()
						.getReservationObjects(edge.getFrom());
				NodeReservationList toNodeReservations = Simulation
						.getInstance().getModel()
						.getReservationObjects(edge.getTo());

				findRelevantReservationObjects(vehicle, relSearchHorizon, edge,
						tempReservations, lastReservationTime, reservations,
						fromNodeReservations, toNodeReservations);

				_tempReservations.put(edge, tempReservations);
			}
		}
	}

	/**
	 * Finds a list of all relevant reservation objects. Relevant considering
	 * the vehicle, the time horizon and start time
	 * 
	 * @param vehicle
	 * @param relSearchHorizon
	 * @param edge
	 * @param tempReservations
	 * @param lastReservationTime
	 * @param reservations
	 * @param fromNodeReservations
	 * @param toNodeReservations
	 */
	private static void findRelevantReservationObjects(Vehicle vehicle,
			double relSearchHorizon, MapEdge edge,
			SortedList<ReservationObject> tempReservations,
			Ref<Double> lastReservationTime, EdgeReservationList reservations,
			NodeReservationList fromNodeReservations,
			NodeReservationList toNodeReservations) {

		// find all relevant reservation objects
		for (ReservationObject reservation : reservations) {
			Ref<Double> toTime = new Ref<Double>(reservation.getFrom());
			if (relSearchHorizon <= toTime.getValue()) {
				toTime.setValue(relSearchHorizon);
			}
			
			if (toTime.getValue() < lastReservationTime.getValue()) {
				double swap = toTime.getValue();
				toTime.setValue(lastReservationTime.getValue());
				lastReservationTime.setValue(swap);
			}

			considerNodeReservation(fromNodeReservations, lastReservationTime,
					toTime);
			considerNodeReservation(toNodeReservations, lastReservationTime,
					toTime);

			tempReservations.add(new ReservationObject(vehicle,
					lastReservationTime.getValue(), toTime.getValue()));
			if(Settings.DEBUG_MODE_ENABLED){
				System.out.println("(" + vehicle.getId() + ") #1 Time window for "
						+ edge.getId() + ": " + lastReservationTime.getValue()
						+ "-" + toTime.getValue());
			}
			
			lastReservationTime.setValue(reservation.getTo());

			if (relSearchHorizon <= toTime.getValue()) {
				break;
			}
		}
		
		if (relSearchHorizon > lastReservationTime.getValue()) {
			tempReservations.add(new ReservationObject(vehicle,
					lastReservationTime.getValue(), relSearchHorizon));

			if(Settings.DEBUG_MODE_ENABLED){
				System.out.println("(" + vehicle.getId()
						+ ") #2 Time window for " + edge.getId() + ": "
						+ lastReservationTime.getValue() + "-"
						+ relSearchHorizon);
			}
		}
	}

	/**
	 * Considers nodes for the time windows, which means it checks if the 
	 * time window provided by the edge needs to be smallered because of
	 * reservations on the edge
	 * 
	 * @param reservations
	 * @param from
	 * @param to
	 */
	private static void considerNodeReservation(
			NodeReservationList reservations, Ref<Double> from, Ref<Double> to) {

		// find all relevant reservation objects
		for (ReservationObject reservation : reservations) {
			// make the time window smaller if there are relevant reservations
			// on the node
			if (reservation.getFrom() < from.getValue()
					&& from.getValue() < reservation.getTo()) {
				from.setValue(reservation.getTo());
			}

			if (reservation.getFrom() < to.getValue()
					&& to.getValue() < reservation.getTo()) {
				to.setValue(reservation.getFrom());
			}

		}

	}

	/**
	 * Calculate all reservations.
	 * 
	 * @param vehicle
	 * @param node
	 * @param arrivalTime
	 * @param start
	 * @return
	 */
	private static boolean calculateReservations(Vehicle vehicle,
			PathReservationNode node, double arrivalTime, double start) {

		// we are at the start point
		if (node.getCameFrom() == null)
			return true;

		// calculate the max speed travel time, and the corresponding start time
		double travelTime = node.getEdge().getCost() / vehicle.getSpeed();
		double startTime = arrivalTime - travelTime;

		PathReservationNode parent = node.getCameFrom();

		// if we are not yet at the beginning
		if (parent.getCameFrom() != null) {
			boolean isPossible = false;

			do {
				SortedList<ReservationObject> reservations = parent
						.getReservations();
				if (reservations == null)
					break;

				// check for a valid start time on the parent edge
				for (ReservationObject obj : reservations) {
					if (obj.getFrom() <= startTime && startTime <= obj.getTo())
						isPossible = true;
					continue;
				}

				startTime -= 1.0;

				// no possible route in the time windows found
				if (startTime < start) {
					return false;
				}

			} while (!isPossible);
		} else {

			startTime = start;

			SortedList<ReservationObject> reservations = parent
					.getReservations();

			if (reservations != null) {
				for (ReservationObject obj : reservations) {
					if (!(obj.getFrom() <= startTime && startTime <= obj
							.getTo()))
						return false;
				}
			}
		}

		return validateRegistration(vehicle, node, arrivalTime, start,
				startTime);

	}

	/**
	 * Validate the registration, checks if the needed speed is not more than the maximum
	 * possible speed for a vehicle and it checks if the start time is not before the current
	 * time
	 * 
	 * @param vehicle
	 * @param node
	 * @param arrivalTime
	 * @param start
	 * @param startTime
	 * @return
	 */
	private static boolean validateRegistration(Vehicle vehicle,
			PathReservationNode node, double arrivalTime, double start,
			double startTime) {

		// calculate speed for the time interval
		double speed = node.getEdge().getCost() / (arrivalTime - startTime);

		// Check if calculated speed is possible for vehicle
		if (speed > vehicle.getSpeed())
			return false;

		ReservationObject reservationObject = new ReservationObject(vehicle,
				startTime, arrivalTime, speed);

		// check if the reservation is in a legal time window
		if (isRegistrationInTimeWindow(node.getEdge(), reservationObject)) {
			_realReservations.put(node.getEdge(), reservationObject);

			return calculateReservations(vehicle, node.getCameFrom(),
					startTime, start);
		} else {
			return false;
		}
	}

	/**
	 * Check if a planned registration object is in an open time window
	 * @param edge
	 * @param obj
	 * @return
	 */
	private static boolean isRegistrationInTimeWindow(MapEdge edge,
			ReservationObject obj) {
		SortedList<ReservationObject> reservations = _tempReservations
				.get(edge);

		for (ReservationObject reservationObject : reservations) {
			if ((reservationObject.getFrom() <= obj.getFrom() && obj.getFrom() <= reservationObject
					.getTo())
					&& (reservationObject.getFrom() <= obj.getTo() && obj
							.getTo() <= reservationObject.getTo()))
				return true;
		}

		return false;

	}

	/**
	 * Calculates the travel time for a vehicle to pass a route
	 * @param node
	 * @param speed
	 * @return
	 */
	private static double calculateTravelTime(List<MapNode> node, double speed) {

		double travelTime = 0.0;

		for (int i = 0; i <= node.size() - 2; i++) {
			MapNode from = node.get(i);
			MapNode to = node.get(i + 1);

			if (from != null && to != null) {
				double cost = MapGraph.getInstance().getEdge(from, to)
						.getCost();
				travelTime += (cost / speed);
			}
		}

		return travelTime;
	}
}
