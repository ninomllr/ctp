package ch.bfh.graph;

/**
 * The edge connects two nodes
 * @author nino
 *
 */
public class Edge {

    protected Node _from;
	protected Node _to;
	protected Edge _oppositeDirection;
    
	/**
	 * Get start node
	 * @return
	 */
    public Node getFrom() {
		return _from;
	}

    /**
     * Set start node
     * @param from
     */
	public void setFrom(Node from) {
		this._from = from;
	}
	
	/**
	 * Get destination node
	 * @return
	 */
	public Node getTo() {
		return _to;
	}

	/**
	 * Set destination node
	 * @param to
	 */
	public void setTo(Node to) {
		this._to = to;
	}

	/**
	 * Gets the opposite direction
	 * @return
	 */
	public Edge getOppositeDirection() {
		return _oppositeDirection;
	}

	/**
	 * Sets the opposite direction
	 * @param _oppositeDirection
	 */
	public void setOppositeDirection(Edge _oppositeDirection) {
		this._oppositeDirection = _oppositeDirection;
	}

}