package ch.bfh.simulation.graph;

import java.awt.Color;

import ch.bfh.graph.Node;
import ch.bfh.simulation.*;
import ch.bfh.graph.Position;

/**
 * Extends node with additional information
 * @author gigi
 *
 */
public class MapNode extends Node{

    protected Position _position;
    protected Boolean _blocked;
    protected MapToken _token;
    protected Vehicle _destinationOf;
	
    /**
     * Gets a token
     * @return
     */
	public MapToken getToken() {
		return _token;
	}
	
	/**
	 * Sets a token
	 * @param token
	 */
	public void setToken(MapToken token) {
		_token = token;
	}
	
	/**
	 * Returns if the node has a token
	 * @return
	 */
	public Boolean hasToken() {
		return _token != null;
	}

    /**
     * Get destination of somone
     * @return
     */
    public Vehicle getDestinationOf() {
		return _destinationOf;
	}

    /**
     * Set destination of someone
     * @param destinationOfSomeone
     */
	public void setDestinationOf(Vehicle vehicle) {
		this._destinationOf = vehicle;
	}
	
	public void resetDestinationOf() {
		this._destinationOf = null;
	}

	/**
     * Constructor for mapnode
     * @param id
     * @param x
     * @param y
     */
    public MapNode(String id, double x, double y) {
		_position = new Position(x, y);
		_id=id;
		_destinationOf = null;
		_blocked = false;
	}
    
    /**
     * Get position of a node
     * @return
     */
    public Position getPosition() {
		return _position;
	}

    /**
     * Set position of a node
     * @param position
     */
	public void setPosition(Position position) {
		_position = position;
	}
	
	/**
	 * Sets the block
	 */
	public void setBlock()
	{
		_blocked = true;
	}
	
	/**
	 * Remove the block
	 */
	public void setUnblock() {
		_blocked = false;
	}
	
	/**
	 * Gets if the node is blocked
	 * @return
	 */
	public Boolean getBlocked() {
		return _blocked;
	}
	
	/**
	 * Returns the color of the node
	 * @return
	 */
	public Color getColor() {
		if (_destinationOf!=null)
			return _destinationOf.getToken().getColor();
		
		return null;
	}
}
