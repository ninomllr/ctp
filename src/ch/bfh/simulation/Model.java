package ch.bfh.simulation;

import java.util.*;
import java.util.Map.*;

import ch.bfh.graph.*;
import ch.bfh.simulation.graph.*;
import ch.bfh.simulation.reservation.*;
import ch.bfh.simulation.tasks.*;
import ch.bfh.utils.*;

public class Model {

	private SortedList<IUpdateable> _updateableObjects;
	private List<Vehicle> _vehicles;
	private Map<MapEdge, EdgeReservationList> _edgeReservations;
	private Map<MapNode, NodeReservationList> _nodeReservations;
	private PriorityQueue<Task> _tasklist;
	private ArrayList<Task> _runningTasks = new ArrayList<Task>();

	/**
	 * Initialize the model and create all the simulation objects
	 */
	public Model() {

		// initialize everything
		_updateableObjects = new SortedList<IUpdateable>(
				new PriorityComparator());
		_vehicles = new ArrayList<Vehicle>();
		_edgeReservations = new HashMap<MapEdge, EdgeReservationList>();
		_nodeReservations = new HashMap<MapNode, NodeReservationList>();
		_tasklist = new PriorityQueue<Task>(20, new TaskListComparator());

		// load map and scenario
		MapGraph.loadMap("hafen");

		TaskReader reader = new TaskReader();

		ScenarioInfo scenario = reader.getVehicles("test"); // read
																	// TaskList

		for (TaskListInfo task : scenario.getTasklist()) {
			_tasklist.add(new Task(task.getFrom(), task.getTo(), task
					.getArrival(), null));
		}

		// init all simulation objects
		initVehicles(scenario.getVehicles());
		initEdges();
		initNodes();

	}

	/**
	 * Create a runnable thread for each IUpdateable
	 */
	public void update(double deltaT) {


		_updateableObjects = new SortedList<IUpdateable>(_updateableObjects);

		// Update each updateable object
		for (IUpdateable v : _updateableObjects)
			v.update(deltaT);


		// check if simulation is over
		boolean hasTasks = _tasklist.size() > 0;
		for (Vehicle v : _vehicles) {
			hasTasks = hasTasks || v.hasTask();
		}

		// stop simulation if all tasks are solved
		if (!hasTasks)
			Simulation.getInstance().stop();

		markBlockedEdges();
	}

	/**
	 * Marks all edges as blocked so they can be displayed differently in the GUI
	 */
	private void markBlockedEdges() {
		// mark the current blocked edges so they can be displayed in the gui
		for (Entry<MapEdge, EdgeReservationList> reservation : _edgeReservations
				.entrySet()) {

			double time = Simulation.getInstance().getSimulationTime();
			
			if (reservation.getValue().reservationExists(time, time + 30)) {
				reservation.getKey().setBlock();
			}
			

		}

		// mark the current blocked nodes so they can be displayed in the gui
		for (Entry<MapNode, NodeReservationList> reservation : _nodeReservations
				.entrySet()) {

			double time = Simulation.getInstance().getSimulationTime();
			
			if (reservation.getValue().reservationExists(time, time + 30)) {
				reservation.getKey().setBlock();
			}
			
		}
	}

	/**
	 * Add an object to the updateable list
	 * 
	 * @param obj
	 */
	public void addUpdateableObject(IUpdateable obj) {
		_updateableObjects.add(obj);
	}

	/**
	 * Remove all reservations for a specific vehicle
	 * 
	 * @param v
	 */
	public void removeReservations(Vehicle v) {

		for (Entry<MapEdge, EdgeReservationList> reservation : _edgeReservations
				.entrySet()) {

			reservation.getValue().clearAllReservations(v);
		}

		for (Entry<MapNode, NodeReservationList> reservation : _nodeReservations
				.entrySet()) {

			reservation.getValue().clearAllReservations(v);
		}

	}

