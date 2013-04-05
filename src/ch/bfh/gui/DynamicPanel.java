package ch.bfh.gui;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import ch.bfh.simulation.*;
import ch.bfh.simulation.graph.MapEdge;
import ch.bfh.simulation.graph.MapGraph;
import ch.bfh.simulation.graph.MapNode;
import ch.bfh.simulation.graph.MapToken;

/**
 * Class to draw dynamic objects
 * @author gigi
 *
 */
@SuppressWarnings("serial")
public class DynamicPanel extends JComponent
{
	SimulationObjects _simObjects;

	/**
	 * Init GUI class
	 */
	public DynamicPanel() {
		Simulation.getInstance().start();
		_simObjects = SimulationObjects.getInstance();
	}
	
	
	/**
	 * Paint class
	 */
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		for(MapEdge e : MapGraph.getInstance().getEdges())
		{
			if(e.getBlocked())
			{
				g2.setColor(Color.orange);
				g2.draw(_simObjects.drawEdge(e));
			}
		}
		
		for(MapNode m : MapGraph.getInstance().getNodes())
		{
			if (m.getDestinationOf()!=null)
			{
				g2.setColor(m.getColor());
				g2.fill(_simObjects.drawNode(m));
			} else if (m.getBlocked()) {
				g2.setColor(Color.orange);
				g2.fill(_simObjects.drawNode(m));
			}
		}
		
		for(MapToken t : MapGraph.getInstance().getTokens()) 
		{
			g2.setColor(t.getColor());
			Rectangle2D d = _simObjects.drawToken(t);
			if (d!=null)
				g2.fill(d);
		}
	}
}
