package ch.bfh.graph;

/**
 * A node represents a vertex in the graph
 * @author nino
 *
 */
public class Node {

    protected String _id;

    /**
     * Get ID of Node
     * @return
     */
	public String getId() {
		return _id;
	}

	/**
	 * Set ID of node
	 * @param id
	 */
	public void setId(String id) {
		this._id = id;
	}
}