	/**
	 * Block an edge and the connected nodes
	 * 
	 * @param v
	 * @param edge
	 * @param from
	 * @param to
	 */
	public void blockEdge(Vehicle v, MapEdge edge, double from, double to,
			double speed, boolean lastNode) {

		if (!getReservationExists(v, edge, from, to)) {

			_edgeReservations.get(edge).reserve(v,
					from - Settings.ADDITIONAL_RESERVATION_TIME,
					to + Settings.ADDITIONAL_RESERVATION_TIME, speed);

			_nodeReservations.get(edge.getFrom()).reserve(
					v,
					from - Settings.ADDITIONAL_RESERVATION_TIME,
					from + ((to - from) / 2)
							+ Settings.ADDITIONAL_RESERVATION_TIME);

			_nodeReservations.get(edge.getTo()).reserve(
					v,
					from + ((to - from) / 2)
							- Settings.ADDITIONAL_RESERVATION_TIME,
					to + Settings.ADDITIONAL_RESERVATION_TIME);
			

		}
	}

	/**
	 * Check if a reservation exists
	 * 
	 * @param edge
	 * @param from
	 * @param to
	 * @return
	 */
	public Boolean getReservationExists(Vehicle v, MapEdge edge, double from,
			double to) {
		Boolean reservationExists = _edgeReservations.get(
				edge.getOppositeDirection()).reservationExists(v, from, to);
		reservationExists = reservationExists
				|| _edgeReservations.get(edge).reservationExists(v, from, to);

		return reservationExists;
	}

	/**
	 * Check if an edge is blocked
	 * 
	 * @param edge
	 * @param from
	 * @param to
	 * @return
	 */
	public Boolean getBlocked(Vehicle v, MapEdge edge, double from, double to) {
		return _edgeReservations.get(edge).reservationExists(v, from, to);
	}

	/**
	 * Check if a node is blocked
	 * 
	 * @param node
	 * @param from
	 * @param to
	 * @return
	 */
	public boolean getBlocked(MapNode node, double from, double to) {
		return _nodeReservations.get(node).reservationExists(from, to);
	}

	/**
	 * Initialize all vehicles
	 * 
	 * @param numberOfVehicles
	 */
	private void initVehicles(int numberOfVehicles) {
		for (int i = 0; i < numberOfVehicles; i++) {
			//generateVehicleAtNode(i + "", "A0");
			generateRandomVehicle(i + "");
		}

		for (Vehicle v : _vehicles) {
			addUpdateableObject(v);
		}
	}

	/**
	 * Initialize all Edges
	 */
	private void initEdges() {
		List<MapEdge> processedEdges = new ArrayList<MapEdge>();

		for (MapEdge e : MapGraph.getInstance().getEdges()) {

			if (processedEdges.contains(e))
				continue;

			processedEdges.add(e);
			processedEdges.add((MapEdge) e.getOppositeDirection());

			EdgeReservationList list = new EdgeReservationList(e,
					(MapEdge) e.getOppositeDirection());
			_updateableObjects.add(list);

			_edgeReservations.put(e, list);
			_edgeReservations.put((MapEdge) e.getOppositeDirection(), list);
		}
	}

	/**
	 * Initialize all Nodes
	 */
	private void initNodes() {
		for (MapNode n : MapGraph.getInstance().getNodes()) {
			NodeReservationList list = new NodeReservationList(n);
			_updateableObjects.add(list);
			_nodeReservations.put(n, list);
		}
	}

	/**
	 * Get list of all vehicles
	 * 
	 * @return
	 */
	public List<Vehicle> getVehicles() {
		return _vehicles;
	}

	/**
	 * Add vehicle to a simulation
	 * 
	 * @param vehicle
	 */
	public void addVehicle(Vehicle vehicle) {
		_vehicles.add(vehicle);
	}

	/**
	 * Generate vehicle and don't add it to a node
	 * 
	 * @param id
	 */
	public void generateVehicle(String id) {
		MapToken token = new MapToken();
		MapGraph.getInstance().addToken(token);
		_vehicles.add(new Vehicle(token, null, id));
	}

