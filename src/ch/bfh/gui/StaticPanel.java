package ch.bfh.gui;

import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.*;
import ch.bfh.simulation.graph.MapEdge;
import ch.bfh.simulation.graph.MapGraph;
import ch.bfh.simulation.graph.MapNode;
import ch.bfh.utils.*;

/**
 * Class to draw static objects
 * @author gigi
 *
 */
@SuppressWarnings("serial")
public class StaticPanel extends JPanel{
	
	Graphics2D g2;
	SimulationObjects _simObjects;
	
	private double _frameWidth = 0;
	private double _frameHeight = 0;
	private int _scaleFactor = 0;
	
	private static Dimension dim = new Dimension();
	
	/**
	 * init static panel
	 */
	public StaticPanel()
	{
		_simObjects = SimulationObjects.getInstance();
	}
	
	/**
	 * Reset Dimension
	 */
	public static void resetDim()
	{
		dim.setSize(0, 0);
	}
	
	/**
	 * Paint static components
	 */
	public void paintComponent(Graphics g)
	{
		g2 = (Graphics2D) g;
		
		//reset frame size on zoom out
		if(_scaleFactor > _simObjects.getScalefactor())
		{
			_frameWidth = 0;
			_frameHeight = 0;
			_scaleFactor = _simObjects.getScalefactor();
		}
		else
			_scaleFactor = _simObjects.getScalefactor();
		
		for(MapEdge e : MapGraph.getInstance().getEdges())
		{
			Line2D line = _simObjects.drawEdge(e);
			
			double tempFrameHeight = 0;
			double tempFrameWidth = 0;
			
			setFrameSize(line, tempFrameHeight, tempFrameWidth);
			
			g2.setColor(Color.WHITE);
			g2.draw(line);
		}
		
		for(MapNode m : MapGraph.getInstance().getNodes())
		{
			g2.setColor(Color.WHITE);
			
			g2.fill(_simObjects.drawNode(m));
		}
	}

	/**
	 * Set framesize for the staic pane
	 * @param line
	 * @param tempFrameHeight
	 * @param tempFrameWidth
	 */
	private void setFrameSize(Line2D line, double tempFrameHeight, double tempFrameWidth)
	{
		if(line.getX1() < line.getX2())
		{
			tempFrameWidth =line.getX2() + Settings.GUI_XOFFSET;
		}
		
		if(line.getY1() < line.getY2())
		{
			tempFrameHeight =line.getY2() + Settings.GUI_YOFFSET;
		}
		
		if(_frameWidth < tempFrameWidth)
		{
			_frameWidth = tempFrameWidth;
			dim.setSize(_frameWidth, _frameHeight);
		}
		
		if(_frameHeight < tempFrameHeight)
		{
			_frameHeight = tempFrameHeight;
			dim.setSize(_frameWidth, _frameHeight);
		}
	}

	/**
	 * Return dimension of this panel
	 * @return
	 */
	public static Dimension getDimension()
	{
		return dim;
	}
}
