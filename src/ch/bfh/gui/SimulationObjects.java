package ch.bfh.gui;


import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import ch.bfh.graph.Position;
import ch.bfh.simulation.graph.MapEdge;
import ch.bfh.simulation.graph.MapNode;
import ch.bfh.simulation.graph.MapToken;
import ch.bfh.utils.Settings;

/**
 * Class for simulation objects
 * @author gigi
 *
 */
public class SimulationObjects 
{
	private int scalefactor = 1;
	
	private static SimulationObjects _simObjects;
	
	/**
	 * init Simulation Objects
	 */
	private SimulationObjects()
	{		
	}
	
	/**
	 * Init Class as singelton
	 * @return
	 */
	public static SimulationObjects getInstance() {
		if (_simObjects == null)
		{
			_simObjects = new SimulationObjects();
		}
		
		return _simObjects;
	}
	
	/**
	 * Get Scalefactor
	 * @return
	 */
	public int getScalefactor() {
		return scalefactor;
	}

	/**
	 * Set Scalefactor
	 * @param scalefactor
	 */
	public void setScalefactor(int scalefactor) {
		
		if (scalefactor < this.scalefactor) {
			StaticPanel.resetDim();
		}
		
		this.scalefactor = scalefactor;
	}

	/**
	 * Draw Edges
	 * @param e
	 */
	public Line2D drawEdge(MapEdge e) {
				
		Position from = ((MapNode)e.getFrom()).getPosition();
		Position to = ((MapNode)e.getTo()).getPosition();
		
		int xPosFrom = (int)from.getX() + Settings.GUI_XOFFSET;
		int xPosTo = (int)to.getX() + Settings.GUI_XOFFSET;
		int yPosFrom = (int)from.getY() + Settings.GUI_YOFFSET;
		int yPosTo = (int)to.getY() + Settings.GUI_YOFFSET;
		
		Line2D line = new Line2D.Double(xPosFrom * scalefactor, yPosFrom * scalefactor, xPosTo * scalefactor, yPosTo * scalefactor);

		return line;
	}
	
	/**
	 * Draw Nodes
	 * @param m
	 */
	public Ellipse2D drawNode(MapNode m)
	{

		double xPos = m.getPosition().getX() + Settings.GUI_XOFFSET - (Settings.GUI_DIAM_NODE/2);
		double yPos = m.getPosition().getY() + Settings.GUI_YOFFSET - (Settings.GUI_DIAM_NODE/2);
		
		Ellipse2D.Double gNode = new Ellipse2D.Double(xPos * scalefactor, yPos * scalefactor, Settings.GUI_DIAM_NODE * scalefactor, Settings.GUI_DIAM_NODE * scalefactor);
		
		return gNode;
	}
	
	/**
	 * Draw tokens
	 * @param t
	 */
	public Rectangle2D drawToken(MapToken t) 
	{

		Position _position = t.getPosition();
		if (_position == null) return null;
		
		double xPos = _position.getX() + Settings.GUI_XOFFSET-3;
		double yPos = _position.getY() + Settings.GUI_YOFFSET-3;
		
		Rectangle2D.Double vehicle = new Rectangle2D.Double(xPos * scalefactor, yPos * scalefactor, 6 * scalefactor, 6 * scalefactor);

		return vehicle;
	}

}