	/**
	 * Generate vehicle and add it to a random node
	 */
	public void generateRandomVehicle(String id) {
		MapToken token = new MapToken();
		MapGraph.getInstance().addToken(token);
		_vehicles.add(new Vehicle(token,
				MapGraph.getInstance().getRandomNode(), id));
	}

	/**
	 * Generates a vehicle at a specific position, can be used for testing
	 * @param id
	 * @param nodeId
	 */
	public void generateVehicleAtNode(String id, String nodeId) {
		MapToken token = new MapToken();
		MapGraph.getInstance().addToken(token);
		MapNode node = MapGraph.getInstance().getNode(nodeId);
		_vehicles.add(new Vehicle(token, node, id));

	}

	/**
	 * Get a list of all open tasks
	 * @return
	 */
	public List<Task> getTasks() {

		// all from Tasklist and runnable list
		ArrayList<Task> tempList = new ArrayList<Task>();

		tempList.addAll(_runningTasks);
		tempList.addAll(_tasklist);

		return tempList;
	}

	/**
	 * Mark a task as completed
	 * @param task
	 */
	public void completeTasks(Task task) {
		_runningTasks.remove(task);
	}

	/**
	 * Get the next task to solve
	 * @return
	 */
	public Task getTask(Vehicle vehicle) {

		Task t0 = _tasklist.peek();

		if (t0 != null) {
			if (Simulation.getInstance().getSimulationTime() >= t0.getArrival()) {
				Task t1 = _tasklist.poll();
				t1.setVehicle(vehicle);

				_runningTasks.add(t1);

				return t1;
			}

		}
		return null;
	}

	/**
	 * Add a task
	 * @param _currentTask
	 */
	public void addTask(Task _currentTask) {
		// TODO noch besprechen mit Hr Eckerle
		_tasklist.add(_currentTask);

	}

	/**
	 * Gets a reservation object for a vehicle on a speicifc edge
	 * @param edge
	 * @param vehicle
	 * @return
	 */
	public ReservationObject getReservationObject(MapEdge edge, Vehicle vehicle) {

		for (Entry<MapEdge, EdgeReservationList> reservation : _edgeReservations
				.entrySet()) {

			if (reservation.getKey().equals(edge)) {
				for (ReservationObject obj : reservation.getValue()) {
					if (obj.getReservator().equals(vehicle)) {
						return obj;
					}
				}
			}
		}

		return null;
	}

	/**
	 * Gets all reservation objects for a specific edge
	 * @param edge
	 * @return
	 */
	public EdgeReservationList getReservationObjects(MapEdge edge) {
		for (Entry<MapEdge, EdgeReservationList> reservation : _edgeReservations
				.entrySet()) {

			if (reservation.getKey().equals(edge)) {
				return reservation.getValue();
			}
		}

		return null;
	}

	/**
	 * Blocks an edge
	 * @param vehicle
	 * @param key
	 * @param value
	 * @param lastNode
	 */
	public void blockEdge(Vehicle vehicle, MapEdge key, ReservationObject value, boolean lastNode) {
		if(Settings.DEBUG_MODE_ENABLED) {
			System.out.println("(" + vehicle.getId() + ") Edge Reservation for "
					+ key.getId() + " " + value.getFrom() + " - " + value.getTo());
			
		}
		blockEdge(vehicle, key, value.getFrom(), value.getTo(),
				value.getSpeed(), lastNode);	
	}

	/**
	 * Gets all reservation objects for a node
	 * @param node
	 * @return
	 */
	public NodeReservationList getReservationObjects(Node node) {
		for (Entry<MapNode, NodeReservationList> reservation : _nodeReservations
				.entrySet()) {

			if (reservation.getKey().equals(node)) {
				return reservation.getValue();
			}
		}

		return null;
	}

	/**
	 * Gets a vehicle with a specific id
	 * @param vehicleId
	 * @return
	 */
	public Vehicle getVehicle(String vehicleId) {
		for (Vehicle v : _vehicles) {
			if (vehicleId.equals(v.getId()))
				return v;
		}
		return null;
	}

}
