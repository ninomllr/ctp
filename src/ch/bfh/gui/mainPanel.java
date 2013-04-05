package ch.bfh.gui;


import java.awt.*;
import java.awt.geom.*;

import javax.swing.JComponent;


import ch.bfh.simulation.*;
import ch.bfh.simulation.graph.*;

import ch.bfh.graph.*;

import ch.bfh.utils.*;


/**
 * GUI class to generat GUI elements
 * @author gigi
 *
 */
@SuppressWarnings("serial")
public class mainPanel extends JComponent{

	Graphics2D g2;
	
	private int scalefactor = 1;
	
	
	/**
	 * Init GUI class
	 */
	public mainPanel() {		
		Simulation.getInstance().start();
	}
	
	
	/**
	 * Paint class
	 */
	public void paintComponent(Graphics g)
	{
		g2 = (Graphics2D) g;
		
		for(MapEdge e : MapGraph.getInstance().getEdges())
		{
			drawEdge(e);
		}
		
		for(MapNode m : MapGraph.getInstance().getNodes())
		{
			drawNode(m);
		}
		
		for(MapToken t : MapGraph.getInstance().getTokens()) {
			drawToken(t);
		}
	}
	
	/**
	 * Draw tokens
	 * @param t
	 */
	private void drawToken(MapToken t) {
		
		Position _position = t.getPosition();
		
		double xPos = _position.getX() + Settings.GUI_XOFFSET-3;
		double yPos = _position.getY() + Settings.GUI_YOFFSET-3;
		
		Rectangle2D.Double vehicle = new Rectangle2D.Double(xPos * scalefactor, yPos * scalefactor, 6 * scalefactor, 6 * scalefactor);
		
		g2.setColor(t.getColor());
		g2.fill(vehicle);
	}
	
	/**
	 * Draw Edges
	 * @param e
	 */
	private void drawEdge(MapEdge e) {
		
		Position from = ((MapNode)e.getFrom()).getPosition();
		Position to = ((MapNode)e.getTo()).getPosition();
		
		int xPosFrom = (int)from.getX() + Settings.GUI_XOFFSET;
		int xPosTo = (int)to.getX() + Settings.GUI_XOFFSET;
		int yPosFrom = (int)from.getY() + Settings.GUI_YOFFSET;
		int yPosTo = (int)to.getY() + Settings.GUI_YOFFSET;
		

		Line2D line = new Line2D.Double(xPosFrom * scalefactor, yPosFrom * scalefactor, xPosTo * scalefactor, yPosTo * scalefactor);
		g2.setStroke(new BasicStroke(1 * scalefactor));
		
		if(e.getBlocked())
			g2.setColor(Color.BLACK);
		else		
			g2.setColor(Color.WHITE);

		g2.draw(line); 
	}

	/**
	 * Draw Nodes
	 * @param m
	 */
	private void drawNode(MapNode m)
	{
		double xPos = m.getPosition().getX() + Settings.GUI_XOFFSET - (Settings.GUI_DIAM_NODE/2);
		double yPos = m.getPosition().getY() + Settings.GUI_YOFFSET - (Settings.GUI_DIAM_NODE/2);
		
		Ellipse2D.Double gNode = new Ellipse2D.Double(xPos * scalefactor, yPos * scalefactor, Settings.GUI_DIAM_NODE * scalefactor, Settings.GUI_DIAM_NODE * scalefactor);

		g2.setColor(Color.WHITE);
		
		g2.fill(gNode);
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
		this.scalefactor = scalefactor;
	}
}
