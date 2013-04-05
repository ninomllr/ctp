package ch.bfh.simulation.graph;

import ch.bfh.graph.*;

/**
 * Extended edge with additional cost information
 * @author gigi
 *
 */
public class MapEdge extends Edge {

	protected int _cost;
	protected Boolean _blocked;
	protected MapToken _token;
	
	/**
	 * Gets id of the two connecting nodes
	 * @return
	 */
	public String getId() {
		return _from.getId() + "-" + _to.getId();
	}
	
	/**
	 * Gets token of the edge
	 * @return
	 */
	public MapToken getToken() {
		return _token;
	}
	
	/**
	 * Sets token of the edge
	 * @param token
	 */
	public void setToken(MapToken token) {
		setToken(token, true);
	}
	
	/**
	 * Check if the edge has a token
	 * @return
	 */
	public Boolean hasToken() {
		return _token != null;
	}
	
	/**
	 * Sets token of the edge with check for opposite
	 * @param token
	 * @param setOpposite
	 */
	public void setToken(MapToken token, Boolean setOpposite) {
		if (setOpposite && _oppositeDirection != null) {
			((MapEdge)_oppositeDirection).setToken(token, false);
		}
		_token = token;
	}
	
	/**
	 * Sets a block on the edge with check for opposite
	 * @param setOpposite
	 */
	public void setBlock(Boolean setOpposite)
	{		
		_blocked = true;
		if (setOpposite && _oppositeDirection != null) {
			((MapEdge)_oppositeDirection).setBlock(false);
		}
	}
	
	/**
	 * Sets a block on the edge
	 */
	public void setBlock()
	{
		setBlock(true);
	}
	
	/**
	 * Removes a block on the edge with check for opposite
	 * @param setOpposite
	 */
	public void setUnblock(Boolean setOpposite) {
		_blocked = false;
		if (setOpposite && _oppositeDirection != null) {
			((MapEdge)_oppositeDirection).setUnblock(false);
		}
	}
	
	/**
	 * Removes a block on the edge
	 */
	public void setUnblock() {
		setUnblock(true);
	}
	
	/**
	 * Gets if the edge is blocked
	 * @return
	 */
	public Boolean getBlocked() {
		return _blocked;
	}
	
	/**
	 * Constructor for MapEdge
	 * @param node1
	 * @param node2
	 * @param cost
	 */
	public MapEdge(MapNode node1, MapNode node2, int cost) {
		_from = node1;
		_to = node2;
		_cost = cost;
		_blocked = false;
	}

	/**
	 * Get cost of a edge
	 * @return
	 */
	public int getCost() {
		return _cost;
	}

	/**
	 * Set cost of a edge
	 * @param cost
	 */
	public void setCost(int cost) {
		_cost = cost;
	}
}
