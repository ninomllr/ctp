package ch.bfh.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ch.bfh.simulation.astar.AStarAlgorithm;
import ch.bfh.simulation.graph.MapEdge;
import ch.bfh.simulation.graph.MapGraph;
import ch.bfh.simulation.graph.MapNode;
import ch.bfh.simulation.graph.MapToken;
import ch.bfh.simulation.reservation.PathReservationAlgorithm;
import ch.bfh.simulation.reservation.ReservationObject;
import ch.bfh.simulation.tasks.Task;
import ch.bfh.utils.Settings;

/**
 * Class for vehicles
 * 
 * @author gigi
 * 
 */
public class Vehicle implements IUpdateable {

	private List<MapNode> _route;
	private double _routeDistance;
	private MapToken _token;
	private MapNode _currentNode;
	private MapNode _destinationNode;
	private MapNode _realEndNode;
	private double _speed;
	private double _progress;
	private Boolean _canCalculate;
	private String _id;
	private Task _currentTask;
	private boolean _taskStarted;
	private boolean _passedGoal;
	
	/**
	 * Gets the token
	 * @return
	 */
	public MapToken getToken() {
		return _token;
	}
	
	/**
	 * Gets the speed
	 * @return
	 */
	public double getSpeed() {
		return _speed;
	}

	/**
	 * Check if vehicle is able to calc route
	 * 
	 * @return
	 */
	public Boolean getCanCalculate() {
		return _canCalculate;
	}

	public String getId() {
		return _id;
	}

