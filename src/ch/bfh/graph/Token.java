package ch.bfh.graph;

/**
 * Tocken class
 * @author gigi
 *
 */

public class Token {

	protected String _id;
	protected Position _position;
	
	/**
	 * Get ID of a Token
	 * @return
	 */
	public String getId() {
		return _id;
	}
	
	/**
	 * Set ID of a Token
	 * @param _id
	 */
	public void setId(String _id) {
		this._id = _id;
	}
	
	/**
	 * Get position of a token
	 * @return
	 */
	public Position getPosition() {
		return _position;
	}
	
	/**
	 * Set position of a token
	 * @param _position
	 */
	public void setPosition(Position _position) {
		this._position = _position;
	}	
	
	/**
	 * Sets X position
	 * @param x
	 */
	public void setX(double x) {
		_position.setX(x);
	}
	
	/**
	 * Sets Y position
	 * @param y
	 */
	public void setY(double y) {
		_position.setY(y);
	}
}
