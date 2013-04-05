package ch.bfh.graph;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is used to represent the neighbours of the current node.
 * @author nino
 *
 * @param <N>
 */
public class Neighbours<N extends Node>{
    protected N _node;
    protected Set<N> _neighbours;
    
    /**
     * Constructor for Neighbours
     * @param node
     */
    public Neighbours(N node) {
		this._node=node;
		this._neighbours = new HashSet<N>();
	}
    
    /**
     * Get main node
     * @return
     */
	public N getNode() {
		return _node;
	}
	
	/**
	 * Set main node
	 * @param node
	 */
	public void setNode(N node) {
		this._node = node;
	}
	
	/**
	 * Get Neighbours of main node
	 * @return
	 */
	public Set<N> getNeighbours() {
		return _neighbours;
	}
	
	/**
	 * Set neighbours of main node
	 * @param neighbors
	 */
	public void setNeighbours(Set<N> neighbors) {
		this._neighbours = neighbors;
	}
	
	/**
	 * Add Neighbour of main node
	 * @param neighbor
	 */
	public void addNeighbour(N neighbor) {
		this._neighbours.add(neighbor);
	}

}