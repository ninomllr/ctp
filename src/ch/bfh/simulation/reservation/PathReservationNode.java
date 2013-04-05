package ch.bfh.simulation.reservation;

import ch.bfh.simulation.graph.*;

public class PathReservationNode {

	private MapNode _node;
	private PathReservationNode _cameFrom;
	private MapEdge _edge;
	private SortedList<ReservationObject> _reservations;
	
	/**
	 * Gets reservation object for the edge
	 * @return
	 */
	public SortedList<ReservationObject> getReservations() {
		return _reservations;
	}
	
	/**
	 * Attaches reservation object for the edge
	 * @param value
	 */
	public void setReservations(SortedList<ReservationObject> value) {
		_reservations = value;
	}
	
	/**
	 * Gets he edge that connects the last node and the current one
	 * @return
	 */
	public MapEdge getEdge() {
		return _edge;
	}
	
	/**
	 * Sets the edge that connects the last node and the current one
	 * @param value
	 */
	public void setEdge(MapEdge value) {
		_edge = value;
	}
	
	/**
	 * Gets the current node
	 * @return
	 */
	public MapNode getNode() {
		return _node;
	}
	
	/**
	 * Sets the current node
	 * @param value
	 */
	public void setNode(MapNode value) {
		_node = value;
	}
	
	/** 
	 * Gets the last node
	 * @return
	 */
	public PathReservationNode getCameFrom() {
		return _cameFrom;
	}
	
	/**
	 * Sets the last node
	 * @param value
	 */
	public void setCameFrom(PathReservationNode value) {
		_cameFrom = value;
	}
	
	/**
	 * Constructor
	 * @param node
	 * @param cameFrom
	 * @param edge
	 * @param reservation
	 */
	public PathReservationNode(MapNode node, PathReservationNode cameFrom, MapEdge edge, SortedList<ReservationObject> reservation) {
		_cameFrom = cameFrom;
		_node = node;
		_edge = edge;
		_reservations = reservation;
		
	}
	
	
	
}
