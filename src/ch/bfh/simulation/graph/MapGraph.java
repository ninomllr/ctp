package ch.bfh.simulation.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ch.bfh.graph.*;
import ch.bfh.utils.MapReader;


/**
 * Map graph is used to display a graph on map with coordinates and distances
 * @author gigi
 *
 */
public class MapGraph extends Graph<MapNode, MapEdge>{
	
	private static MapGraph _graph;
	
	private List<MapToken> _tokens = new ArrayList<MapToken>();
	
	/**
	 * Get instance of mapgraph (Singelton)
	 * @return
	 */
	public static MapGraph getInstance() {
		if (_graph == null) _graph = new MapGraph();
		return _graph;
	}
	
	/**
	 * Load Map
	 * @param map
	 */
	public static void loadMap(String map) {
		MapReader reader = new MapReader();
		_graph = reader.loadMap(map);	
	}
	
	/**
	 * Constructor for MapGraph
	 */
	private MapGraph() {
		
	}

	/**
	 * Get vehicles
	 * @return
	 */
	public List<MapToken> getTokens() {
		return _tokens;
	}

	/**
	 * Set vehicle
	 * @param _vehicle
	 */
	public void setToken(MapToken vehicle) {
		this._tokens.add(vehicle);
	}

	/**
	 * Connect two nodes
	 * @param node1
	 * @param node2
	 * @param cost
	 */
	public void addConnection(MapNode node1, MapNode node2, int cost) {
		if(node1 != null && node2 != null){
			MapEdge edge1 = new MapEdge(node1, node2, cost);
        	MapEdge edge2 = new MapEdge(node2, node1, cost);
        	edge1.setOppositeDirection(edge2);
        	edge2.setOppositeDirection(edge1);
        	addEdge(edge1);
        	addEdge(edge2);
        	setNeighbours(node1, node2);
		}
	}
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @param cost
	 */
	public void addConnection(String from, String to, int cost) {
		MapNode node1 = getNode(from);
		MapNode node2 = getNode(to);
		addConnection(node1, node2, cost);
	}

	/**
	 * Calculates an approximate distance between two points
	 * @param a
	 * @param b
	 * @return
	 */
	public double calcManhattanDistance(MapNode a, MapNode b){
        return Math.abs(a.getPosition().getX() - b.getPosition().getX())
                + Math.abs(a.getPosition().getY() - b.getPosition().getY());
    }

	/**
	 * Get all neighbors of a node 
	 * @param id
	 * @return
	 */
	public Set<MapNode> getNeighbours(String id) {
		Neighbours<MapNode> neighbours = this.getNeighbours().get(id);
		if (neighbours == null)
			return new HashSet<MapNode>();
			
		return neighbours.getNeighbours();
	}
	
	/**
	 * Mark two nodes as neighbors
	 * @param node1
	 * @param node2
	 */
	public void setNeighbours(MapNode node1, MapNode node2) {
		setNeighbour(node1, node2);
		setNeighbour(node2, node1);
	}
	
	/**
	 * Mark a neighbor of a node
	 * @param node
	 * @param neighbour
	 */
	private void setNeighbour(MapNode node, MapNode neighbour) {
		Neighbours<MapNode> adj = getNeighbours().get(node.getId());
		if (adj==null) {
			adj = new Neighbours<MapNode>(node);
			getNeighbours().put(node.getId(), adj);
		}
		adj.addNeighbour(neighbour);
	}

	/**
	 * Add new token
	 * @param token
	 */
	public void addToken(MapToken token) {
		_tokens.add(token);
	}

	/**
	 * Gets a random neighbor node
	 * @param _destinationNodeStorage
	 * @return
	 */
	public MapNode getRandomNeighbour(MapNode node) {
		Random r = new Random();
		Set<MapNode> neighbours = getNeighbours(node.getId());
		int value = r.nextInt(neighbours.size());
		int i = 0;
		for(MapNode obj : neighbours)
		{
		    if (i == value)
		        return obj;
		    i = i + 1;
		}
		return null;
	}

	/**
	 * Gets the edge that connects from and to
	 * @param from
	 * @param to
	 * @return
	 */
	public MapEdge getEdge(String from, String to) {
		MapNode node1 = getNode(from);
		MapNode node2 = getNode(to);
		return getEdge(node1, node2);
	}
	
}