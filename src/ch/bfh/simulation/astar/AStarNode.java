package ch.bfh.simulation.astar;

import ch.bfh.simulation.graph.MapNode;

/**
 * Node helper for a* algorithm
 * @author gigi
 *
 */
public class AStarNode {

    private MapNode _node;

    //used to construct the path after the search is done
    private AStarNode _cameFrom;

    // Distance from source along optimal path
    private double _g;

    // Heuristic estimate of distance from the current node to the target node
    private double _h;

    /**
     * Contructor for AStarNode
     * @param node
     * @param g
     * @param h
     */
	public AStarNode(MapNode node, double g, double h) {
		_node=node;
		_g=g;
		_h=h;
	}

	/**
	 * Get real node
	 * @return
	 */
	public MapNode getNode() {
		return _node;
	}

	/**
	 * Set real node
	 * @param node
	 */
	public void setNode(MapNode node) {
		_node = node;
	}
	
	/**
	 * Get ID of real node
	 * @return
	 */
	public String getId() {
		return _node.getId();
	}

	/**
	 * Get distance from source along optimal path
	 * @return
	 */
	public double getG() {
		return _g;
	}

	/**
	 * Set distance from source along optimal path
	 * @param g
	 */
	public void setG(double g) {
		_g = g;
	}

	/**
	 * Get Heuristic estimate of distance from the current node to the target node
	 * @return
	 */
	public double getH() {
		return _h;
	}

	/**
	 * Set Heuristic estimate of distance from the current node to the target node
	 * @param h
	 */
	public void setH(double h) {
		_h = h;
	}

	/**
	 * Get key value for evaluation of route
	 * @return
	 */
	public double getF() {
		return _g+_h;
	}

	/**
	 * Get last visited node
	 * @return
	 */
	public AStarNode getCameFrom() {
		return _cameFrom;
	}
	/**
	 * Set last visited node
	 * @param cameFrom
	 */
	public void setCameFrom(AStarNode cameFrom) {
		_cameFrom = cameFrom;
	}
}