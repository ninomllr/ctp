package ch.bfh.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import ch.bfh.simulation.graph.MapGraph;
import ch.bfh.simulation.graph.MapNode;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Reader for configuration files
 * @author gigi
 *
 */
public class MapReader {
	
	/**
	 * Loads a map file
	 * @param mapName
	 * @return
	 */
	public MapGraph loadMap(String mapName) {
		try {
            Gson gson = new Gson();
            if(Settings.DEBUG_MODE_ENABLED) System.out.println("Load Map");
            if(Settings.DEBUG_MODE_ENABLED) System.out.println("--------------------------------------------------");
            
            BufferedReader brNodes = new BufferedReader(new FileReader(Settings.DIR_MAPS+"/"+mapName+"."+Settings.EXT_NODES));
            Type typeNodes = new TypeToken<Map<String, List<MapNodeInfo>>>() {}.getType();
            Map<String, List<MapNodeInfo>> nodesList = gson.fromJson(brNodes, typeNodes);
            if(Settings.DEBUG_MODE_ENABLED) System.out.println("Nodes: " + nodesList);
            
            BufferedReader brEdges = new BufferedReader(new FileReader(Settings.DIR_MAPS+"/"+mapName+"."+Settings.EXT_EDGES));
            Type typeEdges = new TypeToken<Map<String, List<MapEdgeInfo>>>() {}.getType();
            Map<String, List<MapEdgeInfo>> edgesList = gson.fromJson(brEdges, typeEdges);
            if(Settings.DEBUG_MODE_ENABLED) System.out.println("Edges: " + edgesList);
            
            if(Settings.DEBUG_MODE_ENABLED) System.out.println("--------------------------------------------------");
            
    		return createGraph(nodesList, edgesList);
 
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return null;
	}
	
	/**
	 * Creates a problem graph 
	 * @param nodesList
	 * @param edgesList
	 * @return
	 */
	public MapGraph createGraph(Map<String, List<MapNodeInfo>> nodesList, Map<String, List<MapEdgeInfo>> edgesList) {
		
		// create graph
		MapGraph graph = MapGraph.getInstance();
		
		// create nodes
		for(List<MapNodeInfo> list : nodesList.values()) {
			for (MapNodeInfo node : list) {
				MapNode mapNode = new MapNode(node.getId(), node.getX(), node.getY());
				graph.addNode(mapNode);
			}
		}
		
		// create edges
		for(List<MapEdgeInfo> list2 : edgesList.values()) {
			for (MapEdgeInfo edge : list2) {
				graph.addConnection(edge.getFrom(), edge.getTo(), edge.getCost());
			}
		}
		
		return graph;
	}
	
}
