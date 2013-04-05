package ch.bfh.simulation.graph;

import java.awt.Color;
import java.util.Random;

import ch.bfh.graph.Token;

/**
 * Class for MapToken
 * @author gigi
 *
 */
public class MapToken extends Token {
	
	private Color _color;
	private Color _default;
	
	/**
	 * Gets color of the token
	 * @return
	 */
	public Color getColor() {
		if (_color == null)
			resetColor();
		
		return _color;
	}
	
	/**
	 * Sets the color of the token
	 * @param value
	 */
	public void setColor(Color value) {
		_color = value;
	}
	
	/**
	 * Resets the color of the token
	 */
	public void resetColor() {
		if (_default == null) {
			
			Random rand = new Random();
			float r = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			
			_default = new Color(r, g, b);
		}
		_color = _default;
	}

}
