package ch.bfh.simulation;

/**
 * Simulation Class
 * @author gigi
 *
 */
public class Simulation {
	
	private static Simulation _instance;

	private double _simulationTime;
	
	private int _simulationSpeed;
	
	private Boolean _running;
	
	private final Model _model;
	
	private int _collisions;
	
	private boolean _fastAsPossible;
	
	/**
	 * Init Simulation class
	 */
	private Simulation() {

		_running = false;
		_simulationTime = 0;
		_simulationSpeed = 5;
		_model = new Model();
		_collisions = 0;
		_fastAsPossible = false;
	}
	
	/**
	 * Get instance of a simulation (Singelton object)
	 * @return
	 */
	public static Simulation getInstance() {
		if (_instance == null) _instance = new Simulation();
		return _instance;
	}
	
	public void setRunAsFastAsPossible(boolean value) {
		_fastAsPossible = value;
	}
	
	/**
	 * Execute method for the simulation which calls all other methods
	 */
	private void execute() {
		
		if (!_fastAsPossible)
			executeNormal();
		else 
			executeFast();
		
		// Show numbers of collisions
		System.out.println("Number of collisions: " + _collisions);

		// Show time of run
		System.out.println("Running time: " + getSimulationTimeFormatted());
		
	}
	
	private void executeFast() {
		while (_running) {
			_model.update(1.0);
			_simulationTime+=1.0;
		}
	}

	/**
	 * Execute with a connection to the real time
	 */
	private void executeNormal() {
		long start = System.currentTimeMillis();
		long end = System.currentTimeMillis();
		
		while(_running) {
			
			double diff = ((double)(end - start) / 1000)*_simulationSpeed;
			start = System.currentTimeMillis();
			end = start;
				
			_model.update(diff); 
			_simulationTime+=diff;
			
			while (end==start) {
				end = System.currentTimeMillis();
			}	
		}
	}
	
	/**
	 * Starts the simulation
	 */
	public void start() {
		
		_running = true;
		
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				execute();
			}
		};
		Thread t = new Thread(r);
		t.start();
	}
	
	/**
	 * Stops the simulation
	 */
	public void stop() {
		_running = false;

	}

	/**
	 * Gets an instance of the model
	 * @return
	 */
	public Model getModel() {
		return _model;
	}
	
	/**
	 * Get state of simulation
	 * @return
	 */
	public boolean getRunning()
	{
		return _running;
	}

	/**
	 * Gets number of collisions
	 * @return
	 */
	public int getColissions() {
		return _collisions;
	}

	/**
	 * Adds another collision
	 */
	public void addCollision() {
		_collisions++;
	}

	/**
	 * Sets the simulation speed
	 * @param value
	 */
	public void setSpeed(int value) {
		_simulationSpeed = value;
		
	}

	/**
	 * Gets the simulation time
	 * @return
	 */
	public double getSimulationTime() {
		return _simulationTime;
	}  

	/**
	 * Get simulation time in a printable form
	 * @return
	 */
	public String getSimulationTimeFormatted() {
		int hours = (int) (_simulationTime / (60 * 60));
		int minutes = (int) (((_simulationTime - (hours / (60 * 60))) / 60) % 60);
		int seconds = (int) ((_simulationTime - (hours / (60 * 60)) - (minutes / 60)) % 60);
		
		return String.format("%02d%n", hours) + ":" + String.format("%02d%n", minutes) + ":" +  String.format("%02d%n", seconds);
	}
}
