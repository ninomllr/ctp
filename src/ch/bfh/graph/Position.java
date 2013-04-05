package ch.bfh.graph;

/**
 * The positions represents one point in the graph
 * @author nino
 *
 */
public class Position implements Cloneable {
    private double _x;

    private double _y;

    /**
     * Clone Position
     */
    public Position clone() throws CloneNotSupportedException
    {
    	return (Position)super.clone();
    }
    
    
    /**
     * Constructor of position
     * @param x
     * @param y
     */
	public Position(double x, double y) {
		this._x = x;
		this._y = y;
	}

	/**
	 * Get x coordinate
	 * @return
	 */
	public double getX() {
		return _x;
	}

	/**
	 * Set x coordinate
	 * @param x
	 */
	public void setX(double x) {
		this._x = x;
	}

	/**
	 * Get y coordinate
	 * @return
	 */
	public double getY() {
		return _y;
	}

	/**
	 * Set y coordinate
	 * @param y
	 */
	public void setY(double y) {
		this._y = y;
	}
}
