package ch.bfh.utils;

/**
 * Node conifguration object
 * @author nino
 *
 */
public class MapNodeInfo {
	private String id;
	private int x;
    private int y;
    private Boolean startpoint;
	private Boolean endpoint;
    
    /**
     * Gets startpoint
     * @return
     */
    public Boolean getStartpoint() {
		return startpoint;
	}

    /**
     * Sets startpoint
     * @param startpoint
     */
	public void setStartpoint(Boolean startpoint) {
		this.startpoint = startpoint;
	}

	/**
	 * Gets endpoint
	 * @return
	 */
	public Boolean getEndpoint() {
		return endpoint;
	}

	/**
	 * Sets endpoint
	 * @param endpoint
	 */
	public void setEndpoint(Boolean endpoint) {
		this.endpoint = endpoint;
	}
	
	/**
	 * Gets id
	 * @return
	 */
    public String getId() {
		return id;
	}

    /**
     * Sets id
     * @param id
     */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets X
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets X
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets X
	 * @return
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets X
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
 
    @Override
    /**
     * Returns a printable string
     */
    public String toString() {
        return "MapNodeInfo{" +
                "id=" + id +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                '}';
    }
}
