package ch.bfh.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Main graph class
 * @author nino
 *
 * @param <N>
 * @param <E>
 */
public class Graph<N extends Node, E extends Edge> {
	
	protected List<N> _nodes;
    
    protected List<E> _edges;
    
    //Index for fast access
    private Map<String, Neighbours<N>> _neighbours;
	
    /**
     * Constructor of Class
     */
	public Graph() {
		this._nodes = new ArrayList<N>();
		this._edges = new ArrayList<E>();
		this._neighbours = new HashMap<String, Neighbours<N>>();
	}
    
	
	/**
	 * Add Node to node list
	 * @param node
	 */
    public void addNode(N node) {
    	_nodes.add(node);
    }
    
    /**
     * Add Edge to edge list
     * @param edge
     */
    public void addEdge(E edge) {
    	_edges.add(edge);
    }
    
    /**
     * Get Edge between two nodes
     * @param node
     * @param neighbor
     * @return
     */
    public E getEdge(N node, N neighbor) {
    	
    	if (node == null || neighbor == null)
    		return null;
    	
		for (E edge : _edges) {
			if (edge.getFrom() == node && edge.getTo() == neighbor)
				return edge;
		}
		return null;
	}
    
    /**
     * Remove edge from graph
     * @param edge1
     */
    public void removeEdge(E edge1) {
    	_edges.remove(edge1);
	}
    
    /**
     * Get node from graph
     * @param firstId
     * @return
     */
	public N getNode(String firstId) {
		for (N node : _nodes) {
			if (node.getId().equals(firstId))
				return node;
		}
		
		return null;
	}
	
	
	/**
	 * Get list of nodes
	 * @return
	 */
	public List<N> getNodes()
	{
		return _nodes;
	}
	
	/**
	 * Get list of edges
	 * @return
	 */
	public List<E> getEdges()
	{
		return _edges;
	}

    /**
     * Get Neighbour lookup map
     * @return
     */
	public Map<String, Neighbours<N>> getNeighbours() {
		return _neighbours;
	}
	
	/**
	 * Gets a random node from the graph
	 * @return
	 */
	public N getRandomNode() 
	{
		int size = _nodes.size();
		if(size > 0)
		{
			int item = new Random().nextInt(size);
		
			int i = 0;
			for(N obj : _nodes)
			{
				if (i == item)
					return obj;
				i = i + 1;
			}
		}
		return null;
	}
}