package ch.bfh.cli;

import ch.bfh.simulation.graph.MapGraph;
import ch.bfh.simulation.graph.MapNode;
import ch.bfh.utils.Settings;

public class AStarTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MapGraph graph = MapGraph.getInstance();
		MapNode node1 = new MapNode("Saarbruecken", 100, 122);
		MapNode node2 = new MapNode("Kaiserslauten", 94, 64);
		MapNode node3 = new MapNode("Frankfurt", 40, 56);
		MapNode node4 = new MapNode("Wuerzburg", 0, 0);
		MapNode node5 = new MapNode("Ludwigshafen", 50, 58);
		MapNode node6 = new MapNode("Karlsruhe", 40, 100);
		MapNode node7 = new MapNode("Heilbronn",25, 62);
		graph.addNode(node1);
		graph.addNode(node2);
		graph.addNode(node3);
		graph.addNode(node4);
		graph.addNode(node5);
		graph.addNode(node6);
		graph.addNode(node7);
		graph.addConnection(node1, node2, 70);
		graph.addConnection(node1, node6, 145);
		graph.addConnection(node2, node3, 103);
		graph.addConnection(node2, node5, 53);
		graph.addConnection(node6, node7, 84);
		graph.addConnection(node3, node4, 116);
		graph.addConnection(node5, node4, 183);
		graph.addConnection(node7, node4, 102);
		
		/*List<MapNode> path = AStarAlgorithm.search(graph, node7, node1, false, 1.0);
		for (MapNode node : path) {
			System.out.println(node.getId());
		}*/
		
		if(Settings.DEBUG_MODE_ENABLED)	System.out.println("End");
	}

}
