package ch.bfh.gui;

import ch.bfh.simulation.*;

public class CLI_Window
{
	static SimulationObjects _simObjects;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		startSimulation();
	}

	/**
	 * Starts the simulation for the CLI gui
	 */
	private static void startSimulation() {
		//Simulation.getInstance().setSpeed(1000);
		Simulation.getInstance().setRunAsFastAsPossible(true);
		Simulation.getInstance().start();
		
		System.out.println("Start Simulation");

		while (Simulation.getInstance().getRunning())
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}


}