	/**
	 * Create a vehicle and its route
	 * 
	 * @param token
	 * @param node
	 */
	public Vehicle(MapToken token, MapNode node, String id) {

		_id = id;
		_token = token;
		_currentNode = node;
		_progress = 0.0;
		_speed = 4.0;
		_canCalculate = true;
		_taskStarted = false;
		
		

		try {
			if (_currentNode != null) {
				_token.setPosition(_currentNode.getPosition().clone());
				_currentNode.setToken(_token);
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		_route = new ArrayList<MapNode>();
	}

	/**
	 * Calculate route for a vehicle to destination
	 * 
	 * @param destination
	 */
	public void calculateRoute(MapNode destination) {

		double travelTime = 0.0;

		_route = AStarAlgorithm.search(this, MapGraph.getInstance(),
				_currentNode, destination, false, _speed);

		if (_route != null && _route.size() > 0) {
			_routeDistance = calculateDistance(_route);
			_realEndNode = _route.get(_route.size()-1);
			
			while (_route.size() > Settings.NUMBER_OF_RESERVED_EDGES) {
				_route.remove(_route.size()-1);
			}
			
			Map<MapEdge, ReservationObject> routeReservation = PathReservationAlgorithm.search(this, _route);
			
			if (routeReservation!=null) {

				// remove all previous reservations for this vehicle
				Simulation.getInstance().getModel().removeReservations(this);
				
				int i = 0;
				for (Entry<MapEdge, ReservationObject> obj : routeReservation.entrySet()) {
					i++;
					Simulation.getInstance().getModel().blockEdge(this, obj.getKey(), obj.getValue(), (i==routeReservation.size()));
				}
			
			} else {
				_route = null;
				if(Settings.DEBUG_MODE_ENABLED) System.out.println("(" + getId() + ") No route. Go to neighbour.");
			}
		}
	}

	/**
	 * Calculate the distance of a route
	 * 
	 * @param route
	 * @return
	 */
	public double calculateDistance(List<MapNode> route) {

		double distance = 0.0;

		if (route != null) {
			if (route.size() > 0) {
				for (int i = 0; i < route.size() - 1; i++) {
					MapNode from = route.get(i);
					MapNode to = route.get(i + 1);
					MapEdge edge = MapGraph.getInstance().getEdge(from, to);
					distance += (double) edge.getCost();
				}

			}
		}

		return distance;
	}

	/**
	 * Recalculate cycle for the vehicle
	 */
	public void update(double deltaT) {

		if (_currentTask == null) 
			_currentTask = Simulation.getInstance().getModel().getTask(this);


		if (_route == null)
			_route = new ArrayList<MapNode>();

		if (_route.size() > 0) {
			MapNode node = _route.get(0);

			MapEdge edge = MapGraph.getInstance().getEdge(_currentNode, node);

			double cost = 0.0;

			// avoid collisions
			if (edge != null) {

				// attach Vehicle to the edge
				edge.setToken(_token);

				// remove the vehicle from the last node
				_currentNode.setToken(null);

				cost = edge.getCost();
			}

			double dx = _currentNode.getPosition().getX()
					- node.getPosition().getX();
			double dy = _currentNode.getPosition().getY()
					- node.getPosition().getY();

			
			double currentSpeed = _speed;
			
			if (edge != null)
			{
				// we are still on an edge
				ReservationObject resObj = Simulation.getInstance().getModel().getReservationObject(edge, this);
					
				if (resObj != null)
				{
					currentSpeed = resObj.getSpeed();
				}
			}
						
			double stepX = dx / cost * (currentSpeed * deltaT);
			double stepY = dy / cost * (currentSpeed * deltaT);

			// check if we passed the whole edge
			if (_progress + (currentSpeed * deltaT) >= cost) {
				passedEdge(deltaT, node, edge, cost, currentSpeed);
			} else {				
				doMove(deltaT, stepX, stepY, currentSpeed);
			}

		} else {
			if (_destinationNode == null) {

				if (_currentTask != null) {
					if (_taskStarted) {
						_destinationNode = MapGraph.getInstance().getNode(
								_currentTask.getTo());
					} else {
						_destinationNode = MapGraph.getInstance().getNode(
								_currentTask.getFrom());
					}
					
					_passedGoal = false;
				}

				if (_currentTask == null) {

					_destinationNode = MapGraph.getInstance().getRandomNode();
					_passedGoal = false;
				}

				if (_destinationNode == null)
					return;

				_destinationNode.setDestinationOf(this);
			}

			Simulation.getInstance().getModel().removeReservations(this);
			calculateRoute(_destinationNode);

			// no route found
			if (_route == null) {
				Set<MapNode> neighbours = MapGraph.getInstance().getNeighbours(_currentNode.getId());
				for (MapNode node : neighbours) {
					calculateRoute(node);
					if (_route != null)
						break;
				}
				return;
			}

		}

		_token.resetColor();

	}

	/**
	 * Vehicle passed the edge  
	 * @param deltaT
	 * @param node
	 * @param edge
	 * @param cost
	 * @param currentSpeed
	 */
	private void passedEdge(double deltaT, MapNode node, MapEdge edge,
			double cost, double currentSpeed) {
		
		// calculate how far the vehicle is in the next node
		double restProgress = _progress + (currentSpeed * deltaT) - cost;
		
		// remove the node
		_route.remove(0);

		// avoid collisions
		if (node.hasToken() && !node.getToken().equals(_token)) {
			Simulation.getInstance().addCollision();
			
			if(Settings.DEBUG_MODE_ENABLED)
			{						
				System.out.println("(" + _id + ") Node colission: " + node.getId());
				if (Settings.STOP_ON_COLLISION) Simulation.getInstance().stop();
			}
		}

		// remove the vehicle from the edge and add it to the current
		// node
		if (edge != null)
			edge.setToken(null);
		
		if(Settings.DEBUG_MODE_ENABLED) System.out.println("(" + _id + ") Reached "+ node.getId() + ": "+ Simulation.getInstance().getSimulationTime());

		node.setToken(_token);

		_currentNode = node;
		
		if (_currentNode.equals(_destinationNode)) {
			_passedGoal = true;
		}

		// vehicle is at destination
		if (_route.size() == 0 && _currentNode.equals(_realEndNode) && _passedGoal) {
			reachGoal();
		}
		
		if (_destinationNode!= null)
			_destinationNode.setDestinationOf(this);

		try {
			_token.setPosition(_currentNode.getPosition().clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		_progress = 0.0;
		
		// call update again
		if (restProgress>0)
			update(deltaT-(currentSpeed * deltaT));
	}

	/**
	 * Vehicle reached the goal
	 */
	private void reachGoal() {
		_destinationNode.resetDestinationOf();

		_destinationNode = null;
		_route = null;

		// task management
		if (_taskStarted) { // task completed
			Simulation.getInstance().getModel().completeTasks(_currentTask);
			_currentTask = null;
			_taskStarted = false;
			if (Settings.STOP_AFTER_FIRST_TASK)
				Simulation.getInstance().stop();
		} else
			_taskStarted = true;
	}
	
	/**
	 * Moves the vehicle
	 * @param deltaT
	 * @param stepX
	 * @param stepY
	 * @param speed
	 */
	private void doMove(double deltaT, double stepX, double stepY, double speed) {
		_progress += speed * deltaT;
		
		double x = -stepX + _token.getPosition().getX();
		double y = -stepY + _token.getPosition().getY();

		_token.setX(x);
		_token.setY(y);
	}

	/**
	 * Check if vehicle currently has a task
	 * @return
	 */
	public boolean hasTask() {
		return _currentTask != null;
	}

	@Override
	/**
	 * Get the priority of a vehicle
	 */
	public int getPriority() {
		double traveledDistance = _routeDistance - calculateDistance(_route);
		
		return (int) (100.0 / _routeDistance * traveledDistance);
	}
}
