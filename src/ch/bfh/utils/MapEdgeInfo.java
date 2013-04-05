package ch.bfh.utils;

/**
 * Class for edge reading from configuration file
 * @author nino
 *
 */
public class MapEdgeInfo {
	private String from;
    private String to;
    private int cost;
 
    /**
     * Get from info
     * @return
     */
    public String getFrom() {
		return from;
	}

    /**
     * Set from info
     * @param from
     */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * Get to info
	 * @return
	 */
	public String getTo() {
		return to;
	}

	/**
	 * Set to info
	 * @param to
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * Get cost
	 * @return
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Set cost
	 * @param cost
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}

	@Override
	/**
	 * Returns a string in a printable form
	 */
    public String toString() {
        return "MapEdgeInfo{" +
                "from=" + from +
                ", to='" + to + '\'' +
                ", cost='" + cost + '\'' +
                '}';
    }
}
